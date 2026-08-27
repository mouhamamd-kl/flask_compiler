package antlr.runtime.values;

/**
 * Base class for all runtime values produced during the generation phase.
 * Every value evaluated from the Python AST is wrapped in an RTValue subclass.
 */
public abstract class RTValue {

    /**
     * Python-style type name (int, float, str, bool, list, dict, NoneType, ...)
     */
    public abstract String typeName();

    /**
     * Python truthiness rules.
     */
    public boolean isTruthy() {
        return true;
    }

    /**
     * String form used when the value is substituted into a template ({{ value }}).
     */
    public String toDisplayString() {
        return toString();
    }

    /**
     * Human-readable short description used in logs.
     */
    public String describe() {
        return "<" + typeName() + ">";
    }

    /**
     * Polymorphic method dispatch (obj.method(args)). Values that do not
     * implement the method return an unsupported result; the caller decides
     * how to warn. Arguments arrive pre-evaluated.
     */
    public MethodResult callMethod(String name, java.util.List<RTValue> args) {
        return MethodResult.unsupported();
    }
}
