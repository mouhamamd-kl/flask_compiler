package antlr.runtime.values;

public class RTFloat extends RTValue {

    public final double value;

    public RTFloat(double value) {
        this.value = value;
    }

    @Override
    public String typeName() {
        return "float";
    }

    @Override
    public boolean isTruthy() {
        return value != 0.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RTFloat) {
            return ((RTFloat) obj).value == value;
        }
        if (obj instanceof RTInt) {
            return ((RTInt) obj).value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String describe() {
        return "float(" + value + ")";
    }
}
