package antlr.serve;

import java.util.ArrayList;
import java.util.List;

/**
 * Structured error payload rendered by the {@link ErrorOverlay} component: a
 * title, the issue list (each with file/line/column), the per-request trace
 * breadcrumbs, and the location the code excerpt pin should point at.
 */
public final class ErrorInfo {

    private final String title;
    private final List<ErrorIssue> issues;
    private final List<String> requestTrace;

    private final String snippetPath;
    private final int snippetLine;
    private final int snippetColumn;

    public ErrorInfo(String title, List<ErrorIssue> issues, List<String> requestTrace) {
        this.title = title;
        this.issues = new ArrayList<>(issues);
        this.requestTrace = new ArrayList<>(requestTrace);
        ErrorIssue first = firstLocated();
        if (first != null) {
            this.snippetPath = first.file();
            this.snippetLine = first.line();
            this.snippetColumn = first.column();
        } else {
            this.snippetPath = null;
            this.snippetLine = 0;
            this.snippetColumn = 0;
        }
    }

    public String title() {
        return title;
    }

    public List<ErrorIssue> issues() {
        return issues;
    }

    public List<String> requestTrace() {
        return requestTrace;
    }

    public String snippetPath() {
        return snippetPath;
    }

    public int snippetLine() {
        return snippetLine;
    }

    public int snippetColumn() {
        return snippetColumn;
    }

    public boolean hasSnippet() {
        return snippetPath != null && snippetLine > 0;
    }

    /** First issue carrying a file+line, used to pin the code excerpt. */
    private ErrorIssue firstLocated() {
        for (ErrorIssue i : issues) {
            if (i.hasLocation()) {
                return i;
            }
        }
        return null;
    }

    /** A stable-ish key so a dismissed overlay stays hidden until the error changes. */
    String signature() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append('|').append(issues.size());
        for (ErrorIssue i : issues) {
            sb.append('|').append(i.file()).append(':').append(i.line()).append(':').append(i.column());
            String msg = i.message();
            sb.append('|').append(msg.length()).append(msg.hashCode());
        }
        return sb.toString().replaceAll("[^A-Za-z0-9|]", "");
    }
}