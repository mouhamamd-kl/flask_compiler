package antlr.generator.python;

import antlr.ast.node.ASTNode;
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
import antlr.ast.python.statements.AssignmentNode;
import antlr.ast.python.statements.BlockNode;
import antlr.ast.python.statements.BreakNode;
import antlr.ast.python.statements.ClassDefinitionNode;
import antlr.ast.python.statements.ContinueNode;
import antlr.ast.python.statements.ExpressionStatementNode;
import antlr.ast.python.statements.ForStatementNode;
import antlr.ast.python.statements.FunctionDefinitionNode;
import antlr.ast.python.statements.IfStatementNode;
import antlr.ast.python.statements.ImportedClassNode;
import antlr.ast.python.statements.ImportStatement;
import antlr.ast.python.statements.PrintNode;
import antlr.ast.python.statements.ReturnNode;
import antlr.ast.python.statements.WhileStatementNode;
import antlr.ast.visitor.ASTVisitorAdapter;
import antlr.generator.GenerationLogWriter;
import antlr.generator.GenerationLogWriter;
import antlr.generator.NamedCallHandler;
import antlr.generator.flask.FlaskPlugin;
import antlr.generator.flask.RenderCaptureService;
import antlr.generator.flask.RouteResolver;
import antlr.generator.python.builtins.Builtin;
import antlr.generator.python.builtins.Builtins;
import antlr.generator.runtime.BreakSignal;
import antlr.generator.runtime.ContinueSignal;
import antlr.generator.runtime.EvalSignal;
import antlr.generator.runtime.EvaluatorContext;
import antlr.generator.runtime.ReturnSignal;
import antlr.generator.runtime.ScopeStack;
import antlr.generator.runtime.ValueOps;
import antlr.runtime.values.MethodResult;
import antlr.runtime.values.RTBool;
import antlr.runtime.values.RTDict;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTFunction;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.generator.render.RTRenderRequest;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluates the data-preparation part of the Python program and captures
 * every render_template(...) call as a RTRenderRequest.
 *
 * Design notes:
 * - Function bodies are registered, not executed, at definition time.
 * - After the top-level statements run, each function whose body references
 *   render_template is invoked once (parameters bound to None) so that its
 *   render request is captured.
 * - Unknown externals (request, redirect, url_for, ...) resolve to
 *   RTUndefined whose truthiness is false; unknown calls degrade to None
 *   with a logged warning. Evaluation never crashes on unsupported code.
 *
 * Infrastructure lives in focused collaborators: {@link ScopeStack} and
 * {@link ValueOps} (shared runtime), {@link PythonOperators} (arith/compare),
 * {@link FunctionInvoker} (user calls), the {@code builtins} package and the
 * flask package ({@link FlaskPlugin}, route resolution, capture service).
 */
public class PythonEvaluator extends ASTVisitorAdapter<RTValue> {

    // ==================== state ====================
    private final GenerationLogWriter log;
    private final EvaluatorContext ctx;
    private final ScopeStack scopes = new ScopeStack();
    private final Map<String, RTFunction> functions = new LinkedHashMap<>();
    private final Map<String, Builtin> builtins;
    private final List<NamedCallHandler> namedHandlers;
    private final FlaskPlugin flask;
    private final FunctionInvoker invoker;

    public PythonEvaluator(GenerationLogWriter log) {
        this(log, List.of(), Builtins.create());
    }

    /**
     * @param extraHandlers   additional named-call handlers, consulted after
     *                        the flask plugin (first non-null result wins)
     * @param builtinRegistry the builtin function registry to use
     */
    public PythonEvaluator(GenerationLogWriter log, List<NamedCallHandler> extraHandlers,
                           Map<String, Builtin> builtinRegistry) {
        this.log = log;
        this.ctx = new EvaluatorContext(log);
        this.invoker = new FunctionInvoker(scopes, ctx, this::eval, this::execStatement);
        this.flask = new FlaskPlugin(new RenderCaptureService(ctx), ctx, this::eval);
        List<NamedCallHandler> handlers = new ArrayList<>();
        handlers.add(flask);
        handlers.addAll(extraHandlers);
        this.namedHandlers = List.copyOf(handlers);
        this.builtins = Map.copyOf(builtinRegistry);
    }

    // ==================== result ====================
    public static class EvaluationResult {
        public final Map<String, RTValue> globals;
        public final List<RTRenderRequest> renderRequests;
        public final Map<String, RTFunction> functions;

        EvaluationResult(Map<String, RTValue> globals,
                         List<RTRenderRequest> renderRequests,
                         Map<String, RTFunction> functions) {
            this.globals = globals;
            this.renderRequests = renderRequests;
            this.functions = functions;
        }
    }

    // ==================== entry point ====================
    public EvaluationResult run(ProgramNode program) {
        scopes.push();
        // Standard python entry-point convention; lets guards like
        // `if __name__ == '__main__'` evaluate without resolution warnings.
        scopes.peek().put("__name__", new RTString("__main__"));
        try {
            for (StatementNode st : program.getStatements()) {
                execStatement(st);
            }
        } catch (EvalSignal signal) {
            log.warn("Top-level break/continue/return ignored.");
        }
        invokeRenderFunctions();
        Map<String, RTValue> globals = new LinkedHashMap<>(scopes.peek());
        scopes.pop();

        log.info("Evaluation finished: " + globals.size() + " global variables, "
                + flask.captures().requests().size() + " render requests captured.");
        return new EvaluationResult(globals, flask.captures().requests(), functions);
    }

    /**
     * Invoke every function whose body contains a render_template(...) call
     * exactly once so the request is recorded.
     */
    private void invokeRenderFunctions() {
        for (RTFunction fn : functions.values()) {
            if (referencesRenderTemplate(fn.definition.getBodyStatements(), 0)) {
                log.info("Invoking function '" + fn.name + "' to capture its template rendering."
                        + (fn.routePath != null ? " (route: " + fn.routePath + ")" : ""));
                try {
                    // tag the default capture with its route so static URLs
                    // ("/", "/add") can be link-rewritten later
                    flask.captures().tagNextCapture(fn.routePath != null
                            ? new RenderCaptureService.PendingRouteInfo(fn.routePath, fn.routePath)
                            : null);
                    invoker.invoke(fn, new LinkedHashMap<>(), List.of());
                } catch (EvalSignal ignored) {
                    log.warn("Function '" + fn.name + "' ended with a stray break/continue/return.");
                } finally {
                    flask.captures().tagNextCapture(null);
                }

                // Dynamic routes: additionally capture one page per concrete
                // entity (e.g. /product/1, /product/2) so static output gets
                // real detail pages instead of dead links.
                RouteResolver.DynamicRoute dyn = RouteResolver.parseDynamicRoute(fn.routePath);
                if (dyn != null) {
                    List<RTValue> candidates =
                            RouteResolver.deriveRouteCandidates(dyn.paramName(), scopes.peek());
                    if (candidates.isEmpty()) {
                        log.info("Dynamic route " + fn.routePath + ": no candidate values found in globals;"
                                + " only the parameter-less page was captured.");
                        continue;
                    }
                    for (RTValue candidate : candidates) {
                        flask.captures().tagNextCapture(new RenderCaptureService.PendingRouteInfo(
                                dyn.prefix() + candidate.toDisplayString(), fn.routePath));
                        try {
                            LinkedHashMap<String, RTValue> kwargs = new LinkedHashMap<>();
                            kwargs.put(dyn.paramName(), candidate);
                            invoker.invoke(fn, kwargs, List.of());
                        } catch (EvalSignal ignored) {
                            log.warn("Function '" + fn.name + "' ended with a stray break/continue/return.");
                        } finally {
                            flask.captures().tagNextCapture(null);
                        }
                    }
                }
            }
        }
    }

    private boolean referencesRenderTemplate(List<StatementNode> statements, int depth) {
        if (depth > 32 || statements == null) {
            return false;
        }
        for (StatementNode st : statements) {
            if (containsRenderTemplate(st, depth)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsRenderTemplate(ASTNode node, int depth) {
        if (node == null || depth > 64) {
            return false;
        }
        if (node instanceof RenderTemplateNode) {
            return true;
        }
        List<ASTNode> children = node.getChildren();
        if (children == null) {
            return false;
        }
        for (ASTNode child : children) {
            if (containsRenderTemplate(child, depth + 1)) {
                return true;
            }
        }
        return false;
    }

    // ==================== execution plumbing ====================
    private void execStatement(StatementNode st) {
        if (st != null) {
            st.accept(this);
        }
    }

    private void execBlock(BlockNode block) {
        if (block != null) {
            for (StatementNode st : block.getStatements()) {
                execStatement(st);
            }
        }
    }

    private void execStatements(List<StatementNode> statements) {
        if (statements != null) {
            for (StatementNode st : statements) {
                execStatement(st);
            }
        }
    }

    private RTValue eval(ExpressionNode expr) {
        if (expr == null) {
            return RTNone.NONE;
        }
        RTValue value = expr.accept(this);
        return value == null ? RTNone.NONE : value;
    }

    private RTValue resolve(String name) {
        RTValue v = scopes.resolveOrNull(name);
        if (v != null) {
            return v;
        }
        warnOnce("unresolved:" + name, "Name '" + name + "' could not be resolved; treated as undefined.");
        return RTUndefined.UNDEFINED;
    }

    private void warnOnce(String key, String message) {
        ctx.warnOnce(key, message);
    }

    // ==================== statements ====================
    @Override
    public RTValue visit(ProgramNode node) {
        for (StatementNode st : node.getStatements()) {
            execStatement(st);
        }
        return null;
    }

    @Override
    public RTValue visit(AssignmentNode node) {
        ExpressionNode target = node.getTarget();
        RTValue value = eval(node.getValue());

        if (target instanceof VariableNode var) {
            scopes.peek().put(var.getName(), value);
        } else if (target instanceof IndexAccessNode idx) {
            setItem(eval(idx.getContainer()), eval(idx.getKey()), value, node.getLineNumber());
        } else if (target instanceof DotAccessNode dot) {
            RTValue container = eval(dot.getObject());
            if (container instanceof RTDict dict) {
                dict.put(dot.getProperty(), value);
            } else {
                log.warn("Line " + node.getLineNumber()
                        + ": attribute assignment is only supported on dicts; skipped.");
            }
        } else {
            log.warn("Line " + node.getLineNumber() + ": unsupported assignment target; skipped.");
        }
        return null;
    }

    @Override
    public RTValue visit(ExpressionStatementNode node) {
        eval(node.getExpression());
        return null;
    }

    @Override
    public RTValue visit(PrintNode node) {
        System.out.println("   [print] " + eval(node.getExpression()));
        return null;
    }

    @Override
    public RTValue visit(IfStatementNode node) {
        ExpressionNode cond = node.getCondition();
        if (isNameMainGuard(cond)) {
            log.info("Skipped `if __name__ == '__main__'` guard (server startup is not part of generation).");
            return null;
        }
        if (eval(cond).isTruthy()) {
            execBlock(node.getIfBlock());
            return null;
        }
        List<ExpressionNode> elifConds = node.getElifConditions();
        List<BlockNode> elifBlocks = node.getElifBlocks();
        if (elifConds != null && elifBlocks != null) {
            for (int i = 0; i < elifConds.size() && i < elifBlocks.size(); i++) {
                if (eval(elifConds.get(i)).isTruthy()) {
                    execBlock(elifBlocks.get(i));
                    return null;
                }
            }
        }
        execBlock(node.getElseBlock());
        return null;
    }

    private boolean isNameMainGuard(ExpressionNode cond) {
        if (!(cond instanceof ComparisonNode cmp) || cmp.getOperator() != ComparisonNode.Operator.EQ) {
            return false;
        }
        boolean forward = cmp.getLeft() instanceof VariableNode lv
                && "__name__".equals(lv.getName())
                && cmp.getRight() instanceof StringNode rs
                && "__main__".equals(unquote(rs.getValue()));
        boolean backward = cmp.getRight() instanceof VariableNode rv
                && "__name__".equals(rv.getName())
                && cmp.getLeft() instanceof StringNode ls
                && "__main__".equals(unquote(ls.getValue()));
        return forward || backward;
    }

    @Override
    public RTValue visit(ForStatementNode node) {
        String target = node.getTargetVariable();
        List<RTValue> items = iterate(eval(node.getIterable()));
        for (RTValue item : items) {
            scopes.peek().put(target, item);
            try {
                execBlock(node.getBody());
            } catch (BreakSignal b) {
                break;
            } catch (ContinueSignal c) {
                // next iteration
            }
        }
        return null;
    }

    @Override
    public RTValue visit(WhileStatementNode node) {
        int iterations = 0;
        while (eval(node.getCondition()).isTruthy()) {
            if (++iterations > 100000) {
                log.warn("While loop exceeded 100000 iterations; aborted.");
                break;
            }
            try {
                execBlock(node.getBody());
            } catch (BreakSignal b) {
                break;
            } catch (ContinueSignal c) {
                // next iteration
            }
        }
        return null;
    }

    @Override
    public RTValue visit(ReturnNode node) {
        throw new ReturnSignal(node.getValue() != null ? eval(node.getValue()) : RTNone.NONE);
    }

    @Override
    public RTValue visit(BreakNode node) {
        throw BreakSignal.BREAK;
    }

    @Override
    public RTValue visit(ContinueNode node) {
        throw ContinueSignal.CONTINUE;
    }

    @Override
    public RTValue visit(FunctionDefinitionNode node) {
        String routePath = RouteResolver.extractRoutePath(node);
        RTFunction fn = new RTFunction(node, routePath);
        functions.put(fn.name, fn);
        scopes.peek().put(fn.name, fn);
        log.info("Registered function '" + fn.name + "'"
                + (routePath != null ? " with route " + routePath : "")
                + " (" + node.getParameters().size() + " parameters).");
        return null;
    }

    @Override
    public RTValue visit(ClassDefinitionNode node) {
        warnOnce("class:" + node.getClassName(),
                "Class definitions are outside the project scope; '" + node.getClassName() + "' defined as None.");
        scopes.peek().put(node.getClassName(), RTNone.NONE);
        return null;
    }

    @Override
    public RTValue visit(ImportStatement node) {
        StringBuilder names = new StringBuilder();
        for (ImportedClassNode imported : node.getImportedClasses()) {
            names.append(imported.getClassName()).append(", ");
            scopes.peek().put(imported.getClassName(), RTUndefined.UNDEFINED);
        }
        if (names.length() > 0) {
            log.info("Import treated as external: " + names.substring(0, names.length() - 2)
                    + " (module '" + node.getModuleName() + "').");
        }
        return null;
    }

    // ==================== literals ====================
    @Override
    public RTValue visit(StringNode node) {
        return new RTString(unquote(node.getValue()));
    }

    /**
     * The python AST builder stores string literals as raw token text,
     * quotes included ('id' or "id"); strip them for semantic use.
     */
    private static String unquote(String s) {
        if (s != null && s.length() >= 2) {
            char first = s.charAt(0);
            char last = s.charAt(s.length() - 1);
            if ((first == '\'' || first == '"') && first == last) {
                return s.substring(1, s.length() - 1);
            }
        }
        return s;
    }

    @Override
    public RTValue visit(VariableNode node) {
        return resolve(node.getName());
    }

    @Override
    public RTValue visit(NumberIntegerNode node) {
        return new RTInt(node.getValue());
    }

    @Override
    public RTValue visit(NumberDoubleNode node) {
        return new RTFloat(node.getValue());
    }

    @Override
    public RTValue visit(BooleanNode node) {
        return RTBool.of(node.getValue());
    }

    @Override
    public RTValue visit(NoneNode node) {
        return RTNone.NONE;
    }

    @Override
    public RTValue visit(ListNode node) {
        List<RTValue> items = new ArrayList<>();
        for (ExpressionNode el : node.getElements()) {
            items.add(eval(el));
        }
        return new RTList(items);
    }

    @Override
    public RTValue visit(DictNode node) {
        RTDict dict = new RTDict();
        for (DictEntryNode entry : node.getElements()) {
            dict.put(display(eval(entry.getKey())), eval(entry.getValue()));
        }
        return dict;
    }

    @Override
    public RTValue visit(ListComprehensionNode node) {
        List<RTValue> result = new ArrayList<>();
        List<RTValue> items = iterate(eval(node.getIterable()));
        String target = node.getTargetVariable();
        scopes.push();
        try {
            for (RTValue item : items) {
                scopes.peek().put(target, item);
                if (node.getFilterCondition() == null || eval(node.getFilterCondition()).isTruthy()) {
                    result.add(eval(node.getElement()));
                }
            }
        } finally {
            scopes.pop();
        }
        return new RTList(result);
    }

    // ==================== operators ====================
    @Override
    public RTValue visit(BinaryOpNode node) {
        return PythonOperators.arith(node.getOperator(), eval(node.getLeft()), eval(node.getRight()),
                node.getLineNumber(), ctx);
    }

    @Override
    public RTValue visit(UnaryOpNode node) {
        RTValue operand = eval(node.getOperand());
        if (node.getOperator() == UnaryOpNode.Operator.NOT) {
            return RTBool.of(!operand.isTruthy());
        }
        if (operand instanceof RTInt i) {
            return new RTInt(-i.value);
        }
        if (operand instanceof RTFloat f) {
            return new RTFloat(-f.value);
        }
        log.warn("Unary minus on non-numeric operand produced None.");
        return RTNone.NONE;
    }

    @Override
    public RTValue visit(ComparisonNode node) {
        RTValue l = eval(node.getLeft());
        RTValue r = eval(node.getRight());
        return RTBool.of(PythonOperators.compare(node.getOperator(), l, r, ctx));
    }

    @Override
    public RTValue visit(LogicalOpNode node) {
        RTValue left = eval(node.getLeft());
        if (node.getOperator() == LogicalOpNode.Operator.AND) {
            return left.isTruthy() ? eval(node.getRight()) : left;
        }
        return left.isTruthy() ? left : eval(node.getRight());
    }

    // ==================== access ====================
    @Override
    public RTValue visit(IndexAccessNode node) {
        return getItem(eval(node.getContainer()), eval(node.getKey()), node.getLineNumber());
    }

    private RTValue getItem(RTValue container, RTValue key, int line) {
        if (container instanceof RTDict dict) {
            RTValue v = dict.get(key.toDisplayString());
            if (v == null) {
                warnOnce("key:" + key, "Line " + line + ": missing dict key '" + key.toDisplayString() + "'.");
                return RTUndefined.UNDEFINED;
            }
            return v;
        }
        if (container instanceof RTList list) {
            Integer index = ValueOps.asIntIndex(key);
            if (index == null) {
                log.warn("Line " + line + ": list index must be an integer.");
                return RTUndefined.UNDEFINED;
            }
            if (index < 0) {
                index += list.size();
            }
            return list.get(index);
        }
        if (container instanceof RTString str) {
            Integer index = ValueOps.asIntIndex(key);
            if (index == null) {
                return RTUndefined.UNDEFINED;
            }
            if (index < 0) {
                index += str.value.length();
            }
            if (index < 0 || index >= str.value.length()) {
                return RTUndefined.UNDEFINED;
            }
            return new RTString(String.valueOf(str.value.charAt(index)));
        }
        warnOnce("index-noncontainer", "Line " + line + ": indexing a non-container value returned undefined.");
        return RTUndefined.UNDEFINED;
    }

    private void setItem(RTValue container, RTValue key, RTValue value, int line) {
        if (container instanceof RTDict dict) {
            dict.put(key.toDisplayString(), value);
            return;
        }
        if (container instanceof RTList list) {
            Integer index = ValueOps.asIntIndex(key);
            if (index == null || index < 0) {
                log.warn("Line " + line + ": invalid list assignment index; skipped.");
                return;
            }
            list.set(index, value);
            return;
        }
        log.warn("Line " + line + ": item assignment on a non-container value; skipped.");
    }

    @Override
    public RTValue visit(DotAccessNode node) {
        return getAttr(eval(node.getObject()), node.getProperty(), node.getLineNumber());
    }

    private RTValue getAttr(RTValue obj, String property, int line) {
        if (obj instanceof RTDict dict) {
            RTValue v = dict.get(property);
            if (v == null) {
                warnOnce("attr:" + property, "Line " + line + ": missing dict key '" + property + "'.");
                return RTUndefined.UNDEFINED;
            }
            return v;
        }
        if (obj instanceof RTUndefined || obj instanceof RTNone) {
            return RTUndefined.UNDEFINED;
        }
        warnOnce("attr-other:" + property,
                "Line " + line + ": attribute '" + property + "' not available on " + obj.typeName() + ".");
        return RTUndefined.UNDEFINED;
    }

    // ==================== calls ====================
    @Override
    public RTValue visit(RenderTemplateNode node) {
        return captureRenderRequest(node.getTemplateName(), node.getContextVariables(), node.getLineNumber());
    }

    @Override
    public RTValue visit(KeywordArgumentNode node) {
        return eval(node.getValue());
    }

    @Override
    public RTValue visit(FunctionCallNode node) {
        ExpressionNode callee = node.getCallee();
        List<ExpressionNode> args = node.getParameters();

        if (callee instanceof VariableNode var) {
            return callNamed(var.getName(), args, node.getLineNumber());
        }
        if (callee instanceof DotAccessNode dot) {
            String method = dot.getProperty();
            RTValue obj = eval(dot.getObject());

            // flask-style attribute calls on unresolved objects
            if (obj instanceof RTUndefined || obj instanceof RTNone) {
                if ("run".equals(method)) {
                    return RTNone.NONE; // app.run(...) inside __main__ guard
                }
                warnOnce("call:" + method, "Line " + node.getLineNumber()
                        + ": call to '" + method + "()' on an unresolved object was skipped.");
                return RTNone.NONE;
            }
            return callMethod(obj, method, args, node.getLineNumber());
        }
        warnOnce("call-expr", "Line " + node.getLineNumber() + ": unsupported call expression produced None.");
        return RTNone.NONE;
    }

    private RTValue callNamed(String name, List<ExpressionNode> argExprs, int line) {
        // user-defined function?
        RTFunction fn = functions.get(name);
        if (fn == null) {
            for (Map<String, RTValue> s : scopes.innermostFirst()) {
                if (s.get(name) instanceof RTFunction found) {
                    fn = found;
                    break;
                }
            }
        }
        if (fn != null) {
            return invoker.invokeWithArgs(fn, argExprs);
        }

        List<RTValue> args = new ArrayList<>();
        for (ExpressionNode e : argExprs) {
            args.add(eval(e));
        }

        // plugin-owned names first (flask's render_template needs raw kwargs)
        for (NamedCallHandler handler : namedHandlers) {
            RTValue handled = handler.tryHandle(name, argExprs, args, line);
            if (handled != null) {
                return handled;
            }
        }

        Builtin builtin = builtins.get(name);
        if (builtin != null) {
            return builtin.invoke(args, line, ctx);
        }

        warnOnce("unknown-call:" + name,
                "Unknown function '" + name + "()' at line " + line + "; returned None instead of failing.");
        return RTNone.NONE;
    }

    private RTValue callMethod(RTValue obj, String method, List<ExpressionNode> argExprs, int line) {
        List<RTValue> args = new ArrayList<>();
        for (ExpressionNode e : argExprs) {
            args.add(eval(e));
        }
        // parity: extend-over-undefined used to warn via iterate()
        if ("extend".equals(method) && !args.isEmpty() && args.get(0) instanceof RTUndefined) {
            warnOnce("iter-undef", "Attempt to iterate an undefined value produced an empty sequence.");
        }
        MethodResult result = obj.callMethod(method, args);
        if (!result.supported()) {
            warnOnce("method:" + method,
                    "Line " + line + ": method '." + method + "()' is not supported; returned None.");
            return RTNone.NONE;
        }
        return result.value();
    }

    private RTValue captureRenderRequest(String templateName, List<KeywordArgumentNode> kwNodes, int line) {
        LinkedHashMap<String, RTValue> renderCtx = new LinkedHashMap<>();
        if (kwNodes != null) {
            for (KeywordArgumentNode kw : kwNodes) {
                renderCtx.put(kw.getName(), eval(kw.getValue()));
            }
        }
        return flask.captures().captureRequest(templateName, renderCtx, line);
    }

    // ==================== helpers ====================
    private List<RTValue> iterate(RTValue value) {
        return Builtins.iterate(value, ctx);
    }

    private String display(RTValue v) {
        return v.toDisplayString();
    }
}
