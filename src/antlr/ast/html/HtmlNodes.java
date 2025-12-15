package antlr.ast.html;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

// TODO: Uncomment these HTML node classes when HTML support is fully implemented in ASTVisitor

// // ==================== HTML Element ====================
// class HtmlElementNode extends ASTNode {
//     public HtmlElementNode(int lineNumber) { super("HtmlElement", lineNumber, 0); }
//     @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
//     @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
// }

// // ==================== HTML Attribute ====================
// class HtmlAttributeNode extends ASTNode {
//     public HtmlAttributeNode(int lineNumber) { super("HtmlAttribute", lineNumber, 0); }
//     @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
//     @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
// }

// // ==================== HTML Text ====================
// class HtmlTextNode extends ASTNode {
//     public HtmlTextNode(int lineNumber) { super("HtmlText", lineNumber, 0); }
//     @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
//     @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
// }

// // ==================== HTML Comment ====================
// class HtmlCommentNode extends ASTNode {
//     public HtmlCommentNode(int lineNumber) { super("HtmlComment", lineNumber, 0); }
//     @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
//     @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
// }

// // ==================== Doctype ====================
// class DoctypeNode extends ASTNode {
//     public DoctypeNode(int lineNumber) { super("Doctype", lineNumber, 0); }
//     @Override public <T> T accept(ASTVisitor<T> visitor) { return visitor.visit(this); }
//     @Override public List<ASTNode> getChildren() { return Collections.emptyList(); }
// }
