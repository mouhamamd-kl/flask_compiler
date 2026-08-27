package antlr.generator.jinja;

import antlr.generator.runtime.ValueOps;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;

import java.util.Collections;
import java.util.List;

/**
 * The supported Jinja2 filters, applied to evaluated values. Warning texts
 * are part of the generation log contract — keep verbatim.
 */
public final class FilterRegistry {

    /** Once-only warning sink owned by the caller (renderer). */
    @FunctionalInterface
    public interface Warn {
        void once(String key, String message);
    }

    private final Warn warn;

    public FilterRegistry(Warn warn) {
        this.warn = warn;
    }

    public RTValue applyNamedFilter(RTValue value, String name, List<RTValue> args) {
        switch (name) {
            case "upper":
                return new RTString(value.toDisplayString().toUpperCase());
            case "lower":
                return new RTString(value.toDisplayString().toLowerCase());
            case "capitalize":
                return new RTString(ValueOps.capitalize(value.toDisplayString()));
            case "title":
                return new RTString(ValueOps.titleCase(value.toDisplayString()));
            case "trim":
                return new RTString(value.toDisplayString().trim());
            case "length":
            case "count": {
                Integer n = ValueOps.lengthOrNull(value);
                return new RTInt(n == null ? 0 : n);
            }
            case "first": {
                List<RTValue> items = iterate(value);
                return items.isEmpty() ? RTUndefined.UNDEFINED : items.get(0);
            }
            case "last": {
                List<RTValue> items = iterate(value);
                return items.isEmpty() ? RTUndefined.UNDEFINED : items.get(items.size() - 1);
            }
            case "reverse": {
                List<RTValue> items = iterate(value);
                Collections.reverse(items);
                if (value instanceof RTString) {
                    StringBuilder sb = new StringBuilder();
                    for (RTValue v : items) {
                        sb.append(v.toDisplayString());
                    }
                    return new RTString(sb.toString());
                }
                return new RTList(items);
            }
            case "sort": {
                List<RTValue> items = iterate(value);
                items.sort((a, b) -> {
                    Double da = ValueOps.coerceDouble(a);
                    Double db = ValueOps.coerceDouble(b);
                    if (da != null && db != null) {
                        return Double.compare(da, db);
                    }
                    return a.toDisplayString().compareTo(b.toDisplayString());
                });
                return new RTList(items);
            }
            case "join": {
                String sep = args.isEmpty() ? "" : args.get(0).toDisplayString();
                List<String> parts = new java.util.ArrayList<>();
                for (RTValue v : iterate(value)) {
                    parts.add(v.toDisplayString());
                }
                return new RTString(String.join(sep, parts));
            }
            case "round": {
                Double d = ValueOps.coerceDouble(value);
                if (d == null) {
                    return value;
                }
                int precision = args.isEmpty() ? 0 : ValueOps.coerceIntOrZero(args.get(0));
                double factor = Math.pow(10, precision);
                double rounded = Math.round(d * factor) / factor;
                return precision > 0 ? new RTFloat(rounded) : new RTFloat(Math.round(rounded));
            }
            case "int": {
                Integer i = ValueOps.coerceInt(value);
                if (i != null) {
                    return new RTInt(i);
                }
                try {
                    return new RTInt((int) Double.parseDouble(value.toDisplayString().trim()));
                } catch (NumberFormatException e) {
                    return args.isEmpty() ? new RTInt(0) : args.get(0);
                }
            }
            case "float": {
                Double d = ValueOps.coerceDouble(value);
                if (d != null) {
                    return new RTFloat(d);
                }
                try {
                    return new RTFloat(Double.parseDouble(value.toDisplayString().trim()));
                } catch (NumberFormatException e) {
                    return args.isEmpty() ? new RTFloat(0.0) : args.get(0);
                }
            }
            case "string":
                return new RTString(value.toDisplayString());
            case "default":
            case "d": {
                if (value instanceof RTUndefined || value instanceof RTNone
                        || (value instanceof RTString s && s.value.isEmpty())) {
                    return args.isEmpty() ? RTNone.NONE : args.get(0);
                }
                return value;
            }
            case "escape":
            case "e":
                return new RTString(htmlEscape(value.toDisplayString()));
            case "safe":
                return value; // autoescaping is off during generation
            case "replace": {
                if (args.size() >= 2) {
                    return new RTString(value.toDisplayString().replace(args.get(0).toDisplayString(),
                            args.get(1).toDisplayString()));
                }
                return value;
            }
            default:
                warn.once("filter:" + name, "Unknown filter '" + name + "' applied as identity (no-op).");
                return value;
        }
    }

    private List<RTValue> iterate(RTValue value) {
        if (value instanceof RTUndefined) {
            warn.once("iter-undef", "Attempt to iterate an undefined value produced an empty sequence.");
        }
        return ValueOps.iterate(value);
    }

    private static String htmlEscape(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
