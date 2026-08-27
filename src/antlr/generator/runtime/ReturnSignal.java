package antlr.generator.runtime;

import antlr.runtime.values.RTValue;

/** Thrown by {@code visit(ReturnNode)} to unwind to the function boundary. */
public final class ReturnSignal extends EvalSignal {

    public final RTValue value;

    public ReturnSignal(RTValue value) {
        this.value = value;
    }
}
