package antlr.generator.runtime;

/**
 * Base for non-exception control-flow signals used while executing python
 * statements (return / break / continue). Never carries a stack trace.
 */
public abstract class EvalSignal extends RuntimeException {

    EvalSignal() {
        super(null, null, false, false);
    }
}
