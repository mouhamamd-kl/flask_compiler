package antlr.ast.css;

import antlr.ast.css.values.CSSValueNode;
import antlr.ast.css.properties.CSSPropertyNode;
import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * CSS declaration containing property name and values
 * Example: color: red; or box-shadow: 0 1px 3px rgba(0,0,0,0.1), 0 1px 2px rgba(0,0,0,0.06);
 */
public class CSSDeclarationNode extends CSSNode {
    private final String property;
    private final List<CSSValueNode> values;
    // Original typed property node this declaration was built from (may be null)
    private CSSPropertyNode sourceProperty;

    public CSSDeclarationNode(String property, int lineNumber, int columnNumber) {
        super("CSSDeclaration", lineNumber, columnNumber);
        this.property = property;
        this.values = new ArrayList<>();
    }

    public CSSDeclarationNode(String property, int lineNumber) {
        this(property, lineNumber, 0);
    }

    public void addValue(CSSValueNode value) {
        if (value != null) {
            values.add(value);
            value.setParent(this);
        }
    }

    public String getProperty() {
        return property;
    }

    public List<CSSValueNode> getValues() {
        return values;
    }

    public CSSPropertyNode getSourceProperty() {
        return sourceProperty;
    }

    public void setSourceProperty(CSSPropertyNode sourceProperty) {
        this.sourceProperty = sourceProperty;
        if (sourceProperty != null) {
            sourceProperty.setParent(this);
        }
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(values);
    }

    @Override
    protected String getExtraInfo() {
        return String.format("property='%s' (%d values)", property, values.size());
    }
}
