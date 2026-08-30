package antlr.semantic.jinja;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Outcome of analysing a single Jinja2 template:
 * which variable names the template provides itself ({% for %}, {% set %},
 * {% with %}, macro/import params, ...) and which names it references.
 *
 * A referenced name that is neither provided by the template nor present in
 * the names a Flask render_template(...) call passes is reported as missing.
 */
public final class TemplateVariableAnalysis {

    /** Unified template-wide provided names. */
    private final Set<String> provided = new LinkedHashSet<>();

    /** First reference site of each required name, in traversal order. */
    private final Map<String, int[]> firstReference = new LinkedHashMap<>();

    public void provide(String name) {
        if (name != null && !name.isEmpty()) {
            provided.add(name);
        }
    }

    public void require(String name, int line, int column) {
        if (name == null || name.isEmpty()) {
            return;
        }
        firstReference.putIfAbsent(name, new int[]{line, column});
    }

    public Set<String> getProvided() {
        return provided;
    }

    public Set<String> getRequired() {
        return firstReference.keySet();
    }

    /**
     * Required names that no source (template-provided, the render site's
     * context, evaluation globals, or Jinja/Flask builtins) supplies.
     *
     * @param externalProvided names available at the render site; null = none
     */
    public List<MissingTemplateVariable> computeMissing(Set<String> externalProvided) {
        Set<String> available = new LinkedHashSet<>(provided);
        if (externalProvided != null) {
            available.addAll(externalProvided);
        }
        available.addAll(JinjaBuiltins.GLOBAL_NAMES);

        List<MissingTemplateVariable> missing = new ArrayList<>();
        for (Map.Entry<String, int[]> entry : firstReference.entrySet()) {
            String name = entry.getKey();
            if (available.contains(name)) {
                continue;
            }
            int[] pos = entry.getValue();
            missing.add(new MissingTemplateVariable(name, pos[0], pos[1]));
        }
        return missing;
    }

    /** A variable referenced by the template but never supplied. */
    public record MissingTemplateVariable(String name, int line, int column) {
    }
}