package antlr.ast.jinja2.content;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

/**
 * عقدة النص العادي (HTML/Plain Text)
 * تمثل النص الثابت بين علامات Jinja2
 *
 * Examples:
 * - <h1>Hello</h1>
 * - Plain text content
 * - Whitespace between Jinja blocks
 */
public class HtmlTextNode extends ASTNode {

    private final String text;

    public HtmlTextNode(String text, int lineNumber, int columnNumber) {
        super("HtmlText", lineNumber, columnNumber);
        this.text = text;
    }

    public HtmlTextNode(String text, int lineNumber) {
        this(text, lineNumber, 0);
    }

    // ==================== Getters ====================

    public String getText() {
        return text;
    }

    // ==================== تطبيق الواجهات ====================

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.emptyList();  // عقدة ورقة - لا أبناء
    }

    @Override
    protected String getExtraInfo() {
        // Show truncated text for readability
        String preview = text.length() > 30
            ? text.substring(0, 30) + "..."
            : text;
        // Escape newlines for display
        preview = preview.replace("\n", "\\n").replace("\r", "\\r");
        return String.format("(text: \"%s\")", preview);
    }
}
