package antlr.ast.python;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import antlr.ast.python.expressions.ExpressionNode;
import java.util.ArrayList;
import java.util.List;

/**
 * عقدة الإسناد (Assignment)
 * تمثل: variable = expression
 *
 * مثال: x = 5 + 3
 */
public class AssignmentNode extends StatementNode {

    private final String variableName;
    private final ExpressionNode value;

    public AssignmentNode(String variableName, ExpressionNode value,
                          int lineNumber, int columnNumber) {
        super("Assignment", lineNumber, columnNumber);
        this.variableName = variableName;
        this.value = value;

        if (value != null) {
            value.setParent(this);
        }
    }

    public AssignmentNode(String variableName, ExpressionNode value, int lineNumber) {
        this(variableName, value, lineNumber, 0);
    }

    // ==================== Getters ====================

    public String getVariableName() {
        return variableName;
    }

    public ExpressionNode getValue() {
        return value;
    }

    // ==================== تطبيق الواجهات ====================

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (value != null) {
            children.add(value);
        }
        return children;
    }

    @Override
    protected String getExtraInfo() {
        return String.format("(var: %s)", variableName);
    }
}
