package antlr.ast.html;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

// ==================== HTML Document ====================
public class HtmlDocumentNode extends ASTNode {
    public HtmlDocumentNode(int lineNumber) { super("HtmlDocument", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}
