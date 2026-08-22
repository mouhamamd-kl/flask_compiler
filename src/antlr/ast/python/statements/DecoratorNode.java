package antlr.ast.python.statements;

import antlr.ast.node.ASTNode;
import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.visitor.ASTVisitor;

import java.util.Collections;
import java.util.List;

/**
 * عقدة الديكوريتر
 * تمثل: @expr
 * مثال: @app.route('/')
 */
public class DecoratorNode extends StatementNode {

    private ExpressionNode expression;

    public DecoratorNode(int lineNumber, int columnNumber) {
        super("Decorator", lineNumber, columnNumber);
    }

    public DecoratorNode(ExpressionNode expression, int lineNumber, int columnNumber) {
        this(lineNumber, columnNumber);
        setExpression(expression);
    }

    // ==================== Getters & Setters ====================

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        if (expression != null) {
            expression.setParent(this);
        }
        this.expression = expression;
    }

    // ==================== تطبيق الواجهات ====================

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        if (expression == null) {
            return Collections.emptyList();
        }
        return List.of(expression);
    }

    @Override
    protected String getExtraInfo() {
        return "";
    }

    public String toValueString() {
        return expression != null ? expression.toValueString() : null;
    }
}
