package antlr.generator.jinja;

import antlr.ast.css.CSSDeclarationNode;
import antlr.ast.css.CSSRuleNode;
import antlr.ast.css.CSSStylesheetNode;
import antlr.ast.css.properties.BoxShadowPropertyNode;
import antlr.ast.css.properties.FontFamilyPropertyNode;
import antlr.ast.css.values.CSSValueNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Serializes a parsed CSS stylesheet back to indented CSS text for inlining
 * into rendered HTML. Pure output shaping — no evaluation state.
 */
public final class CssReconstructor {

    private CssReconstructor() {
    }

    public static String reconstruct(CSSStylesheetNode sheet) {
        StringBuilder css = new StringBuilder();
        for (CSSRuleNode rule : sheet.getRules()) {
            List<String> selectors = new ArrayList<>();
            rule.getSelectors().forEach(sel -> selectors.add(sel.getSelectorText()));
            css.append("    ").append(String.join(", ", selectors)).append(" {\n");
            for (CSSDeclarationNode decl : rule.getDeclarations()) {
                css.append("        ").append(decl.getProperty()).append(": ")
                        .append(declarationText(decl)).append(";\n");
            }
            css.append("    }\n");
        }
        return css.toString();
    }

    /**
     * Serializes a declaration back to CSS text. Typed property nodes carry
     * structure the flat value list loses (font-name commas, box-shadow layer
     * separation), so prefer them when available.
     */
    private static String declarationText(CSSDeclarationNode decl) {
        antlr.ast.css.properties.CSSPropertyNode source = decl.getSourceProperty();
        if (source instanceof FontFamilyPropertyNode font) {
            return String.join(", ", font.getFontNames());
        }
        if (source instanceof BoxShadowPropertyNode shadowProp) {
            List<String> layers = new ArrayList<>();
            for (BoxShadowPropertyNode.Shadow shadow : shadowProp.getShadows()) {
                List<String> parts = new ArrayList<>();
                for (CSSValueNode v : new CSSValueNode[] {shadow.offsetX, shadow.offsetY, shadow.blur, shadow.color}) {
                    if (v != null && !v.getValueText().isEmpty()) {
                        parts.add(v.getValueText());
                    }
                }
                layers.add(String.join(" ", parts));
            }
            return String.join(", ", layers);
        }
        // Generic form: margin/padding lengths already carry keywords such as `auto`
        List<String> values = new ArrayList<>();
        decl.getValues().forEach(v -> values.add(v.getValueText()));
        return String.join(" ", values);
    }
}
