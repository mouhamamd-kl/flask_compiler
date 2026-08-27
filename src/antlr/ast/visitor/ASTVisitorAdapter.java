package antlr.ast.visitor;

import antlr.ast.css.*;
import antlr.ast.css.properties.*;
import antlr.ast.css.selectors.*;
import antlr.ast.css.values.*;
import antlr.ast.jinja2.TemplateNode;
import antlr.ast.jinja2.blocks.*;
import antlr.ast.jinja2.content.*;
import antlr.ast.jinja2.content.elements.*;
import antlr.ast.jinja2.content.elements.document.*;
import antlr.ast.jinja2.content.elements.sectioning.*;
import antlr.ast.jinja2.content.elements.text.*;
import antlr.ast.jinja2.content.elements.form.*;
import antlr.ast.jinja2.content.elements.media.*;
import antlr.ast.jinja2.content.elements.embedded.*;
import antlr.ast.jinja2.expressions.*;
import antlr.ast.jinja2.expressions.literals.*;
import antlr.ast.jinja2.expressions.operations.*;
import antlr.ast.jinja2.statements.*;
import antlr.ast.jinja2.targets.*;

import antlr.ast.python.ProgramNode;
import antlr.ast.python.expressions.access.DotAccessNode;
import antlr.ast.python.expressions.access.FunctionCallNode;
import antlr.ast.python.expressions.access.IndexAccessNode;
import antlr.ast.python.expressions.access.KeywordArgumentNode;
import antlr.ast.python.expressions.access.RenderTemplateNode;
import antlr.ast.python.expressions.access.VariableNode;
import antlr.ast.python.expressions.literals.BooleanNode;
import antlr.ast.python.expressions.literals.DictEntryNode;
import antlr.ast.python.expressions.literals.DictNode;
import antlr.ast.python.expressions.literals.ListNode;
import antlr.ast.python.expressions.literals.NumberDoubleNode;
import antlr.ast.python.expressions.literals.NumberIntegerNode;
import antlr.ast.python.expressions.literals.StringNode;
import antlr.ast.python.expressions.operations.BinaryOpNode;
import antlr.ast.python.expressions.operations.ComparisonNode;
import antlr.ast.python.expressions.operations.LogicalOpNode;
import antlr.ast.python.expressions.operations.UnaryOpNode;
import antlr.ast.python.parameters.KeywordOnlyParamNode;
import antlr.ast.python.parameters.KeywordOnlySeparator;
import antlr.ast.python.parameters.KwargsNode;
import antlr.ast.python.parameters.ParameterNode;
import antlr.ast.python.parameters.PositionalOnlyParamNode;
import antlr.ast.python.parameters.RegularParamNode;
import antlr.ast.python.parameters.StarArgsNode;
import antlr.ast.python.expressions.ListComprehensionNode;
import antlr.ast.python.expressions.literals.NoneNode;
import antlr.ast.python.statements.AssignmentNode;
import antlr.ast.python.statements.BlockNode;
import antlr.ast.python.statements.BreakNode;
import antlr.ast.python.statements.ClassDefinitionNode;
import antlr.ast.python.statements.ContinueNode;
import antlr.ast.python.statements.DecoratorNode;
import antlr.ast.python.statements.ExpressionStatementNode;
import antlr.ast.python.statements.ForStatementNode;
import antlr.ast.python.statements.FunctionDefinitionNode;
import antlr.ast.python.statements.IfStatementNode;
import antlr.ast.python.statements.ImportStatement;
import antlr.ast.python.statements.ImportedClassNode;
import antlr.ast.python.statements.PrintNode;
import antlr.ast.python.statements.ReturnNode;
import antlr.ast.python.statements.WhileStatementNode;

/**
 * Base adapter for ASTVisitor: every visit method returns null by default.
 * Subclasses override only the node types they care about.
 *
 * @param <T> visitor return type
 */
public abstract class ASTVisitorAdapter<T> implements ASTVisitor<T> {

    // ==================== Python Nodes ====================
    @Override public T visit(ProgramNode node) { return null; }

    @Override public T visit(BlockNode node) { return null; }

    @Override public T visit(AssignmentNode node) { return null; }

    @Override public T visit(PrintNode node) { return null; }

    @Override public T visit(ReturnNode node) { return null; }

    @Override public T visit(IfStatementNode node) { return null; }

    @Override public T visit(ForStatementNode node) { return null; }

    @Override public T visit(WhileStatementNode node) { return null; }

    @Override public T visit(BreakNode node) { return null; }

    @Override public T visit(ContinueNode node) { return null; }

    @Override public T visit(ExpressionStatementNode node) { return null; }

    @Override public T visit(ImportedClassNode node) { return null; }

    @Override public T visit(ImportStatement node) { return null; }

    @Override public T visit(KeywordOnlyParamNode node) { return null; }

    @Override public T visit(KwargsNode node) { return null; }

    @Override public T visit(ParameterNode node) { return null; }

    @Override public T visit(PositionalOnlyParamNode node) { return null; }

    @Override public T visit(RegularParamNode node) { return null; }

    @Override public T visit(StarArgsNode node) { return null; }

    @Override public T visit(KeywordOnlySeparator node) { return null; }

    @Override public T visit(FunctionDefinitionNode node) { return null; }

    @Override public T visit(ClassDefinitionNode node) { return null; }

    @Override public T visit(DecoratorNode node) { return null; }

    // ==================== Expression Nodes ====================
    @Override public T visit(BinaryOpNode node) { return null; }

    @Override public T visit(UnaryOpNode node) { return null; }

    @Override public T visit(NumberIntegerNode node) { return null; }

    @Override public T visit(NumberDoubleNode node) { return null; }

    @Override public T visit(VariableNode node) { return null; }

    @Override public T visit(BooleanNode node) { return null; }

    @Override public T visit(NoneNode node) { return null; }

    @Override public T visit(ListNode node) { return null; }

    @Override public T visit(ListComprehensionNode node) { return null; }

    @Override public T visit(StringNode node) { return null; }

    @Override public T visit(DictNode node) { return null; }

    @Override public T visit(DictEntryNode node) { return null; }

    @Override public T visit(IndexAccessNode node) { return null; }

    @Override public T visit(DotAccessNode node) { return null; }

    @Override public T visit(FunctionCallNode node) { return null; }

    @Override public T visit(RenderTemplateNode node) { return null; }

    @Override public T visit(KeywordArgumentNode node) { return null; }

    // ==================== Condition Nodes ====================
    @Override public T visit(ComparisonNode node) { return null; }

    @Override public T visit(LogicalOpNode node) { return null; }

    // ==================== Jinja2 Nodes ====================
    @Override public T visit(TemplateNode node) { return null; }

    @Override public T visit(HtmlTextNode node) { return null; }

    @Override public T visit(ExpressionBlockNode node) { return null; }

    @Override public T visit(ForBlockNode node) { return null; }

    @Override public T visit(IfBlockNode node) { return null; }

    @Override public T visit(ElifBranchNode node) { return null; }

    @Override public T visit(ElseBranchNode node) { return null; }

    @Override public T visit(JinjaExpressionNode node) { return null; }

    @Override public T visit(FilterNode node) { return null; }

    @Override public T visit(ExtendsNode node) { return null; }

    @Override public T visit(IncludeNode node) { return null; }

    @Override public T visit(BlockDefinitionNode node) { return null; }

    @Override public T visit(SetStatementNode node) { return null; }

    @Override public T visit(SetBlockNode node) { return null; }

    @Override public T visit(DoStatementNode node) { return null; }

    @Override public T visit(ImportNode node) { return null; }

    @Override public T visit(FromImportNode node) { return null; }

    @Override public T visit(FromImportItemNode node) { return null; }

    // Jinja2 Blocks
    @Override public T visit(MacroNode node) { return null; }

    @Override public T visit(MacroParamNode node) { return null; }

    @Override public T visit(WithBlockNode node) { return null; }

    @Override public T visit(WithAssignmentNode node) { return null; }

    @Override public T visit(FilterBlockNode node) { return null; }

    @Override public T visit(CallBlockNode node) { return null; }

    @Override public T visit(AutoescapeBlockNode node) { return null; }

    @Override public T visit(EscapeModeNode node) { return null; }

    @Override public T visit(BooleanEscapeModeNode node) { return null; }

    @Override public T visit(StringEscapeModeNode node) { return null; }

    // Jinja2 Expressions
    @Override public T visit(VariableExprNode node) { return null; }

    @Override public T visit(DotAccessExprNode node) { return null; }

    @Override public T visit(IndexAccessExprNode node) { return null; }

    @Override public T visit(FunctionCallExprNode node) { return null; }

    @Override public T visit(ParenExprNode node) { return null; }

    @Override public T visit(SliceExprNode node) { return null; }

    @Override public T visit(TernaryExprNode node) { return null; }

    @Override public T visit(TestExprNode node) { return null; }

    @Override public T visit(ArgumentNode node) { return null; }

    // Jinja2 Literals
    @Override public T visit(JinjaBooleanNode node) { return null; }

    @Override public T visit(JinjaStringNode node) { return null; }

    @Override public T visit(JinjaNumberIntegerNode node) { return null; }

    @Override public T visit(JinjaNumberDoubleNode node) { return null; }

    @Override public T visit(JinjaNoneNode node) { return null; }

    @Override public T visit(JinjaListNode node) { return null; }

    @Override public T visit(JinjaDictNode node) { return null; }

    @Override public T visit(JinjaDictEntryNode node) { return null; }

    // Jinja2 Operations
    @Override public T visit(JinjaBinaryOpNode node) { return null; }

    @Override public T visit(JinjaUnaryOpNode node) { return null; }

    @Override public T visit(JinjaComparisonNode node) { return null; }

    @Override public T visit(JinjaLogicalOpNode node) { return null; }

    // Jinja2 Targets
    @Override public T visit(TargetNode node) { return null; }

    @Override public T visit(SimpleTargetNode node) { return null; }

    @Override public T visit(TupleTargetNode node) { return null; }

    @Override public T visit(NamespaceTargetNode node) { return null; }

    // ==================== HTML Nodes ====================
    @Override public T visit(HtmlElementNode node) { return null; }

    @Override public T visit(HtmlAttributeValueNode node) { return null; }

    @Override public T visit(HtmlRootHtmlNode node) { return null; }

    @Override public T visit(HeadHtmlNode node) { return null; }

    @Override public T visit(BodyHtmlNode node) { return null; }

    @Override public T visit(TitleHtmlNode node) { return null; }

    @Override public T visit(DivHtmlNode node) { return null; }

    @Override public T visit(H1HtmlNode node) { return null; }

    @Override public T visit(ParagraphHtmlNode node) { return null; }

    @Override public T visit(AnchorHtmlNode node) { return null; }

    @Override public T visit(FormHtmlNode node) { return null; }

    @Override public T visit(LabelHtmlNode node) { return null; }

    @Override public T visit(TextareaHtmlNode node) { return null; }

    @Override public T visit(ButtonHtmlNode node) { return null; }

    @Override public T visit(InputHtmlNode node) { return null; }

    @Override public T visit(ImgHtmlNode node) { return null; }

    @Override public T visit(StyleHtmlNode node) { return null; }

    @Override public T visit(GenericHtmlNode node) { return null; }

    @Override public T visit(HtmlAttributeNode node) { return null; }

    // ==================== CSS Nodes ====================
    @Override public T visit(StylesheetNode node) { return null; }

    @Override public T visit(CSSStylesheetNode node) { return null; }

    @Override public T visit(CSSRuleNode node) { return null; }

    @Override public T visit(CSSDeclarationNode node) { return null; }

    @Override public T visit(CSSElementSelectorNode node) { return null; }

    @Override public T visit(CSSClassSelectorNode node) { return null; }

    @Override public T visit(CSSPseudoClassSelectorNode node) { return null; }

    @Override public T visit(CSSDescendantSelectorNode node) { return null; }

    @Override public T visit(LengthPropertyNode node) { return null; }

    @Override public T visit(MultiLengthPropertyNode node) { return null; }

    @Override public T visit(ColorPropertyNode node) { return null; }

    @Override public T visit(KeywordPropertyNode node) { return null; }

    @Override public T visit(BoxShadowPropertyNode node) { return null; }

    @Override public T visit(BorderPropertyNode node) { return null; }

    @Override public T visit(TransitionPropertyNode node) { return null; }

    @Override public T visit(TransformPropertyNode node) { return null; }

    @Override public T visit(BackgroundPropertyNode node) { return null; }

    @Override public T visit(FontFamilyPropertyNode node) { return null; }

    @Override public T visit(FontWeightPropertyNode node) { return null; }

    @Override public T visit(BoxSizingPropertyNode node) { return null; }

    @Override public T visit(OutlinePropertyNode node) { return null; }

    @Override public T visit(CSSLengthValueNode node) { return null; }

    @Override public T visit(CSSColorValueNode node) { return null; }

    @Override public T visit(CSSKeywordValueNode node) { return null; }

    @Override public T visit(CSSNumberValueNode node) { return null; }

    @Override public T visit(CSSTimeValueNode node) { return null; }

    @Override public T visit(CSSIdentValueNode node) { return null; }

    @Override public T visit(CSSFunctionValueNode node) { return null; }
}
