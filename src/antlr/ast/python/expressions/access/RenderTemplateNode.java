package antlr.ast.python.expressions.access;

import antlr.ast.node.ASTNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * عقدة استدعاء render_template
 * تمثل: render_template('template.html', key=value, ...)
 * الوسيط الأول هو اسم القالب والوسائط المسماة هي متغيرات السياق
 */
public class RenderTemplateNode extends ExpressionNode {

    private String templateName;
    private final List<KeywordArgumentNode> contextVariables;

    public RenderTemplateNode(int lineNumber, int columnNumber) {
        super("RenderTemplate", lineNumber, columnNumber);
        this.contextVariables = new ArrayList<>();
    }

    public RenderTemplateNode(String templateName, int lineNumber, int columnNumber) {
        this(lineNumber, columnNumber);
        this.templateName = templateName;
    }

    // ==================== Getters & Setters ====================

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    // ==================== إدارة متغيرات السياق ====================

    public void addContextVariable(KeywordArgumentNode node) {
        if (node != null) {
            node.setParent(this);
            contextVariables.add(node);
        }
    }

    public List<KeywordArgumentNode> getContextVariables() {
        return Collections.unmodifiableList(contextVariables);
    }

    public int getContextVariablesCount() {
        return contextVariables.size();
    }

    // ==================== تطبيق الواجهات ====================

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(contextVariables);
    }

    @Override
    protected String getExtraInfo() {
        return String.format("(template: \"%s\", context: %d)", templateName, contextVariables.size());
    }

    @Override
    public String toValueString() {
        return templateName;
    }
}
