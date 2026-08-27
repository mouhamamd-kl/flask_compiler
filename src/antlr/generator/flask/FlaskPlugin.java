package antlr.generator.flask;

import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.access.KeywordArgumentNode;
import antlr.generator.NamedCallHandler;
import antlr.generator.runtime.EvaluatorContext;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTValue;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Flask-specific call handling for the evaluator: the render_template
 * builtin (which needs raw argument expressions for its keyword names) and
 * the known external functions that degrade to None with a warning.
 */
public final class FlaskPlugin implements NamedCallHandler {

    /** Evaluates an expression in the host evaluator's runtime. */
    @FunctionalInterface
    public interface Eval {
        RTValue eval(ExpressionNode expression);
    }

    private final RenderCaptureService captures;
    private final EvaluatorContext ctx;
    private final Eval eval;

    public FlaskPlugin(RenderCaptureService captures, EvaluatorContext ctx, Eval eval) {
        this.captures = captures;
        this.ctx = ctx;
        this.eval = eval;
    }

    /**
     * Handles a flask-owned named call. Returns null when the name is not
     * flask-owned so the caller can fall through to other registries.
     *
     * @param args pre-evaluated arguments (positional + keyword values in order)
     */
    public RTValue tryHandle(String name, List<ExpressionNode> argExprs, List<RTValue> args, int line) {
        switch (name) {
            case "render_template": {
                if (!args.isEmpty() && args.get(0) instanceof RTString templateName) {
                    LinkedHashMap<String, RTValue> renderCtx = new LinkedHashMap<>();
                    collectKeywordArgs(argExprs, renderCtx);
                    return captures.captureRequest(templateName.value, renderCtx, line);
                }
                ctx.log().warn("render_template called without a template name.");
                return RTNone.NONE;
            }
            case "Flask":
            case "redirect":
            case "url_for":
            case "flash":
            case "jsonify":
                ctx.warnOnce("extcall:" + name,
                        "Call to external function '" + name + "()' was skipped (returns None).");
                return RTNone.NONE;
            default:
                return null;
        }
    }

    /**
     * Fallback path for a render_template call that reached the evaluator as a
     * generic FunctionCallNode rather than a dedicated RenderTemplateNode.
     */
    public RTValue handleRenderTemplateCallFallback(int lineNumber, List<ExpressionNode> args) {
        if (!args.isEmpty() && eval.eval(args.get(0)) instanceof RTString name) {
            LinkedHashMap<String, RTValue> renderCtx = new LinkedHashMap<>();
            collectKeywordArgs(args, renderCtx);
            return captures.captureRequest(name.value, renderCtx, lineNumber);
        }
        ctx.log().warn("render_template(...) without a literal template name; skipped.");
        return RTNone.NONE;
    }

    private void collectKeywordArgs(List<ExpressionNode> argExprs, LinkedHashMap<String, RTValue> out) {
        for (ExpressionNode e : argExprs) {
            if (e instanceof KeywordArgumentNode kw) {
                out.put(kw.getName(), eval.eval(kw.getValue()));
            }
        }
    }

    public RenderCaptureService captures() {
        return captures;
    }
}
