package antlr.runtime.values;

import antlr.ast.python.statements.FunctionDefinitionNode;

/**
 * A user-defined Python function captured from the AST.
 * The body is NOT executed at definition time; only on invocation.
 */
public class RTFunction extends RTValue {

    public final String name;
    public final FunctionDefinitionNode definition;
    /** Route path extracted from a flask decorator such as @app.route('/'), or null. */
    public final String routePath;

    public RTFunction(FunctionDefinitionNode definition) {
        this(definition, null);
    }

    public RTFunction(FunctionDefinitionNode definition, String routePath) {
        this.definition = definition;
        this.name = definition.getFunctionName();
        this.routePath = routePath;
    }

    @Override
    public String typeName() {
        return "function";
    }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    public String toString() {
        return "<function " + name + ">";
    }

    @Override
    public String describe() {
        return routePath != null ? "function(" + name + ", route=" + routePath + ")" : "function(" + name + ")";
    }
}
