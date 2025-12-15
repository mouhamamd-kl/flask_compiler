package antlr.ast.jinja2;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

// ==================== Template Node ====================
public class TemplateNode extends ASTNode {
    public TemplateNode(int lineNumber) { super("Template", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}
