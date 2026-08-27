package antlr.generator.python;

import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.access.KeywordArgumentNode;
import antlr.ast.python.parameters.ParameterNode;
import antlr.generator.runtime.EvaluatorContext;
import antlr.generator.runtime.ReturnSignal;
import antlr.generator.runtime.ScopeStack;
import antlr.runtime.values.RTFunction;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Binds call arguments to user-function parameters (including defaults) and
 * executes the body through the supplied evaluator callback.
 */
public final class FunctionInvoker {

    /** Evaluates an argument expression in the caller's runtime. */
    @FunctionalInterface
    public interface ArgEval {
        RTValue eval(ExpressionNode expression);
    }

    /** Executes a body statement in the caller's runtime. */
    @FunctionalInterface
    public interface StmtExec {
        void exec(StatementNode statement);
    }

    private final ScopeStack scopes;
    private final EvaluatorContext ctx;
    private final ArgEval eval;
    private final StmtExec exec;

    public FunctionInvoker(ScopeStack scopes, EvaluatorContext ctx, ArgEval eval, StmtExec exec) {
        this.scopes = scopes;
        this.ctx = ctx;
        this.eval = eval;
        this.exec = exec;
    }

    /**
     * Executes a user function with pre-evaluated positional args and named kwargs.
     * The return value is whatever the body returned (None when no return ran).
     */
    public RTValue invoke(RTFunction fn, Map<String, RTValue> kwargs, List<RTValue> positional) {
        scopes.push();
        try {
            List<ParameterNode> params = fn.definition.getParameters();
            for (int i = 0; i < params.size(); i++) {
                ParameterNode param = params.get(i);
                String pname = param.getName();
                if (i < positional.size()) {
                    scopes.peek().put(pname, positional.get(i));
                } else if (kwargs.containsKey(pname)) {
                    scopes.peek().put(pname, kwargs.remove(pname));
                } else if (param.getDefaultValue() != null) {
                    scopes.peek().put(pname, eval.eval(param.getDefaultValue()));
                } else {
                    scopes.peek().put(pname, RTNone.NONE);
                    ctx.log().info("Parameter '" + pname + "' of '" + fn.name + "' bound to None for generation.");
                }
            }
            for (Map.Entry<String, RTValue> extra : kwargs.entrySet()) {
                scopes.peek().put(extra.getKey(), extra.getValue());
            }
            try {
                for (StatementNode stmt : fn.definition.getBodyStatements()) {
                    exec.exec(stmt);
                }
            } catch (ReturnSignal ret) {
                return ret.value;
            }
            return RTNone.NONE;
        } finally {
            scopes.pop();
        }
    }

    /**
     * Evaluates raw call-site expressions, splitting them into positional and
     * keyword arguments before invoking.
     */
    public RTValue invokeWithArgs(RTFunction fn, List<ExpressionNode> argExprs) {
        List<RTValue> positional = new ArrayList<>();
        LinkedHashMap<String, RTValue> kwargs = new LinkedHashMap<>();
        for (ExpressionNode e : argExprs) {
            if (e instanceof KeywordArgumentNode kw) {
                kwargs.put(kw.getName(), eval.eval(kw.getValue()));
            } else {
                positional.add(eval.eval(e));
            }
        }
        return invoke(fn, kwargs, positional);
    }
}
