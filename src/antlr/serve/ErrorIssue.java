package antlr.serve;

/**
 * A single error issue shown in the overlay UI: a project-relative file
 * reference (e.g. "app.py", "templates/products.html"), an optional line/column
 * (0/0 when unknown), a kind badge and the human-readable message.
 */
public final class ErrorIssue {

    private final String file;
    private final int line;
    private final int column;
    private final String kind;
    private final String message;

    public ErrorIssue(String file, int line, int column, String kind, String message) {
        this.file = file;
        this.line = line;
        this.column = column;
        this.kind = kind;
        this.message = message;
    }

    public String file() {
        return file;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    public String kind() {
        return kind;
    }

    public String message() {
        return message;
    }

    /** True when this issue carries a usable line reference for a code excerpt. */
    public boolean hasLocation() {
        return file != null && !file.isEmpty() && line > 0;
    }

    @Override
    public String toString() {
        return (file != null ? file : "?") + ":" + line + ":" + column
                + " [" + kind + "] " + message;
    }
}