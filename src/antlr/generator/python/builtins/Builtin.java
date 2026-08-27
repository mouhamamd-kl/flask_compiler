package antlr.generator.python.builtins;

import antlr.generator.runtime.EvaluatorContext;
import antlr.runtime.values.RTValue;

import java.util.List;

/** A python builtin function callable from evaluated code. */
@FunctionalInterface
public interface Builtin {

    RTValue invoke(List<RTValue> args, int line, EvaluatorContext ctx);
}
