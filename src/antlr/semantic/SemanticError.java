package antlr.semantic;

/**
 * A single semantic finding produced by one of the analysis passes
 * (Python, Jinja template, or Flask project).
 */
public final class SemanticError {

    public enum Code {
        UNDEFINED_VARIABLE,
        TYPE_ERROR,
        TYPE_MISMATCH,
        SCOPE_ERROR,
        MISSING_TEMPLATE_VARIABLE
    }

    private final Code code;
    private final String message;
    private final int line;
    private final int column;
    private final String sourceName;

    public SemanticError(Code code, String message, int line, int column) {
        this(code, message, line, column, null);
    }

    public SemanticError(Code code, String message, int line, int column, String sourceName) {
        this.code = code;
        this.message = message;
        this.line = line;
        this.column = column;
        this.sourceName = sourceName;
    }

    public Code getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getSourceName() {
        return sourceName;
    }

    /**
     * Renders the error for the semantic report and the compilation log.
     * Python source errors use "Line X:Y", template errors are prefixed with
     * the template file name.
     */
    public String format() {
        String location;
        if (sourceName != null) {
            location = sourceName + ":" + line + ":" + column;
        } else {
            location = "Line " + line + ":" + column;
        }
        return location + " [" + code + "] - Semantic error: " + message;
    }

    @Override
    public String toString() {
        return format();
    }
}