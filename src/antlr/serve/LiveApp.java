package antlr.serve;

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
import antlr.ast.python.statements.ContinueNode;
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
import antlr.gen.jinja2.jinja2Lexer;
import antlr.gen.jinja2.jinja2Parser;
import antlr.gen.python.pythonLexer;
import antlr.gen.python.pythonParser;
import antlr.generator.GenerationLogWriter;
import antlr.generator.flask.RouteResolver;
import antlr.generator.jinja.JinjaRenderer;
import antlr.generator.python.FunctionInvoker;
import antlr.generator.python.PythonOperators;
import antlr.generator.python.builtins.Builtin;
import antlr.generator.python.builtins.Builtins;
import antlr.generator.runtime.BreakSignal;
import antlr.generator.runtime.ContinueSignal;
import antlr.generator.runtime.EvalSignal;
import antlr.generator.runtime.EvaluatorContext;
import antlr.generator.runtime.ReturnSignal;
import antlr.generator.runtime.ScopeStack;
import antlr.generator.runtime.ValueOps;
import antlr.ast.jinja2.TemplateNode;
import antlr.runtime.values.MethodResult;
import antlr.runtime.values.RTBool;
import antlr.runtime.values.RTDict;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTFunction;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;
import antlr.visitor.ASTBuilder;
import antlr.visitor.JinjaASTBuilder;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The live Flask runtime: parses app.py once into an AST, re-evaluates
 * per-request so in-memory state (e.g. `products`) persists across requests,
 * dispatches GET/POST/dynamic routes and renders templates on demand.
 *
 * Reuses the generation collaborators — ScopeStack, PythonOperators, ValueOps,
 * FunctionInvoker, Builtins, RouteResolver and JinjaRenderer — without touching
 * PythonEvaluator's private members.
 */
public class LiveApp extends ASTVisitorAdapter<RTValue> {

    // ==================== flask live singletons ====================
    private static final AppValue APP = new AppValue();
    private static final class AppValue extends RTValue {
        @Override public String typeName() { return "Flask"; }
    }

    /** The value a handler returns / sets to signal a rendered HTML response. */
    private static final class LiveResponse extends RTValue {
        final Response response;
        LiveResponse(Response response) { this.response = response; }
        @Override public String typeName() { return "Response"; }
    }

    // ==================== state ====================
    private final GenerationLogWriter log;
    private final EvaluatorContext ctx;
    private final ScopeStack scopes = new ScopeStack();
    private final Map<String, RTFunction> functions = new LinkedHashMap<>();
    private final Map<String, Builtin> builtins = new LinkedHashMap<>(Builtins.create());
    private final FunctionInvoker invoker;
    private final Path projectRoot;
    private final Path templatesDir;
    private final Map<String, TemplateNode> templateRegistry = new LinkedHashMap<>();
    private final JinjaRenderer renderer;
    private final List<Route> routes = new ArrayList<>();

    private Request currentRequest;
    private LiveResponse pendingResponse;
    private ProgramNode program;

    // ==================== route registry ====================
    private static final class Route {
        final String handlerName;
        final String routePath;
        final Set<String> methods;
        final boolean dynamic;
        final String prefix;
        final String paramName;

        Route(String handlerName, String routePath, Set<String> methods) {
            this.handlerName = handlerName;
            this.routePath = routePath;
            this.methods = methods;
            RouteResolver.DynamicRoute dyn = RouteResolver.parseDynamicRoute(routePath);
            if (dyn != null) {
                this.dynamic = true;
                this.prefix = dyn.prefix();
                this.paramName = dyn.paramName();
            } else {
                this.dynamic = false;
                this.prefix = null;
                this.paramName = null;
            }
        }

        /** Matches the raw request path; for dynamic routes captures the param value. */
        String match(String path) {
            if (!dynamic) {
                return routePath.equals(path) ? "" : null;
            }
            if (!path.startsWith(prefix)) {
                return null;
            }
            String rest = path.substring(prefix.length());
            return rest.isEmpty() ? null : rest;
        }
    }

    public LiveApp(GenerationLogWriter log, Path projectRoot) {
        this.log = log;
        this.ctx = new EvaluatorContext(log);
        this.invoker = new FunctionInvoker(scopes, ctx, this::eval, this::execStatement);
        this.projectRoot = projectRoot;
        this.templatesDir = projectRoot.resolve("templates");
        this.renderer = new JinjaRenderer(log, templateRegistry);
    }

    // ==================== lifecycle ====================
    public void boot(Path appPy) throws IOException {
        String source = Files.readString(appPy, StandardCharsets.UTF_8);
        pythonLexer lexer = new pythonLexer(CharStreams.fromString(source));
        lexer.removeErrorListeners();
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pythonParser parser = new pythonParser(tokens);
        parser.removeErrorListeners();
        ParseTree tree = parser.root();
        ASTBuilder builder = new ASTBuilder();
        ASTNode ast = builder.visit(tree);
        this.program = (ProgramNode) ast;

        scopes.push();
        scopes.peek().put("__name__", new RTString("__main__"));
        for (StatementNode st : program.getStatements()) {
            execStatement(st);
        }
        log.info("Live app booted: " + routes.size() + " routes registered.");
    }

    // ==================== request handling ====================
    /**
     * Dispatch a single request through the live app. State (globals) persists
     * across calls, so POST /add appends to `products` and / sees it next.
     */
    public Response handle(String method, String path,
                           Map<String, RTValue> form, Map<String, RTValue> args) {
        this.currentRequest = new Request(method, path, form, args);
        this.pendingResponse = null;
        try {
            for (Route route : routes) {
                String captured = route.match(path);
                if (captured == null) {
                    continue;
                }
                if (!route.methods.contains(method)) {
                    Response r = new Response(405, "Method Not Allowed", "text/plain");
                    r.header("Allow", String.join(", ", route.methods));
                    return r;
                }
                RTValue result = invokeHandler(route, captured);
                if (result instanceof LiveResponse live) {
                    return live.response;
                }
                if (pendingResponse != null) {
                    return pendingResponse.response;
                }
                return new Response(200, result == null || result instanceof RTNone
                        ? "" : result.toDisplayString(), "text/html");
            }
            return new Response(404, "Not Found", "text/plain");
        } finally {
            this.currentRequest = null;
            this.pendingResponse = null;
        }
    }

    private RTValue invokeHandler(Route route, String captured) {
        RTFunction fn = functions.get(route.handlerName);
        if (fn == null) {
            return RTNone.NONE;
        }
        LinkedHashMap<String, RTValue> kwargs = new LinkedHashMap<>();
        List<RTValue> positional = new ArrayList<>();
        if (route.dynamic && captured != null) {
            // <int:...> binder parses to an int
            RTValue bound = parseParamValue(captured);
            positional.add(bound);
            kwargs.put(route.paramName, bound);
        }
        try {
            return invoker.invoke(fn, kwargs, positional);
        } catch (EvalSignal signal) {
            log.warn("Handler '" + route.handlerName + "' ended with a stray break/continue/return.");
            return RTNone.NONE;
        }
    }

    private static RTValue parseParamValue(String captured) {
        try {
            return new RTInt(Integer.parseInt(captured));
        } catch (NumberFormatException e) {
            return new RTString(captured);
        }
    }

    // ==================== flask builtins ====================
    private RTValue flaskCall(String name, List<ExpressionNode> argExprs, int line) {
        switch (name) {
            case "render_template":
                return renderTemplate(argExprs, line);
            case "redirect":
                return new LiveResponse(redirect(argExprs, line));
            case "url_for": {
                String url = urlFor(argExprs, line);
                return new RTString(url);
            }
            case "jsonify":
                return new LiveResponse(jsonify(argExprs, line));
            case "Flask":
                return APP;
            default:
                return null;
        }
    }

    private LiveResponse renderTemplate(List<ExpressionNode> argExprs, int line) {
        if (argExprs.isEmpty()) {
            return new LiveResponse(new Response(500, "render_template() requires a template name.", "text/plain"));
        }
        String templateName = eval(argExprs.get(0)).toDisplayString();
        TemplateNode template = loadTemplate(templateName, line);
        if (template == null) {
            return new LiveResponse(new Response(500,
                    "Template '" + templateName + "' not found.", "text/plain"));
        }
        LinkedHashMap<String, RTValue> renderCtx = new LinkedHashMap<>();
        for (int i = 1; i < argExprs.size(); i++) {
            ExpressionNode e = argExprs.get(i);
            String name = e instanceof KeywordArgumentNode kw ? kw.getName() : "arg" + i;
            renderCtx.put(name, eval(e instanceof KeywordArgumentNode kw2 ? kw2.getValue() : e));
        }
        String html = renderer.render(template, templateName, renderCtx);
        return new LiveResponse(Response.ok(html, "text/html; charset=utf-8"));
    }

    private Response redirect(List<ExpressionNode> argExprs, int line) {
        String target = "";
        if (!argExprs.isEmpty()) {
            target = eval(argExprs.get(0)).toDisplayString();
        }
        return Response.redirect(target);
    }

    private String urlFor(List<ExpressionNode> argExprs, int line) {
        if (argExprs.isEmpty()) {
            return "/";
        }
        String endpoint = eval(argExprs.get(0)).toDisplayString();
        Map<String, RTValue> kwargs = new LinkedHashMap<>();
        for (int i = 1; i < argExprs.size(); i++) {
            ExpressionNode e = argExprs.get(i);
            if (e instanceof KeywordArgumentNode kw) {
                kwargs.put(kw.getName(), eval(kw.getValue()));
            }
        }
        for (Route route : routes) {
            if (route.handlerName.equals(endpoint)) {
                if (!route.dynamic) {
                    return route.routePath;
                }
                String value = kwargs.containsKey(route.paramName)
                        ? kwargs.get(route.paramName).toDisplayString()
                        : "0";
                return route.prefix + value;
            }
        }
        return "/";
    }

    private Response jsonify(List<ExpressionNode> argExprs, int line) {
        if (argExprs.size() == 1) {
            return Response.ok(toJson(eval(argExprs.get(0))), "application/json");
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (ExpressionNode e : argExprs) {
            if (!(e instanceof KeywordArgumentNode kw)) {
                continue;
            }
            if (!first) sb.append(",");
            first = false;
            sb.append(jsonEscape(kw.getName())).append(":").append(toJson(eval(kw.getValue())));
        }
        sb.append("}");
        return Response.ok(sb.toString(), "application/json");
    }

    private static String jsonEscape(String s) {
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    /** Minimal JSON serializer over the RT* value family (dicts use string keys). */
    private static String toJson(RTValue v) {
        if (v instanceof RTDict dict) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (String key : dict.keys()) {
                if (!first) sb.append(",");
                first = false;
                sb.append(jsonEscape(key)).append(":").append(toJson(dict.get(key)));
            }
            return sb.append("}").toString();
        }
        if (v instanceof RTList list) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(toJson(list.get(i)));
            }
            return sb.append("]").toString();
        }
        if (v instanceof RTString s) {
            return jsonEscape(s.value);
        }
        if (v instanceof RTBool b) {
            return b.value ? "true" : "false";
        }
        if (v instanceof RTInt i) {
            return String.valueOf(i.value);
        }
        if (v instanceof RTFloat f) {
            double d = f.value;
            return d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d);
        }
        if (v instanceof RTNone || v == null || v instanceof RTUndefined) {
            return "null";
        }
        return jsonEscape(v.toDisplayString());
    }

    // ==================== templates ====================
    private TemplateNode loadTemplate(String templateName, int line) {
        String baseName = templateName.contains("/")
                ? templateName.substring(templateName.lastIndexOf('/') + 1) : templateName;
        if (templateRegistry.containsKey(baseName)) {
            return templateRegistry.get(baseName);
        }
        Path file = templatesDir.resolve(baseName);
        try {
            String source = Files.readString(file, StandardCharsets.UTF_8);
            jinja2Lexer lexer = new jinja2Lexer(CharStreams.fromString(source));
            lexer.removeErrorListeners();
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            jinja2Parser parser = new jinja2Parser(tokens);
            parser.removeErrorListeners();
            ParseTree tree = parser.template();
            JinjaASTBuilder builder = new JinjaASTBuilder();
            TemplateNode node = (TemplateNode) builder.visit(tree);
            templateRegistry.put(baseName, node);
            log.info("Loaded template '" + baseName + "'.");
            return node;
        } catch (Exception e) {
            log.error("Could not load template '" + baseName + "': " + e.getMessage());
            templateRegistry.put(baseName, null);
            return null;
        }
    }

    public void reloadTemplates() {
        List<String> names = new ArrayList<>(templateRegistry.keySet());
        templateRegistry.clear();
        for (String name : names) {
            templateRegistry.remove(name);
            loadTemplate(name, 0);
        }
    }

    // ==================== statement execution ====================
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

    private RTValue eval(ExpressionNode expr) {
        if (expr == null) {
            return RTNone.NONE;
        }
        RTValue value = expr.accept(this);
        return value == null ? RTNone.NONE : value;
    }

    private RTValue resolve(String name) {
        if ("request".equals(name)) {
            return currentRequest != null ? currentRequest : RTNone.NONE;
        }
        if ("app".equals(name)) {
            return APP;
        }
        RTValue v = scopes.resolveOrNull(name);
        if (v != null) {
            return v;
        }
        return RTUndefined.UNDEFINED;
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
            setItem(eval(idx.getContainer()), eval(idx.getKey()), value);
        } else if (target instanceof DotAccessNode dot) {
            RTValue container = eval(dot.getObject());
            if (container instanceof RTDict dict) {
                dict.put(dot.getProperty(), value);
            }
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
        if (isNameMainGuard(node.getCondition())) {
            // Under `flask --app app run` semantics the guard body is not executed.
            log.info("Skipped `if __name__ == '__main__'` guard (matches `flask run` behavior).");
            return null;
        }
        if (eval(node.getCondition()).isTruthy()) {
            execBlock(node.getIfBlock());
            return null;
        }
        List<ExpressionNode> elifConds = node.getElifConditions();
        List<BlockNode> elifBlocks = node.getElifBlocks();
        for (int i = 0; i < elifConds.size() && i < elifBlocks.size(); i++) {
            if (eval(elifConds.get(i)).isTruthy()) {
                execBlock(elifBlocks.get(i));
                return null;
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
        for (RTValue item : iterate(eval(node.getIterable()))) {
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
        if (routePath != null) {
            routes.add(new Route(fn.name, routePath, extractRouteMethods(node)));
            log.info("Registered route " + routePath + " -> " + fn.name);
        }
        return null;
    }

    @Override
    public RTValue visit(ImportStatement node) {
        if ("flask".equals(node.getModuleName())) {
            for (ImportedClassNode imported : node.getImportedClasses()) {
                String name = imported.getClassName();
                scopes.peek().put(name, flaskValue(name));
            }
        } else {
            for (ImportedClassNode imported : node.getImportedClasses()) {
                scopes.peek().put(imported.getClassName(), RTUndefined.UNDEFINED);
            }
        }
        return null;
    }

    private RTValue flaskValue(String name) {
        return new FlaskName(name);
    }

    /** Marker for an imported flask name; resolved specially in function calls. */
    private static final class FlaskName extends RTValue {
        final String name;
        FlaskName(String name) { this.name = name; }
        @Override public String typeName() { return "flask"; }
    }

    // ==================== literals ====================
    @Override
    public RTValue visit(StringNode node) {
        return new RTString(unquote(node.getValue()));
    }

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
        String name = node.getName();
        if ("request".equals(name)) {
            return currentRequest != null ? currentRequest : RTUndefined.UNDEFINED;
        }
        if ("app".equals(name)) {
            return APP;
        }
        RTValue v = scopes.resolveOrNull(name);
        if (v != null) {
            return v;
        }
        return resolve(name);
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
            dict.put(eval(entry.getKey()).toDisplayString(), eval(entry.getValue()));
        }
        return dict;
    }

    @Override
    public RTValue visit(ListComprehensionNode node) {
        List<RTValue> result = new ArrayList<>();
        scopes.push();
        try {
            for (RTValue item : iterate(eval(node.getIterable()))) {
                scopes.peek().put(node.getTargetVariable(), item);
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
        return getItem(eval(node.getContainer()), eval(node.getKey()));
    }

    private RTValue getItem(RTValue container, RTValue key) {
        if (container instanceof RTDict dict) {
            RTValue v = dict.get(key.toDisplayString());
            return v == null ? RTUndefined.UNDEFINED : v;
        }
        if (container instanceof RTList list) {
            Integer index = ValueOps.asIntIndex(key);
            if (index == null) {
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
        return RTUndefined.UNDEFINED;
    }

    private void setItem(RTValue container, RTValue key, RTValue value) {
        if (container instanceof RTDict dict) {
            dict.put(key.toDisplayString(), value);
        } else if (container instanceof RTList list) {
            Integer index = ValueOps.asIntIndex(key);
            if (index != null && index >= 0) {
                list.set(index, value);
            }
        }
    }

    @Override
    public RTValue visit(DotAccessNode node) {
        RTValue obj = eval(node.getObject());
        if (obj instanceof Request req) {
            return requestAttr(req, node.getProperty());
        }
        if (obj instanceof RTDict dict) {
            RTValue v = dict.get(node.getProperty());
            return v == null ? RTUndefined.UNDEFINED : v;
        }
        if (obj instanceof RTUndefined || obj instanceof RTNone) {
            return RTUndefined.UNDEFINED;
        }
        return RTUndefined.UNDEFINED;
    }

    private RTValue requestAttr(Request req, String property) {
        switch (property) {
            case "method":
                return new RTString(req.method());
            case "path":
                return new RTString(req.path());
            case "form":
                return toDict(req.form());
            case "args":
                return toDict(req.args());
            default:
                return RTUndefined.UNDEFINED;
        }
    }

    private static RTDict toDict(Map<String, RTValue> src) {
        RTDict dict = new RTDict();
        if (src != null) {
            for (Map.Entry<String, RTValue> e : src.entrySet()) {
                dict.put(e.getKey(), e.getValue());
            }
        }
        return dict;
    }

    // ==================== calls ====================
    @Override
    public RTValue visit(RenderTemplateNode node) {
        return renderTemplate(node.getTemplateName(), node.getContextVariables(), node.getLineNumber());
    }

    private LiveResponse renderTemplate(String templateName, List<KeywordArgumentNode> kwNodes, int line) {
        TemplateNode template = loadTemplate(templateName, line);
        if (template == null) {
            return new LiveResponse(new Response(500,
                    "Template '" + templateName + "' not found.", "text/plain"));
        }
        LinkedHashMap<String, RTValue> renderCtx = new LinkedHashMap<>();
        for (KeywordArgumentNode kw : kwNodes) {
            renderCtx.put(kw.getName(), eval(kw.getValue()));
        }
        String html = renderer.render(template, templateName, renderCtx);
        return new LiveResponse(Response.ok(html, "text/html; charset=utf-8"));
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
            RTValue obj = eval(dot.getObject());
            if (obj instanceof AppValue) {
                // app.route(...) / app.run(...) handled at parse time or no-op
                return RTNone.NONE;
            }
            return callMethod(obj, dot.getProperty(), args);
        }
        return RTNone.NONE;
    }

    private RTValue callNamed(String name, List<ExpressionNode> argExprs, int line) {
        // flask builtins first (render_template / redirect / url_for / jsonify / Flask)
        RTValue flask = flaskCall(name, argExprs, line);
        if (flask != null) {
            return flask;
        }
        // pre-resolved flask name marker (from import)
        RTValue scopeVal = scopes.resolveOrNull(name);
        if (scopeVal instanceof FlaskName) {
            return flaskCall(((FlaskName) scopeVal).name, argExprs, line);
        }
        // user-defined function
        RTFunction fn = functions.get(name);
        if (fn != null) {
            return invoker.invokeWithArgs(fn, argExprs);
        }
        // builtins (len, str, int, float, max, min, ...)
        List<RTValue> args = new ArrayList<>();
        for (ExpressionNode e : argExprs) {
            args.add(eval(e));
        }
        Builtin builtin = builtins.get(name);
        if (builtin != null) {
            return builtin.invoke(args, line, ctx);
        }
        log.warn("Unknown function '" + name + "()' at line " + line + "; returned None.");
        return RTNone.NONE;
    }

    private RTValue callMethod(RTValue obj, String method, List<ExpressionNode> argExprs) {
        List<RTValue> args = new ArrayList<>();
        for (ExpressionNode e : argExprs) {
            args.add(eval(e));
        }
        MethodResult result = obj.callMethod(method, args);
        return result.supported() ? result.value() : RTNone.NONE;
    }

    private List<RTValue> iterate(RTValue value) {
        return Builtins.iterate(value, ctx);
    }

    // ==================== route methods parsing ====================
    private static Set<String> extractRouteMethods(FunctionDefinitionNode node) {
        for (antlr.ast.python.statements.DecoratorNode decorator : node.getDecorators()) {
            ExpressionNode expr = decorator.getExpression();
            if (expr instanceof FunctionCallNode call
                    && call.getCallee() instanceof DotAccessNode dot
                    && "route".equals(dot.getProperty())) {
                for (ExpressionNode p : call.getParameters()) {
                    if (p instanceof KeywordArgumentNode kw && "methods".equals(kw.getName())
                            && kw.getValue() instanceof ListNode listNode) {
                        Set<String> methods = new LinkedHashSet<>();
                        for (ExpressionNode el : listNode.getElements()) {
                            if (el instanceof StringNode s) {
                                methods.add(unquote(s.getValue()));
                            }
                        }
                        if (!methods.isEmpty()) {
                            return methods;
                        }
                    }
                }
            }
        }
        Set<String> def = new LinkedHashSet<>();
        def.add("GET");
        return def;
    }
}
