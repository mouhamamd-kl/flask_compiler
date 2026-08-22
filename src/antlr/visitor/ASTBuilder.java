package antlr.visitor;


import antlr.ast.node.ASTNode;
import antlr.ast.python.ProgramNode;
import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.ListComprehensionNode;
import antlr.ast.python.expressions.access.*;
import antlr.ast.python.expressions.literals.*;
import antlr.ast.python.expressions.operations.BinaryOpNode;
import antlr.ast.python.expressions.operations.ComparisonNode;
import antlr.ast.python.expressions.operations.LogicalOpNode;
import antlr.ast.python.expressions.operations.UnaryOpNode;
import antlr.ast.python.parameters.*;
import antlr.ast.python.statements.*;
import antlr.gen.python.pythonParser;
import antlr.gen.python.pythonParserBaseVisitor;
import antlr.semantic.Builtins;
import antlr.symbol.Symbol;
import antlr.symbol.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends pythonParserBaseVisitor<ASTNode> {

    // جدول الرموز
    private final SymbolTable symbolTable;
    private SymbolTable currentScope;

    // أخطاء التحليل الدلالي
    private final List<String> semanticErrors = new ArrayList<>();

    public ASTBuilder() {
        this.symbolTable = new SymbolTable();
        this.currentScope = symbolTable;
        Builtins.defineIn(symbolTable);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // ==================== الأخطاء الدلالية ====================
    public List<String> getSemanticErrors() {
        return semanticErrors;
    }

    private void reportSemanticError(int line, int col, String message) {
        semanticErrors.add(String.format("Line %d:%d - Semantic error: %s", line, col, message));
    }

    // ==================== ROOT ====================
    @Override
    public ASTNode visitRoot(pythonParser.RootContext ctx) {
        int line = ctx.getStart() != null ? ctx.getStart().getLine() : 1;

        ProgramNode program = new ProgramNode(line);

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                program.addStatement((StatementNode) stmtNode);
            }
        }

        return program;
    }

    // ==================== STATEMENTS ====================
    @Override
    public ASTNode visitAssignStatement(pythonParser.AssignStatementContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public ASTNode visitAssignment(pythonParser.AssignmentContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode targetNode = (ExpressionNode) visit(ctx.target);
        ExpressionNode valueNode = (ExpressionNode) visit(ctx.value);

        if (targetNode instanceof VariableNode) {
            String varName = ((VariableNode) targetNode).getName();
            Symbol symbol = new Symbol(varName, Symbol.SymbolType.VARIABLE, line, col);
            symbol.setValue(valueNode.toValueString());
            currentScope.define(symbol);
        }

        return new AssignmentNode(targetNode, valueNode, line, col);
    }

    @Override
    public ASTNode visitPrintStatement(pythonParser.PrintStatementContext ctx) {
        return visit(ctx.printAction());
    }

    @Override
    public ASTNode visitPrintAction(pythonParser.PrintActionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode exprNode = (ExpressionNode) visit(ctx.expr());
        return new PrintNode(exprNode, line, col);
    }

    @Override
    public ASTNode visitReturnStmt(pythonParser.ReturnStmtContext ctx) {
        return visit(ctx.returnStatement());
    }

    @Override
    public ASTNode visitReturnStatement(pythonParser.ReturnStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode value = null;
        if (ctx.expr() != null) {
            value = (ExpressionNode) visit(ctx.expr());
        }

        return new ReturnNode(value, line, col);
    }

    @Override
    public ASTNode visitExprStatement(pythonParser.ExprStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode exprNode = (ExpressionNode) visit(ctx.expr());
        return new ExpressionStatementNode(exprNode, line, col);
    }

    @Override
    public ASTNode visitImportStatement(pythonParser.ImportStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        String moduleName = ctx.importHeader().module.getText();
        ImportStatement importStmt = new ImportStatement(moduleName, line, col);

        for (pythonParser.ImportedClassesContext classCtx : ctx.importHeader().importedClasses()) {
            String className = classCtx.NAME().getText();
            ImportedClassNode classNode = new ImportedClassNode(className,
                    classCtx.getStart().getLine(),
                    classCtx.getStart().getCharPositionInLine());
            importStmt.addImportedClass(classNode);

            // تعريف الاسم المستورد كرمز في النطاق الحالي
            Symbol importedSymbol = new Symbol(className, Symbol.SymbolType.IMPORT,
                    classCtx.getStart().getLine(),
                    classCtx.getStart().getCharPositionInLine());
            importedSymbol.setDataType(moduleName);
            currentScope.define(importedSymbol);
        }

        return importStmt;
    }

    @Override
    public ASTNode visitBlock(pythonParser.BlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        BlockNode block = new BlockNode(line, col);

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                block.addStatement((StatementNode) stmtNode);
            }
        }

        return block;
    }

    // ==================== CONTROL FLOW ====================
    @Override
    public ASTNode visitIfStatement(pythonParser.IfStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        List<pythonParser.ConditionContext> conditions = ctx.condition();
        List<pythonParser.BlockContext> blocks = ctx.block();

        // Main if condition
        ExpressionNode mainCondition = (ExpressionNode) visit(conditions.getFirst());

        // If block
        currentScope = currentScope.enterScope("if_block_" + line);
        BlockNode ifBlock = (BlockNode) visit(blocks.getFirst());
        currentScope = currentScope.exitScope();

        // Elif conditions and blocks
        List<ExpressionNode> elifConditions = new ArrayList<>();
        List<BlockNode> elifBlocks = new ArrayList<>();

        int elifCount = conditions.size() - 1;
        for (int i = 0; i < elifCount; i++) {
            ExpressionNode elifCond = (ExpressionNode) visit(conditions.get(i + 1));
            elifConditions.add(elifCond);

            currentScope = currentScope.enterScope("elif_block_" + line + "_" + i);
            BlockNode elifBlock = (BlockNode) visit(blocks.get(i + 1));
            currentScope = currentScope.exitScope();
            elifBlocks.add(elifBlock);
        }

        // Else block (if exists)
        BlockNode elseBlock = null;
        if (blocks.size() > conditions.size()) {
            currentScope = currentScope.enterScope("else_block_" + line);
            elseBlock = (BlockNode) visit(blocks.getLast());
            currentScope = currentScope.exitScope();
        }

        return new IfStatementNode(mainCondition, ifBlock,
                elifConditions, elifBlocks,
                elseBlock, line, col);
    }

    // ==================== LOOP STATEMENTS ====================
    @Override
    public ASTNode visitForStmt(pythonParser.ForStmtContext ctx) {
        return visit(ctx.forStatement());
    }

    @Override
    public ASTNode visitForStatement(pythonParser.ForStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        String target = ctx.target.getText();
        ExpressionNode iterable = (ExpressionNode) visit(ctx.iterable);

        currentScope = currentScope.enterScope("for_block_" + line);
        BlockNode body = (BlockNode) visit(ctx.block());
        currentScope = currentScope.exitScope();

        return new ForStatementNode(target, iterable, body, line, col);
    }

    @Override
    public ASTNode visitWhileStmt(pythonParser.WhileStmtContext ctx) {
        return visit(ctx.whileStatement());
    }

    @Override
    public ASTNode visitWhileStatement(pythonParser.WhileStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode condition = (ExpressionNode) visit(ctx.condition());

        currentScope = currentScope.enterScope("while_block_" + line);
        BlockNode body = (BlockNode) visit(ctx.block());
        currentScope = currentScope.exitScope();

        return new WhileStatementNode(condition, body, line, col);
    }

    @Override
    public ASTNode visitBreakStmt(pythonParser.BreakStmtContext ctx) {
        return visit(ctx.breakStatement());
    }

    @Override
    public ASTNode visitBreakStatement(pythonParser.BreakStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BreakNode(line, col);
    }

    @Override
    public ASTNode visitContinueStmt(pythonParser.ContinueStmtContext ctx) {
        return visit(ctx.continueStatement());
    }

    @Override
    public ASTNode visitContinueStatement(pythonParser.ContinueStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new ContinueNode(line, col);
    }

    // ==================== CONDITIONS ====================
    @Override
    public ASTNode visitAndCondition(pythonParser.AndConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);
        return new LogicalOpNode(left, LogicalOpNode.Operator.AND, right, line, col);
    }

    @Override
    public ASTNode visitOrCondition(pythonParser.OrConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);
        return new LogicalOpNode(left, LogicalOpNode.Operator.OR, right, line, col);
    }

    @Override
    public ASTNode visitNotCondition(pythonParser.NotConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode operand = (ExpressionNode) visit(ctx.condition());
        return new UnaryOpNode(UnaryOpNode.Operator.NOT, operand, line, col);
    }

    @Override
    public ASTNode visitParenCondition(pythonParser.ParenConditionContext ctx) {
        return visit(ctx.condition());
    }

    @Override
    public ASTNode visitCompareCondition(pythonParser.CompareConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);
        String opStr = ctx.comparisonOp().getText();
        return new ComparisonNode(left, opStr, right, line, col);
    }

    @Override
    public ASTNode visitTrueCondition(pythonParser.TrueConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(true, line, col);
    }

    @Override
    public ASTNode visitFalseCondition(pythonParser.FalseConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(false, line, col);
    }

    // ==================== DEFINITIONS ====================
    @Override
    public ASTNode visitClassDefStatement(pythonParser.ClassDefStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String className = ctx.classDefinition().name.getText();
        ClassDefinitionNode definitionNode = new ClassDefinitionNode(className, line, col);

        // الديكوريترات
        for (pythonParser.DecoratorContext decoratorCtx : ctx.classDefinition().decorator()) {
            ASTNode decoratorNode = visit(decoratorCtx);
            if (decoratorNode instanceof DecoratorNode dec) {
                definitionNode.addDecorator(dec);
            }
        }

        // تعريف اسم الصنف كرمز في النطاق الحالي
        Symbol classSymbol = new Symbol(className, Symbol.SymbolType.VARIABLE, line, col);
        classSymbol.setDataType("class");
        currentScope.define(classSymbol);

        currentScope = currentScope.enterScope("class_" + className);

        // Class body
        for (pythonParser.StatementContext stmtCtx : ctx.classDefinition().block().statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                definitionNode.addStatement((StatementNode) stmtNode);
            }
        }
        currentScope = currentScope.exitScope();

        return definitionNode;
    }

    @Override
    public ASTNode visitFunctionDef(pythonParser.FunctionDefContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String functionName = ctx.NAME().getText();
        FunctionDefinitionNode definitionNode = new FunctionDefinitionNode(functionName, line, col);

        // الديكوريترات
        for (pythonParser.DecoratorContext decoratorCtx : ctx.decorator()) {
            ASTNode decoratorNode = visit(decoratorCtx);
            if (decoratorNode instanceof DecoratorNode dec) {
                definitionNode.addDecorator(dec);
            }
        }

        // تعريف اسم الدالة كرمز في النطاق الحالي
        currentScope.define(functionName, Symbol.SymbolType.FUNCTION, line, col);

        currentScope = currentScope.enterScope("function_" + functionName);

        // Function body
        for (pythonParser.StatementContext stmtCtx : ctx.block().statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                definitionNode.addStatement((StatementNode) stmtNode);
            }
        }

        // 1. Regular params
        if (ctx.params() != null && ctx.params().regularParams() != null
                && !ctx.params().regularParams().kwParam().isEmpty()) {
            for (pythonParser.KwParamContext ctxx : ctx.params().regularParams().kwParam()) {
                ASTNode stmtNode = visit(ctxx);
                if (stmtNode instanceof RegularParamNode paramNode) {
                    definitionNode.addParameter(paramNode);
                }
            }
        }

        // 2. *args or bare *
        if (ctx.params() != null && ctx.params().starParam() != null) {
            ASTNode starNode = visit(ctx.params().starParam());
            if (starNode instanceof ParameterNode paramNode) {
                definitionNode.addParameter(paramNode);
            }
        }

        // 3. Keyword-only params
        if (ctx.params() != null && ctx.params().kwOnlyParams() != null) {
            for (pythonParser.KwParamContext kwCtx : ctx.params().kwOnlyParams().kwParam()) {
                int kwline = kwCtx.getStart().getLine();
                int kwcol = kwCtx.getStart().getCharPositionInLine();
                String name = kwCtx.name.getText();
                KeywordOnlyParamNode kwNode = new KeywordOnlyParamNode(name, kwline, kwcol);
                if (kwCtx.defaultVal != null) {
                    ExpressionNode defaultVal = (ExpressionNode) visit(kwCtx.defaultVal);
                    kwNode.setDefaultValue(defaultVal);
                }
                definitionNode.addParameter(kwNode);
            }
        }

        // 4. **kwargs
        if (ctx.params() != null && ctx.params().kwargsParam() != null) {
            ASTNode kwargsNode = visit(ctx.params().kwargsParam());
            if (kwargsNode instanceof ParameterNode paramNode) {
                definitionNode.addParameter(paramNode);
            }
        }
        currentScope = currentScope.exitScope();

        return definitionNode;
    }

    @Override
    public ASTNode visitDecorator(pythonParser.DecoratorContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        DecoratorNode node = new DecoratorNode(line, col);
        ExpressionNode exprNode = (ExpressionNode) visit(ctx.expr());
        node.setExpression(exprNode);
        return node;
    }

    @Override
    public ASTNode visitKwParam(pythonParser.KwParamContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String name = ctx.name.getText();
        RegularParamNode regularParamNode = new RegularParamNode(name, line, col);
        if (ctx.defaultVal != null) {
            ExpressionNode defaultValueExpr = (ExpressionNode) visit(ctx.defaultVal);
            regularParamNode.setDefaultValue(defaultValueExpr);
        }
        return regularParamNode;
    }

    @Override
    public ASTNode visitStarParam(pythonParser.StarParamContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        if (ctx.starArgs != null) {
            String name = ctx.starArgs.getText();
            return new StarArgsNode(name, line, col);
        } else {
            return new KeywordOnlySeparator(line, col);
        }
    }

    @Override
    public ASTNode visitKwargsParam(pythonParser.KwargsParamContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String name = ctx.kwargs.getText();
        return new KwargsNode(name, line, col);
    }

    // ==================== DATA STRUCTURES ====================
    @Override
    public ASTNode visitListExpr(pythonParser.ListExprContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ListNode listNode = new ListNode(line, col);
        for (pythonParser.ExprContext elem : ctx.list().expr()) {
            listNode.addElement((ExpressionNode) visit(elem));
        }
        return listNode;
    }

    @Override
    public ASTNode visitListCompExpr(pythonParser.ListCompExprContext ctx) {
        return visit(ctx.listComprehension());
    }

    @Override
    public ASTNode visitListComprehension(pythonParser.ListComprehensionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode element = (ExpressionNode) visit(ctx.element);
        String target = ctx.target.getText();
        ExpressionNode iterable = (ExpressionNode) visit(ctx.iterable);

        ExpressionNode filter = null;
        if (ctx.filterCondition != null) {
            filter = (ExpressionNode) visit(ctx.filterCondition);
        }

        return new ListComprehensionNode(element, target, iterable, filter, line, col);
    }

    @Override
    public ASTNode visitDict(pythonParser.DictContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        DictNode dictNode = new DictNode(line, col);
        for (pythonParser.DictEntryContext elem : ctx.dictEntry()) {
            dictNode.addElement((DictEntryNode) visit(elem));
        }
        return dictNode;
    }

    @Override
    public ASTNode visitDictEntry(pythonParser.DictEntryContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        DictEntryNode dictEntryNode = new DictEntryNode(line, col);
        ExpressionNode keyExpression = (ExpressionNode) visit(ctx.key);
        ExpressionNode valueExpression = (ExpressionNode) visit(ctx.value);
        dictEntryNode.addKey(keyExpression);
        dictEntryNode.addValue(valueExpression);
        return dictEntryNode;
    }

    @Override
    public ASTNode visitDICTIONARY(pythonParser.DICTIONARYContext ctx) {
        return visit(ctx.dict());
    }

    // ==================== EXPRESSIONS ====================
    // Pass-through rules
    @Override
    public ASTNode visitAdditivePassThrough(pythonParser.AdditivePassThroughContext ctx) {
        return visit(ctx.multiplicativeExpr());
    }

    @Override
    public ASTNode visitMultiplicativePassThrough(pythonParser.MultiplicativePassThroughContext ctx) {
        return visit(ctx.powerExpr());
    }

    @Override
    public ASTNode visitPowerPassThrough(pythonParser.PowerPassThroughContext ctx) {
        return visit(ctx.postfixExpr());
    }

    @Override
    public ASTNode visitPostfixPassThrough(pythonParser.PostfixPassThroughContext ctx) {
        return visit(ctx.primaryExpr());
    }

    // Binary operations
    @Override
    public ASTNode visitEXPONENT_OP(pythonParser.EXPONENT_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode base = (ExpressionNode) visit(ctx.base);
        ExpressionNode exp = (ExpressionNode) visit(ctx.exp);
        return new BinaryOpNode(base, BinaryOpNode.Operator.POW, exp, line, col);
    }

    @Override
    public ASTNode visitMUL_DIV_OP(pythonParser.MUL_DIV_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);
        BinaryOpNode.Operator op = ctx.STAR() != null
                ? BinaryOpNode.Operator.MUL : BinaryOpNode.Operator.DIV;
        return new BinaryOpNode(left, op, right, line, col);
    }

    @Override
    public ASTNode visitSUM_SUB_OP(pythonParser.SUM_SUB_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);
        BinaryOpNode.Operator op = ctx.PLUS() != null
                ? BinaryOpNode.Operator.ADD : BinaryOpNode.Operator.SUB;
        return new BinaryOpNode(left, op, right, line, col);
    }

    // Primary & Access expressions
    @Override
    public ASTNode visitNUM(pythonParser.NUMContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        if (ctx.int_ != null) {
            int value = Integer.parseInt(ctx.int_.getText());
            return new NumberIntegerNode(value, line, col);
        } else {
            double value = Double.parseDouble(ctx.double_.getText());
            return new NumberDoubleNode(value, line, col);
        }
    }

    @Override
    public ASTNode visitSTRING_LITERAL(pythonParser.STRING_LITERALContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new StringNode(ctx.STRING().getText(), line, col);
    }

    @Override
    public ASTNode visitTrueLiteral(pythonParser.TrueLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(true, line, col);
    }

    @Override
    public ASTNode visitFalseLiteral(pythonParser.FalseLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(false, line, col);
    }

    @Override
    public ASTNode visitNoneLiteral(pythonParser.NoneLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new NoneNode(line, col);
    }

    @Override
    public ASTNode visitVAR(pythonParser.VARContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String name = ctx.NAME().getText();
        return new VariableNode(name, line, col);
    }

    @Override
    public ASTNode visitParenExpr(pythonParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitINDEX_ACCESS(pythonParser.INDEX_ACCESSContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode container = (ExpressionNode) visit(ctx.container);
        ExpressionNode key = (ExpressionNode) visit(ctx.key);

        IndexAccessNode node = new IndexAccessNode(line, col);
        node.addContainer(container);
        node.addKey(key);
        return node;
    }

    @Override
    public ASTNode visitDotAccessExpr(pythonParser.DotAccessExprContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode object = (ExpressionNode) visit(ctx.object);
        String property = ctx.property.getText();

        DotAccessNode node = new DotAccessNode(line, col);
        node.addObject(object);
        node.addProperty(property);
        return node;
    }

    @Override
    public ASTNode visitCallExpr(pythonParser.CallExprContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        FunctionCallNode node = new FunctionCallNode(line, col);
        ExpressionNode callee = (ExpressionNode) visit(ctx.callee);
        node.setCallee(callee);

        for (pythonParser.ArgumentContext argCtx : ctx.argument()) {
            ExpressionNode arg = (ExpressionNode) visit(argCtx);
            node.addParameter(arg);
        }

        // التحقق الدلالي: الدالة المستدعاة يجب أن تكون معرفة أو مستوردة
        if (callee instanceof VariableNode varCallee) {
            String calleeName = varCallee.getName();
            Symbol resolved = currentScope.resolve(calleeName);
            if (resolved == null) {
                reportSemanticError(line, col,
                        "'" + calleeName + "' is not defined (is it imported or declared?)");
                return node;
            }

            // استدعاء خاص لـ render_template
            if ("render_template".equals(calleeName)) {
                List<ExpressionNode> parameters = node.getParameters();
                if (!parameters.isEmpty()
                        && parameters.getFirst() instanceof KeywordArgumentNode firstArg
                        && firstArg.getName() == null
                        && firstArg.getValue() instanceof StringNode templateArg) {
                    String rawTemplateName = templateArg.getValue();
                    String templateName = rawTemplateName.substring(1, rawTemplateName.length() - 1);

                    RenderTemplateNode renderTemplateNode = new RenderTemplateNode(templateName, line, col);
                    for (int i = 1; i < parameters.size(); i++) {
                        if (parameters.get(i) instanceof KeywordArgumentNode keywordArg) {
                            renderTemplateNode.addContextVariable(keywordArg);
                        }
                    }
                    return renderTemplateNode;
                }
            }
        }

        return node;
    }

    @Override
    public ASTNode visitArgument(pythonParser.ArgumentContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        KeywordArgumentNode node = new KeywordArgumentNode(line, col);
        if (ctx.name != null) {
            node.addName(ctx.name.getText());
        }
        if (ctx.value != null) {
            ExpressionNode arg = (ExpressionNode) visit(ctx.value);
            node.addValue(arg);
        }
        return node;
    }
}
