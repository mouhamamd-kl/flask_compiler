package antlr.semantic.jinja;

import java.util.Arrays;
import java.util.Set;

/**
 * Names that are always in scope inside a rendered Jinja2/Flask template:
 * Jinja's builtin globals plus the template globals Flask injects.
 * Also carries the standard filter/test vocabularies for future checks.
 */
public final class JinjaBuiltins {

    private JinjaBuiltins() {
    }

    /**
     * Global names visible in any template without being passed to
     * render_template(...): Jinja2 builtin globals + Flask template globals.
     */
    public static final Set<String> GLOBAL_NAMES = readonly(
            // Jinja2 builtin globals
            "range", "dict", "cycler", "joiner", "namespace", "lipsum",
            // Flask template globals / context processors
            "config", "request", "session", "g", "url_for",
            "get_flashed_messages", "current_app"
    );

    /** Jinja2 builtin filters (standard library). */
    public static final Set<String> FILTER_NAMES = readonly(
            "abs", "attr", "batch", "capitalize", "center", "default", "dictsort",
            "e", "escape", "filesizeformat", "first", "float", "forceescape",
            "format", "groupby", "indent", "int", "join", "last", "length",
            "list", "lower", "map", "max", "min", "pprint", "random", "reject",
            "rejectattr", "replace", "reverse", "round", "safe", "select",
            "selectattr", "slice", "sort", "string", "striptags", "sum", "title",
            "tojson", "trim", "truncate", "unique", "upper", "urlencode",
            "urlize", "wordcount", "wordwrap", "xmlattr"
    );

    /** Jinja2 builtin tests ({% if x is defined %} etc.). */
    public static final Set<String> TEST_NAMES = readonly(
            "defined", "divisibleby", "eq", "escaped", "even", "false", "filter",
            "float", "ge", "gt", "in", "integer", "iterable", "le", "lower",
            "lt", "mapping", "ne", "none", "number", "odd", "sameas", "sequence",
            "string", "test", "true", "undefined", "upper"
    );

    private static Set<String> readonly(String... names) {
        return Set.copyOf(Arrays.asList(names));
    }
}