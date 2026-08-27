package antlr.runtime.values;

/**
 * Outcome of a polymorphic method call on a runtime value. Values carry no
 * logger, so callers translate {@code supported == false} into their own
 * warning text.
 */
public record MethodResult(RTValue value, boolean supported) {

    public static MethodResult ok(RTValue value) {
        return new MethodResult(value, true);
    }

    public static MethodResult unsupported() {
        return new MethodResult(RTNone.NONE, false);
    }
}
