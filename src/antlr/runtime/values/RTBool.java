package antlr.runtime.values;

/**
 * Python bool (True / False).
 */
public class RTBool extends RTValue {

    public final boolean value;

    public RTBool(boolean value) {
        this.value = value;
    }

    public static RTBool of(boolean value) {
        return value ? new RTBool(true) : new RTBool(false);
    }

    @Override
    public String typeName() {
        return "bool";
    }

    @Override
    public boolean isTruthy() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RTBool && ((RTBool) obj).value == value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    @Override
    public String toString() {
        return value ? "True" : "False";
    }

    @Override
    public String describe() {
        return "bool(" + value + ")";
    }
}
