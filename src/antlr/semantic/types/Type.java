package antlr.semantic.types;

/**
 * Static type model used by the semantic-analysis passes.
 *
 * The compiler intentionally mirrors CPython: it only flags operations that
 * Python always rejects (NameError / TypeError / AttributeError / scope
 * errors). Anything that depends on runtime values is kept as {@link Kind#ANY}
 * so the analyzer stays silent when the real interpreter would.
 */
public final class Type {

    public enum Kind {
        INT,
        FLOAT,
        STRING,
        BOOL,
        NONE,
        LIST,
        DICT,
        FUNCTION,
        CLASS,
        IMPORT,
        ANY
    }

    public static final Type INT = new Type(Kind.INT);
    public static final Type FLOAT = new Type(Kind.FLOAT);
    public static final Type STRING = new Type(Kind.STRING);
    public static final Type BOOL = new Type(Kind.BOOL);
    public static final Type NONE = new Type(Kind.NONE);
    public static final Type FUNCTION = new Type(Kind.FUNCTION);
    public static final Type CLASS = new Type(Kind.CLASS);
    public static final Type IMPORT = new Type(Kind.IMPORT);
    public static final Type ANY = new Type(Kind.ANY);

    public static Type listOf(Type element) {
        return new Type(Kind.LIST, element, null, null);
    }

    public static Type dictOf(Type key, Type value) {
        return new Type(Kind.DICT, null, key, value);
    }

    private final Kind kind;
    private final Type elementType;
    private final Type keyType;
    private final Type valueType;

    private Type(Kind kind) {
        this(kind, null, null, null);
    }

    private Type(Kind kind, Type elementType, Type keyType, Type valueType) {
        this.kind = kind;
        this.elementType = elementType;
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public Kind kind() {
        return kind;
    }

    public Type elementType() {
        return elementType;
    }

    public Type keyType() {
        return keyType;
    }

    public Type valueType() {
        return valueType;
    }

    public boolean isNumeric() {
        return kind == Kind.INT || kind == Kind.FLOAT || kind == Kind.BOOL;
    }

    public boolean isIterable() {
        return kind == Kind.LIST || kind == Kind.STRING || kind == Kind.DICT || kind == Kind.ANY;
    }

    public boolean isSubscriptable() {
        return kind == Kind.LIST || kind == Kind.STRING || kind == Kind.DICT || kind == Kind.ANY;
    }

    public boolean isCallable() {
        return kind == Kind.FUNCTION || kind == Kind.CLASS || kind == Kind.IMPORT || kind == Kind.ANY;
    }

    /** True when the analyzer knows the concrete type (everything but ANY). */
    public boolean isKnown() {
        return kind != Kind.ANY;
    }

    /**
     * Human-readable type name matching CPython error messages, e.g.
     * "'int' object is not subscriptable".
     */
    public String display() {
        return switch (kind) {
            case INT -> "int";
            case FLOAT -> "float";
            case STRING -> "str";
            case BOOL -> "bool";
            case NONE -> "NoneType";
            case LIST -> "list";
            case DICT -> "dict";
            case FUNCTION -> "function";
            case CLASS -> "class";
            case IMPORT -> "module";
            case ANY -> "unknown type";
        };
    }

    public String pythonRepr() {
        return "'" + display() + "'";
    }

    /**
     * Best-effort join of two operands for expressions whose result type
     * depends on the operands (e.g. binary arithmetic). ANY dominates, so an
     * unknown operand yields an unknown result instead of a false positive.
     */
    public Type union(Type other) {
        if (kind == Kind.ANY && other.kind == Kind.ANY) {
            return ANY;
        }
        if (kind == Kind.ANY) {
            return other;
        }
        if (other.kind == Kind.ANY) {
            return this;
        }
        if (this.isNumeric() && other.isNumeric()) {
            return (kind == Kind.FLOAT || other.kind == Kind.FLOAT) ? FLOAT : INT;
        }
        if (kind == other.kind) {
            return this;
        }
        return ANY;
    }

    /** Element type to bind when iterating an object. */
    public Type iterationElement() {
        return switch (kind) {
            case LIST -> (elementType != null) ? elementType : ANY;
            case STRING -> STRING;
            case DICT -> (keyType != null) ? keyType : ANY;
            default -> ANY;
        };
    }

    @Override
    public String toString() {
        return display();
    }
}