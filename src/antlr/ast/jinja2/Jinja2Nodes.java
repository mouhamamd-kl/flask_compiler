package antlr.ast.jinja2;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

// ==================== Expression Block {{ expr }} ====================
class ExpressionBlockNode extends ASTNode {
    public ExpressionBlockNode(int lineNumber) { super("ExpressionBlock", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Statement Block {% stmt %} ====================
class StatementBlockNode extends ASTNode {
    public StatementBlockNode(int lineNumber) { super("StatementBlock", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== For Loop ====================
class ForLoopNode extends ASTNode {
    public ForLoopNode(int lineNumber) { super("ForLoop", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Jinja If ====================
class JinjaIfNode extends ASTNode {
    public JinjaIfNode(int lineNumber) { super("JinjaIf", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Include ====================
class IncludeNode extends ASTNode {
    public IncludeNode(int lineNumber) { super("Include", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Extends ====================
class ExtendsNode extends ASTNode {
    public ExtendsNode(int lineNumber) { super("Extends", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Block Definition ====================
class BlockDefinitionNode extends ASTNode {
    public BlockDefinitionNode(int lineNumber) { super("BlockDefinition", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}

// ==================== Raw Text ====================
class RawTextNode extends ASTNode {
    public RawTextNode(int lineNumber) { super("RawText", lineNumber, 0); }
    @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
}
