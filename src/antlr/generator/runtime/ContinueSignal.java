package antlr.generator.runtime;

/** Singleton-style continue signal; {@link #CONTINUE} is reused to avoid allocation. */
public final class ContinueSignal extends EvalSignal {

    public static final ContinueSignal CONTINUE = new ContinueSignal();

    private ContinueSignal() {
    }
}
