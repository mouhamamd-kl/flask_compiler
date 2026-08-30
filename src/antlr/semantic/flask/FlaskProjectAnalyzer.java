package antlr.semantic.flask;

import antlr.ast.jinja2.TemplateNode;
import antlr.semantic.jinja.JinjaSemanticAnalyzer;
import antlr.semantic.jinja.TemplateVariableAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Project-level analysis over the render_template(...) calls captured during
 * evaluation: binds each template to the variable names its render site
 * supplies and reports variables the template references but no one provides.
 *
 * The Jinja/Flask builtin globals are always considered available, so only
 * genuinely un-satisfied references are reported and generation is aborted
 * by the caller when any finding is returned.
 */
public final class FlaskProjectAnalyzer {

    private FlaskProjectAnalyzer() {
    }

    /**
     * @param template         parsed template; null (unparseable/missing) yields no findings
     * @param templateName     source prefix used in the formatted errors
     * @param externalProvided names made available by the render site:
     *                         render_template kwargs
     * @return formatted MISSING_TEMPLATE_VARIABLE errors, one per missing name
     */
    public static List<String> checkTemplate(TemplateNode template, String templateName, Set<String> externalProvided) {
        List<String> errors = new ArrayList<>();
        if (template == null) {
            return errors;
        }
        JinjaSemanticAnalyzer analyzer = new JinjaSemanticAnalyzer(externalProvided);
        analyzer.analyze(template);
        for (TemplateVariableAnalysis.MissingTemplateVariable missing : analyzer.getMissingVariables()) {
            errors.add(String.format("%s:%d:%d [MISSING_TEMPLATE_VARIABLE] - Semantic error: "
                            + "template variable '%s' is not provided by render_template() "
                            + "and is not defined in the template",
                    templateName, missing.line(), missing.column(), missing.name()));
        }
        return errors;
    }
}