package antlr.generator.runtime;

/** Singleton-style break signal; {@link #BREAK} is reused to avoid allocation. */
public final class BreakSignal extends EvalSignal {

    public static final BreakSignal BREAK = new BreakSignal();

    private BreakSignal() {
    }
}
