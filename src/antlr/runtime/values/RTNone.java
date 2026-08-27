package antlr.runtime.values;

/**
 * Python None.
 */
public class RTNone extends RTValue {

    public static final RTNone NONE = new RTNone();

    private RTNone() {
    }

    @Override
    public String typeName() {
        return "NoneType";
    }

    @Override
    public boolean isTruthy() {
        return false;
    }

    @Override
    public String toString() {
        return "None";
    }
}
