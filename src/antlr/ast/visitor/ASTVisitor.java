package antlr.ast.visitor;

import antlr.ast.python.*;
import antlr.ast.python.expressions.*;
import antlr.ast.jinja2.*;
import antlr.ast.html.*;
import antlr.ast.css.*;

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
