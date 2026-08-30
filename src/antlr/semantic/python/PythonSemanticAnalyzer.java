package antlr.semantic.python;

import antlr.ast.python.ProgramNode;
import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.ListComprehensionNode;
import antlr.ast.python.expressions.access.DotAccessNode;
import antlr.ast.python.expressions.access.FunctionCallNode;
import antlr.ast.python.expressions.access.IndexAccessNode;
import antlr.ast.python.expressions.access.KeywordArgumentNode;
import antlr.ast.python.expressions.access.RenderTemplateNode;
import antlr.ast.python.expressions.access.VariableNode;
import antlr.ast.python.expressions.literals.BooleanNode;
import antlr.ast.python.expressions.literals.DictEntryNode;
import antlr.ast.python.expressions.literals.DictNode;
import antlr.ast.python.expressions.literals.ListNode;
import antlr.ast.python.expressions.literals.NoneNode;
import antlr.ast.python.expressions.literals.NumberDoubleNode;
import antlr.ast.python.expressions.literals.NumberIntegerNode;
import antlr.ast.python.expressions.literals.StringNode;
import antlr.ast.python.expressions.operations.BinaryOpNode;
import antlr.ast.python.expressions.operations.ComparisonNode;
import antlr.ast.python.expressions.operations.LogicalOpNode;
import antlr.ast.python.expressions.operations.UnaryOpNode;
import antlr.ast.python.parameters.KwargsNode;
import antlr.ast.python.parameters.KeywordOnlySeparator;
import antlr.ast.python.parameters.ParameterNode;
import antlr.ast.python.parameters.StarArgsNode;
import antlr.ast.python.statements.AssignmentNode;
import antlr.ast.python.statements.BlockNode;
import antlr.ast.python.statements.BreakNode;
import antlr.ast.python.statements.ClassDefinitionNode;
import antlr.ast.python.statements.ContinueNode;
import antlr.ast.python.statements.DecoratorNode;
import antlr.ast.python.statements.ExpressionStatementNode;
import antlr.ast.python.statements.ForStatementNode;
import antlr.ast.python.statements.FunctionDefinitionNode;
import antlr.ast.python.statements.IfStatementNode;
import antlr.ast.python.statements.ImportStatement;
import antlr.ast.python.statements.ImportedClassNode;
import antlr.ast.python.statements.PrintNode;
import antlr.ast.python.statements.ReturnNode;
import antlr.ast.python.statements.WhileStatementNode;
import antlr.ast.visitor.ASTVisitorAdapter;
import antlr.semantic.Builtins;
import antlr.semantic.SemanticError;
import antlr.semantic.types.Type;
import antlr.symbol.Symbol;
import antlr.symbol.SymbolTable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Semantic analysis pass over a Python AST. Produces the same class of
 * findings CPython raises at compile/runtime time, without executing the code:
 *
 * <ul>
 *   <li>UNDEFINED_VARIABLE — NameError (pyflakes-style: later module-level
 *       definitions are pre-declared, so a function may reference a global
 *       defined further down the file).</li>
 *   <li>TYPE_ERROR — calling / subscripting / iterating / attribute-reading a
 *       known non-callable / non-subscriptable / non-iterable / non-object
 *       type, which Python always rejects.</li>
 *   <li>TYPE_MISMATCH — unsupported binary operand types, ordering comparisons
 *       between incompatible types, list indexed by a string, unary minus on a
 *       string, wrong user-function arity and unknown keyword arguments.</li>
 *   <li>SCOPE_ERROR — {@code break}/{@code continue} outside a loop and
 *       {@code return} outside a function.</li>
 * </ul>
 *
 * Anything that depends on runtime values is kept as {@link Type.Kind#ANY} so
 * the pass never guesses: it only flags operations Python always rejects.
 */
public class PythonSemanticAnalyzer extends ASTVisitorAdapter<Type> {

    private static final String ARROW = " -> ";

    private final SymbolTable symbolTable = new SymbolTable();
    private SymbolTable currentScope;

    private final Deque<Map<String, Type>> typeFrames = new ArrayDeque<>();
    private final Map<String, FunctionSignature> signatures = new LinkedHashMap<>();

    private final List<SemanticError> errors = new ArrayList<>();

    private int loopDepth = 0;
    private int functionDepth = 0;

    private String sourceName;

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void analyze(ProgramNode program) {
        currentScope = symbolTable;
        typeFrames.clear();
        typeFrames.push(new LinkedHashMap<>());
        Builtins.defineIn(symbolTable);
        defineModuleDunders();
        declareModuleGlobals(program);
        program.accept(this);
    }

    private void defineModuleDunders() {
        Symbol name = new Symbol("__name__", Symbol.SymbolType.VARIABLE, 0, 0);
        name.setDataType(Type.STRING.display());
        symbolTable.define(name);
        bind("__name__", Type.STRING);

        Symbol file = new Symbol("__file__", Symbol.SymbolType.VARIABLE, 0, 0);
        file.setDataType(Type.STRING.display());
        symbolTable.define(file);
        bind("__file__", Type.STRING);

        Symbol builtins = new Symbol("__builtins__", Symbol.SymbolType.VARIABLE, 0, 0);
        builtins.setDataType(Type.ANY.display());
        symbolTable.define(builtins);
        bind("__builtins__", Type.ANY);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public List<SemanticError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    // ==================== Pass A: module-level pre-declaration ====================
    // Registers names bound directly in the global scope before the walk, so a
    // use in an earlier statement (e.g. a route function calling a global that
    // is assigned below it) resolves like it does in Python.
    private void declareModuleGlobals(ProgramNode program) {
        for (StatementNode stmt : program.getStatements()) {
            if (stmt instanceof ImportStatement imp) {
                for (ImportedClassNode cls : imp.getImportedClasses()) {
                    defineGlobal(cls.getClassName(), Symbol.SymbolType.IMPORT, imp.getLineNumber(),
                            imp.getColumnNumber(), imp.getModuleName());
                }
            } else if (stmt instanceof FunctionDefinitionNode fn) {
                defineGlobal(fn.getFunctionName(), Symbol.SymbolType.FUNCTION, fn.getLineNumber(),
                        fn.getColumnNumber(), null);
                registerSignature(fn);
            } else if (stmt instanceof ClassDefinitionNode cls) {
                defineGlobal(cls.getClassName(), Symbol.SymbolType.CLASS, cls.getLineNumber(),
                        cls.getColumnNumber(), "class");
            } else if (stmt instanceof AssignmentNode assign
                    && assign.getTarget() instanceof VariableNode var) {
                defineGlobal(var.getName(), Symbol.SymbolType.VARIABLE, assign.getLineNumber(),
                        assign.getColumnNumber(), null);
            }
        }
    }

    private void defineGlobal(String name, Symbol.SymbolType type, int line, int col, String dataType) {
        Symbol symbol = new Symbol(name, type, line, col);
        if (dataType != null) {
            symbol.setDataType(dataType);
        }
        symbolTable.define(symbol);
    }

    // ==================== Scopes ====================

    private void enterScope(String name) {
        currentScope = currentScope.enterScope(name);
        typeFrames.push(new LinkedHashMap<>());
    }

    private void exitScope() {
        currentScope = currentScope.exitScope();
        typeFrames.pop();
    }

    private void bind(String name, Type type) {
        typeFrames.peek().put(name, type);
        Symbol symbol = currentScope.resolve(name);
        if (symbol == null) {
            symbol = new Symbol(name, Symbol.SymbolType.VARIABLE,
                    currentScope.getScopeLevel(), 0);
            currentScope.define(symbol);
        }
        symbol.setDataType(type.display());
        symbol.setValue(type.display());
    }

    private Type lookupType(String name) {
        for (Map<String, Type> frame : typeFrames) {
            Type type = frame.get(name);
            if (type != null) {
                return type;
            }
        }
        return Type.ANY;
    }

    private boolean isDefined(String name) {
        return currentScope.resolve(name) != null;
    }

    // ==================== Error reporting ====================

    private void error(SemanticError.Code code, String message, int line, int col) {
        errors.add(new SemanticError(code, message, line, col, sourceName));
    }

    private static String repr(Type type) {
        return type.pythonRepr();
    }

    private static boolean isIntLike(Type type) {
        return type.kind() == Type.Kind.INT || type.kind() == Type.Kind.BOOL;
    }

    // ==================== Root / Statements ====================

    @Override
    public Type visit(ProgramNode node) {
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(BlockNode node) {
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(AssignmentNode node) {
        ExpressionNode value = node.getValue();
        Type valueType = (value != null) ? value.accept(this) : Type.NONE;

        ExpressionNode target = node.getTarget();
        if (target instanceof VariableNode var) {
            bind(var.getName(), valueType);
        } else if (target != null) {
            // Index / dot targets still need their container/key visited.
            target.accept(this);
        }
        return valueType;
    }

    @Override
    public Type visit(PrintNode node) {
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        return Type.NONE;
    }

    @Override
    public Type visit(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(ReturnNode node) {
        if (functionDepth == 0) {
            error(SemanticError.Code.SCOPE_ERROR, "'return' outside function",
                    node.getLineNumber(), node.getColumnNumber());
        }
        if (node.hasValue()) {
            return node.getValue().accept(this);
        }
        return Type.NONE;
    }

    @Override
    public Type visit(BreakNode node) {
        if (loopDepth == 0) {
            error(SemanticError.Code.SCOPE_ERROR, "'break' outside loop",
                    node.getLineNumber(), node.getColumnNumber());
        }
        return Type.NONE;
    }

    @Override
    public Type visit(ContinueNode node) {
        if (loopDepth == 0) {
            error(SemanticError.Code.SCOPE_ERROR, "'continue' not properly in loop",
                    node.getLineNumber(), node.getColumnNumber());
        }
        return Type.NONE;
    }

    @Override
    public Type visit(IfStatementNode node) {
        if (node.getCondition() != null) {
            node.getCondition().accept(this);
        }

        enterScope("if_block_" + node.getLineNumber());
        if (node.getIfBlock() != null) {
            node.getIfBlock().accept(this);
        }
        exitScope();

        List<ExpressionNode> elifConditions = node.getElifConditions();
        List<BlockNode> elifBlocks = node.getElifBlocks();
        for (int i = 0; i < elifConditions.size(); i++) {
            elifConditions.get(i).accept(this);
            enterScope("elif_block_" + node.getLineNumber() + "_" + i);
            if (i < elifBlocks.size() && elifBlocks.get(i) != null) {
                elifBlocks.get(i).accept(this);
            }
            exitScope();
        }

        if (node.hasElse()) {
            enterScope("else_block_" + node.getLineNumber());
            node.getElseBlock().accept(this);
            exitScope();
        }
        return Type.ANY;
    }

    @Override
    public Type visit(ForStatementNode node) {
        Type iterableType = node.getIterable().accept(this);
        if (iterableType.isKnown() && !iterableType.isIterable()) {
            error(SemanticError.Code.TYPE_ERROR,
                    repr(iterableType) + " object is not iterable",
                    node.getLineNumber(), node.getColumnNumber());
        }

        loopDepth++;
        enterScope("for_block_" + node.getLineNumber());
        bind(node.getTargetVariable(), iterableType.iterationElement());
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }
        exitScope();
        loopDepth--;
        return Type.ANY;
    }

    @Override
    public Type visit(WhileStatementNode node) {
        loopDepth++;
        if (node.getCondition() != null) {
            node.getCondition().accept(this);
        }
        enterScope("while_block_" + node.getLineNumber());
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }
        exitScope();
        loopDepth--;
        return Type.ANY;
    }

    @Override
    public Type visit(ImportStatement node) {
        for (ImportedClassNode importedClass : node.getImportedClasses()) {
            Symbol symbol = new Symbol(importedClass.getClassName(),
                    Symbol.SymbolType.IMPORT,
                    importedClass.getLineNumber(), importedClass.getColumnNumber());
            symbol.setDataType(node.getModuleName());
            currentScope.define(symbol);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(FunctionDefinitionNode node) {
        currentScope.define(node.getFunctionName(), Symbol.SymbolType.FUNCTION,
                node.getLineNumber(), node.getColumnNumber());
        registerSignature(node);

        for (DecoratorNode decorator : node.getDecorators()) {
            decorator.accept(this);
        }

        enterScope("function_" + node.getFunctionName());
        functionDepth++;

        for (ParameterNode parameter : node.getParameters()) {
            if (parameter instanceof StarArgsNode) {
                bind(parameter.getName(), Type.ANY);
            } else if (parameter instanceof KwargsNode) {
                bind(parameter.getName(), Type.dictOf(Type.ANY, Type.ANY));
            } else if (parameter instanceof KeywordOnlySeparator) {
                // bare * separator — no binding
            } else {
                Type parameterType = Type.ANY;
                if (parameter.hasDefault() && parameter.getDefaultValue() != null) {
                    parameterType = parameter.getDefaultValue().accept(this);
                }
                bind(parameter.getName(), parameterType);
            }
        }

        for (StatementNode bodyStatement : node.getBodyStatements()) {
            bodyStatement.accept(this);
        }

        functionDepth--;
        exitScope();
        return Type.FUNCTION;
    }

    @Override
    public Type visit(ClassDefinitionNode node) {
        Symbol classSymbol = new Symbol(node.getClassName(), Symbol.SymbolType.CLASS,
                node.getLineNumber(), node.getColumnNumber());
        classSymbol.setDataType("class");
        currentScope.define(classSymbol);

        for (DecoratorNode decorator : node.getDecorators()) {
            decorator.accept(this);
        }

        enterScope("class_" + node.getClassName());
        for (StatementNode bodyStatement : node.getBodyStatements()) {
            bodyStatement.accept(this);
        }
        exitScope();
        return Type.CLASS;
    }

    @Override
    public Type visit(DecoratorNode node) {
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
        }
        return Type.ANY;
    }

    // ==================== Expressions ====================

    @Override
    public Type visit(VariableNode node) {
        if (!isDefined(node.getName())) {
            error(SemanticError.Code.UNDEFINED_VARIABLE,
                    "name '" + node.getName() + "' is not defined",
                    node.getLineNumber(), node.getColumnNumber());
            return Type.ANY;
        }
        return lookupType(node.getName());
    }

    @Override
    public Type visit(NumberIntegerNode node) {
        return Type.INT;
    }

    @Override
    public Type visit(NumberDoubleNode node) {
        return Type.FLOAT;
    }

    @Override
    public Type visit(StringNode node) {
        return Type.STRING;
    }

    @Override
    public Type visit(BooleanNode node) {
        return Type.BOOL;
    }

    @Override
    public Type visit(NoneNode node) {
        return Type.NONE;
    }

    @Override
    public Type visit(ListNode node) {
        Type elementType = Type.ANY;
        boolean first = true;
        for (ExpressionNode item : node.getElements()) {
            Type itemType = item.accept(this);
            elementType = first ? itemType : elementType.union(itemType);
            first = false;
        }
        return Type.listOf(elementType);
    }

    @Override
    public Type visit(DictNode node) {
        Type keyType = Type.ANY;
        Type valueType = Type.ANY;
        boolean first = true;
        for (DictEntryNode entry : node.getElements()) {
            Type key = entry.getKey().accept(this);
            Type value = entry.getValue().accept(this);
            if (first) {
                keyType = key;
                valueType = value;
                first = false;
            } else {
                keyType = keyType.union(key);
                valueType = valueType.union(value);
            }
        }
        return Type.dictOf(keyType, valueType);
    }

    @Override
    public Type visit(DictEntryNode node) {
        node.getKey().accept(this);
        return node.getValue().accept(this);
    }

    @Override
    public Type visit(ListComprehensionNode node) {
        Type iterableType = node.getIterable().accept(this);
        if (iterableType.isKnown() && !iterableType.isIterable()) {
            error(SemanticError.Code.TYPE_ERROR,
                    repr(iterableType) + " object is not iterable",
                    node.getLineNumber(), node.getColumnNumber());
        }

        enterScope("comprehension_" + node.getLineNumber());
        bind(node.getTargetVariable(), iterableType.iterationElement());
        Type elementType = node.getElement().accept(this);
        if (node.hasFilter()) {
            node.getFilterCondition().accept(this);
        }
        exitScope();

        return Type.listOf(elementType);
    }

    @Override
    public Type visit(IndexAccessNode node) {
        Type containerType = node.getContainer().accept(this);
        Type keyType = node.getKey().accept(this);

        Type.Kind kind = containerType.kind();
        if (kind == Type.Kind.INT || kind == Type.Kind.FLOAT
                || kind == Type.Kind.BOOL || kind == Type.Kind.NONE) {
            error(SemanticError.Code.TYPE_ERROR,
                    repr(containerType) + " object is not subscriptable",
                    node.getLineNumber(), node.getColumnNumber());
            return Type.ANY;
        }
        if (kind == Type.Kind.LIST) {
            if (keyType.kind() == Type.Kind.STRING) {
                error(SemanticError.Code.TYPE_MISMATCH,
                        "list indices must be integers or slices, not 'str'",
                        node.getLineNumber(), node.getColumnNumber());
                return Type.ANY;
            }
            if (keyType.kind() == Type.Kind.NONE) {
                error(SemanticError.Code.TYPE_MISMATCH,
                        "list indices must be integers or slices, not 'NoneType'",
                        node.getLineNumber(), node.getColumnNumber());
                return Type.ANY;
            }
            return containerType.elementType() != null ? containerType.elementType() : Type.ANY;
        }
        if (kind == Type.Kind.STRING) {
            if (keyType.kind() == Type.Kind.STRING) {
                error(SemanticError.Code.TYPE_MISMATCH,
                        "string indices must be integers, not 'str'",
                        node.getLineNumber(), node.getColumnNumber());
                return Type.ANY;
            }
            return Type.STRING;
        }
        if (kind == Type.Kind.DICT) {
            return containerType.valueType() != null ? containerType.valueType() : Type.ANY;
        }
        return Type.ANY;
    }

    @Override
    public Type visit(DotAccessNode node) {
        Type objectType = node.getObject().accept(this);
        Type.Kind kind = objectType.kind();
        if (kind == Type.Kind.INT || kind == Type.Kind.FLOAT
                || kind == Type.Kind.BOOL || kind == Type.Kind.NONE) {
            error(SemanticError.Code.TYPE_ERROR,
                    repr(objectType) + " object has no attribute '" + node.getProperty() + "'",
                    node.getLineNumber(), node.getColumnNumber());
        }
        return Type.ANY;
    }

    @Override
    public Type visit(FunctionCallNode node) {
        ExpressionNode callee = node.getCallee();
        Type calleeType = (callee != null) ? callee.accept(this) : Type.ANY;

        if (calleeType.isKnown() && !calleeType.isCallable()) {
            error(SemanticError.Code.TYPE_ERROR,
                    repr(calleeType) + " object is not callable",
                    node.getLineNumber(), node.getColumnNumber());
        }

        int positional = 0;
        List<String> namedArguments = new ArrayList<>();
        for (ExpressionNode argument : node.getParameters()) {
            if (argument instanceof KeywordArgumentNode kw) {
                if (kw.getName() != null) {
                    namedArguments.add(kw.getName());
                } else {
                    positional++;
                }
                if (kw.getValue() != null) {
                    kw.getValue().accept(this);
                }
            } else {
                positional++;
                argument.accept(this);
            }
        }

        if (callee instanceof VariableNode varCallee) {
            String calleeName = varCallee.getName();
            FunctionSignature signature = signatures.get(calleeName);
            if (signature != null) {
                if (positional > signature.maxPositional()) {
                    error(SemanticError.Code.TYPE_MISMATCH,
                            calleeName + "() takes " + signature.positionalSlots()
                                    + " positional argument(s) but " + positional + " were given",
                            node.getLineNumber(), node.getColumnNumber());
                }
                for (String name : namedArguments) {
                    if (!signature.allowsKeyword(name)) {
                        error(SemanticError.Code.TYPE_MISMATCH,
                                calleeName + "() got an unexpected keyword argument '" + name + "'",
                                node.getLineNumber(), node.getColumnNumber());
                    }
                }
            }
            Type conversion = conversionResultType(calleeName);
            if (conversion != null) {
                return conversion;
            }
            if ("len".equals(calleeName)) {
                return Type.INT;
            }
            if ("range".equals(calleeName)) {
                return Type.listOf(Type.INT);
            }
            if ("list".equals(calleeName)) {
                return Type.listOf(Type.ANY);
            }
            if ("dict".equals(calleeName)) {
                return Type.dictOf(Type.ANY, Type.ANY);
            }
        }

        return Type.ANY;
    }

    private Type conversionResultType(String calleeName) {
        return switch (calleeName) {
            case "int" -> Type.INT;
            case "float" -> Type.FLOAT;
            case "str" -> Type.STRING;
            case "bool" -> Type.BOOL;
            case "list" -> Type.listOf(Type.ANY);
            case "dict" -> Type.dictOf(Type.ANY, Type.ANY);
            case "round" -> Type.INT;
            case "range" -> Type.listOf(Type.INT);
            default -> null;
        };
    }

    @Override
    public Type visit(RenderTemplateNode node) {
        for (KeywordArgumentNode contextVariable : node.getContextVariables()) {
            if (contextVariable.getValue() != null) {
                contextVariable.getValue().accept(this);
            }
        }
        return Type.STRING;
    }

    @Override
    public Type visit(KeywordArgumentNode node) {
        if (node.getValue() != null) {
            return node.getValue().accept(this);
        }
        return Type.ANY;
    }

    // ==================== Operations ====================

    @Override
    public Type visit(BinaryOpNode node) {
        Type leftType = node.getLeft().accept(this);
        Type rightType = node.getRight().accept(this);
        Type.Kind leftKind = leftType.kind();
        Type.Kind rightKind = rightType.kind();

        switch (node.getOperator()) {
            case ADD -> {
                if (leftKind == Type.Kind.ANY || rightKind == Type.Kind.ANY) {
                    return leftType.union(rightType);
                }
                if (leftType.isNumeric() && rightType.isNumeric()) {
                    return leftType.union(rightType);
                }
                if (leftKind == Type.Kind.STRING && rightKind == Type.Kind.STRING) {
                    return Type.STRING;
                }
                if (leftKind == Type.Kind.LIST && rightKind == Type.Kind.LIST) {
                    return leftType;
                }
                error(SemanticError.Code.TYPE_MISMATCH,
                        "unsupported operand type(s) for +: " + repr(leftType)
                                + " and " + repr(rightType),
                        node.getLineNumber(), node.getColumnNumber());
                return Type.ANY;
            }
            case SUB, DIV, POW -> {
                if (leftKind == Type.Kind.ANY || rightKind == Type.Kind.ANY) {
                    return leftType.union(rightType);
                }
                if (!leftType.isNumeric() || !rightType.isNumeric()) {
                    String symbol = node.getOperator().getSymbol();
                    error(SemanticError.Code.TYPE_MISMATCH,
                            "unsupported operand type(s) for " + symbol + ": "
                                    + repr(leftType) + " and " + repr(rightType),
                            node.getLineNumber(), node.getColumnNumber());
                    return Type.ANY;
                }
                return node.getOperator() == BinaryOpNode.Operator.DIV
                        ? Type.FLOAT : leftType.union(rightType);
            }
            case MUL -> {
                if (leftKind == Type.Kind.ANY || rightKind == Type.Kind.ANY) {
                    return leftType.union(rightType);
                }
                if (leftType.isNumeric() && rightType.isNumeric()) {
                    return leftType.union(rightType);
                }
                if (leftKind == Type.Kind.STRING && isIntLike(rightType)) {
                    return Type.STRING;
                }
                if (rightKind == Type.Kind.STRING && isIntLike(leftType)) {
                    return Type.STRING;
                }
                if (leftKind == Type.Kind.LIST && isIntLike(rightType)) {
                    return leftType;
                }
                if (rightKind == Type.Kind.LIST && isIntLike(leftType)) {
                    return rightType;
                }
                error(SemanticError.Code.TYPE_MISMATCH,
                        "unsupported operand type(s) for *: " + repr(leftType)
                                + " and " + repr(rightType),
                        node.getLineNumber(), node.getColumnNumber());
                return Type.ANY;
            }
            default -> {
                return Type.ANY;
            }
        }
    }

    @Override
    public Type visit(ComparisonNode node) {
        Type leftType = node.getLeft().accept(this);
        Type rightType = node.getRight().accept(this);

        ComparisonNode.Operator operator = node.getOperator();
        if (operator != ComparisonNode.Operator.EQ
                && operator != ComparisonNode.Operator.NEQ
                && leftType.isKnown() && rightType.isKnown()) {
            boolean orderable = (leftType.isNumeric() && rightType.isNumeric())
                    || (leftType.kind() == Type.Kind.STRING && rightType.kind() == Type.Kind.STRING)
                    || (leftType.kind() == Type.Kind.LIST && rightType.kind() == Type.Kind.LIST);
            if (!orderable) {
                error(SemanticError.Code.TYPE_MISMATCH,
                        "'" + operator.getSymbol() + "' not supported between instances of "
                                + repr(leftType) + " and " + repr(rightType),
                        node.getLineNumber(), node.getColumnNumber());
            }
        }
        return Type.BOOL;
    }

    @Override
    public Type visit(LogicalOpNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return Type.ANY;
    }

    @Override
    public Type visit(UnaryOpNode node) {
        Type operandType = node.getOperand().accept(this);
        if (node.getOperator() == UnaryOpNode.Operator.NOT) {
            return Type.BOOL;
        }
        if (operandType.isKnown() && !operandType.isNumeric()) {
            error(SemanticError.Code.TYPE_MISMATCH,
                    "bad operand type for unary -: " + repr(operandType),
                    node.getLineNumber(), node.getColumnNumber());
        }
        return operandType.isNumeric() ? operandType : Type.ANY;
    }

    // ==================== Signature registry ====================

    private void registerSignature(FunctionDefinitionNode node) {
        FunctionSignature signature = new FunctionSignature();
        for (ParameterNode parameter : node.getParameters()) {
            if (parameter instanceof StarArgsNode) {
                signature.hasStarArgs = true;
            } else if (parameter instanceof KwargsNode) {
                signature.hasKwargs = true;
            } else if (parameter instanceof KeywordOnlySeparator) {
                // separator only
            } else {
                signature.parameters.add(parameter.getName());
                if (!parameter.hasDefault()) {
                    signature.required++;
                }
            }
        }
        signatures.put(node.getFunctionName(), signature);
    }

    /**
     * Arity contract of a user-defined function, gathered at definition time.
     */
    private static final class FunctionSignature {
        private final List<String> parameters = new ArrayList<>();
        private int required;
        private boolean hasStarArgs;
        private boolean hasKwargs;

        private int maxPositional() {
            return hasStarArgs ? Integer.MAX_VALUE : parameters.size();
        }

        private int positionalSlots() {
            return hasStarArgs ? required : parameters.size();
        }

        private boolean allowsKeyword(String name) {
            if (hasKwargs) {
                return true;
            }
            return parameters.contains(name);
        }
    }
}