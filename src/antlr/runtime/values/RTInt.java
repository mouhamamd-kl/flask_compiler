package antlr.runtime.values;

public class RTInt extends RTValue {

    public final int value;

    public RTInt(int value) {
        this.value = value;
    }

    @Override
    public String typeName() {
        return "int";
    }

    @Override
    public boolean isTruthy() {
        return value != 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RTInt) {
            return ((RTInt) obj).value == value;
        }
        if (obj instanceof RTFloat) {
            return ((RTFloat) obj).value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String describe() {
        return "int(" + value + ")";
    }
}
