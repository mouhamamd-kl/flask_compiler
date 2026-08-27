package antlr.runtime.values;

/**
 * Marker value for a name that could not be resolved at generation time
 * (e.g. flask's request / redirect / url_for objects).
 *
 * Semantics chosen so that typical template code degrades gracefully:
 * - truthy: false  ->  {% if request.method == 'POST' %} takes the else branch
 * - attribute/index access returns another RTUndefined
 * - equality against anything real is false
 */
public class RTUndefined extends RTValue {

    public static final RTUndefined UNDEFINED = new RTUndefined();

    private RTUndefined() {
    }

    public final String name = "<undefined>";

    @Override
    public String typeName() {
        return "undefined";
    }

    @Override
    public boolean isTruthy() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        // Equality with a real value is always False; two undefineds are also
        // treated as not equal to avoid masking genuine bugs.
        return false;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String describe() {
        return "undefined";
    }
}
