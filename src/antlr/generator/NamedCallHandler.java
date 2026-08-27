package antlr.generator;

import antlr.ast.python.expressions.ExpressionNode;
import antlr.runtime.values.RTValue;

import java.util.List;

/**
 * Extension point for evaluator-owned named calls (e.g. framework functions
 * like render_template that need raw argument expressions for keyword names).
 *
 * Handlers are consulted after user-defined function lookup and before the
 * builtins registry; the first non-null result wins.
 */
@FunctionalInterface
public interface NamedCallHandler {

    /**
     * Handles a named call owned by this plugin.
     *
     * @param name     called function name
     * @param argExprs raw argument expressions (needed for keyword-argument names)
     * @param args     pre-evaluated positional arguments in source order
     * @param line     source line of the call site
     * @return the call's value, or null when the name is not handled here
     */
    RTValue tryHandle(String name, List<ExpressionNode> argExprs, List<RTValue> args, int line);
}
