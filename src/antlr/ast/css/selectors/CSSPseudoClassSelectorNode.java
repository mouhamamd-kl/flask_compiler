package antlr.ast.css.selectors;

import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitor;
import java.util.Collections;
import java.util.List;

/**
 * Pseudo-class selector node (e.g., :hover, :focus)
 */
public class CSSPseudoClassSelectorNode extends CSSSelectorNode {
    private final String pseudoClass;
    // Optional simple selector the pseudo-class is attached to (e.g. `.button` in `.button:hover`)
    private final CSSSelectorNode baseSelector;

    public CSSPseudoClassSelectorNode(String pseudoClass, int lineNumber, int columnNumber) {
        this(null, pseudoClass, lineNumber, columnNumber);
    }

    public CSSPseudoClassSelectorNode(CSSSelectorNode baseSelector, String pseudoClass, int lineNumber, int columnNumber) {
        super("CSSPseudoClassSelector", lineNumber, columnNumber);
        this.baseSelector = baseSelector;
        if (baseSelector != null) {
            baseSelector.setParent(this);
        }
        this.pseudoClass = pseudoClass;
    }

    public CSSPseudoClassSelectorNode(String pseudoClass, int lineNumber) {
        this(pseudoClass, lineNumber, 0);
    }

    public String getPseudoClass() {
        return pseudoClass;
    }

    public CSSSelectorNode getBaseSelector() {
        return baseSelector;
    }

    @Override
    public String getSelectorText() {
        if (baseSelector != null) {
            return baseSelector.getSelectorText() + ":" + pseudoClass;
        }
        return ":" + pseudoClass;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    protected String getExtraInfo() {
        return String.format("pseudo='%s'", pseudoClass);
    }
}
