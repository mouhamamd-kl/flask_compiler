package antlr.ast.python.expressions;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

/**
 * عقدة الرقم (Number Literal)
 * تمثل قيمة عددية ثابتة
 */
public class NumberNode extends ExpressionNode {

    private final int value;

    public NumberNode(int value, int lineNumber, int columnNumber) {
        super("Number", lineNumber, columnNumber);
        this.value = value;
    }

    public NumberNode(int value, int lineNumber) {
        this(value, lineNumber, 0);
    }

    public NumberNode(String valueStr, int lineNumber, int columnNumber) {
        super("Number", lineNumber, columnNumber);
        this.value = Integer.parseInt(valueStr);
    }

    // ==================== Getters ====================

    public int getValue() {
        return value;
    }

    // ==================== تطبيق الواجهات ====================

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.emptyList();  // عقدة ورقة - لا أبناء
    }

    @Override
    protected String getExtraInfo() {
        return String.format("(value: %d)", value);
    }
}
