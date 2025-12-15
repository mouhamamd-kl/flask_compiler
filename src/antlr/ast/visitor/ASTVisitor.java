package antlr.ast.visitor;

import antlr.ast.css.StylesheetNode;
import antlr.ast.html.HtmlDocumentNode;
import antlr.ast.jinja2.TemplateNode;
import antlr.ast.python.AssignmentNode;
import antlr.ast.python.BlockNode;
import antlr.ast.python.IfStatementNode;
import antlr.ast.python.PrintNode;
import antlr.ast.python.ProgramNode;
import antlr.ast.python.expressions.BinaryOpNode;
import antlr.ast.python.expressions.BooleanNode;
import antlr.ast.python.expressions.ComparisonNode;
import antlr.ast.python.expressions.ListNode;
import antlr.ast.python.expressions.LogicalOpNode;
import antlr.ast.python.expressions.NumberNode;
import antlr.ast.python.expressions.UnaryOpNode;
import antlr.ast.python.expressions.VariableNode;

/**
 * واجهة الزائر (Visitor Interface)
 * تطبيق نمط Visitor Pattern للتعامل مع الشجرة
 *
 * @param <T> نوع القيمة المُرجعة من كل زيارة
 */
public interface ASTVisitor<T> {

    // ==================== Python Nodes ====================

    T visit(ProgramNode node);
    T visit(BlockNode node);
    T visit(AssignmentNode node);
    T visit(PrintNode node);
    T visit(IfStatementNode node);

    // ==================== Expression Nodes ====================

    T visit(BinaryOpNode node);
    T visit(UnaryOpNode node);
    T visit(NumberNode node);
    T visit(VariableNode node);
    T visit(BooleanNode node);
    T visit(ListNode node);
    // ==================== Condition Nodes ====================

    T visit(ComparisonNode node);
    T visit(LogicalOpNode node);

    // ==================== Jinja2 Nodes ====================

    T visit(TemplateNode node);
    //TODO in the future for Jinja2
    // T visit(ExpressionBlockNode node);
    // T visit(StatementBlockNode node);
    // T visit(ForLoopNode node);
    // T visit(JinjaIfNode node);
    // T visit(IncludeNode node);
    // T visit(ExtendsNode node);
    // T visit(BlockDefinitionNode node);
    // T visit(RawTextNode node);

    // ==================== HTML Nodes ====================

    T visit(HtmlDocumentNode node);
    //TODO in the future for HTML
    // T visit(HtmlElementNode node);
    // T visit(HtmlAttributeNode node);
    // T visit(HtmlTextNode node);
    // T visit(HtmlCommentNode node);
    // T visit(DoctypeNode node);

    // ==================== CSS Nodes ====================

    T visit(StylesheetNode node);
    //TODO in the future for CSS
    // T visit(CssRuleNode node);
    // T visit(SelectorNode node);
    // T visit(DeclarationNode node);
    // T visit(CssValueNode node);
}
