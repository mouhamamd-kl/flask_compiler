package antlr.generator.runtime;

import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure value helpers shared by the python evaluator and the jinja renderer.
 * No logging here — callers keep their own warning texts.
 */
public final class ValueOps {

    private ValueOps() {
    }

    public static boolean isNumber(RTValue v) {
        return v instanceof RTInt || v instanceof RTFloat;
    }

    /** Numeric view of ints and floats; {@code null} for anything else. */
    public static Double asNumberOrNull(RTValue v) {
        if (v instanceof RTInt i) {
            return (double) i.value;
        }
        if (v instanceof RTFloat f) {
            return f.value;
        }
        return null;
    }

    /** Integral index view (ints, or floats with integral value); {@code null} otherwise. */
    public static Integer asIntIndex(RTValue v) {
        if (v instanceof RTInt i) {
            return i.value;
        }
        if (v instanceof RTFloat f && f.value == Math.rint(f.value)) {
            return (int) f.value;
        }
        return null;
    }

    public static int intValueOrZero(RTValue v) {
        Integer idx = asIntIndex(v);
        return idx == null ? 0 : idx;
    }

    /**
     * Lenient integer view: ints, integral floats, or numeric strings;
     * {@code null} otherwise. (Template-side coercion — stricter than
     * {@link #asIntIndex}.)
     */
    public static Integer coerceInt(RTValue v) {
        if (v instanceof RTInt i) {
            return i.value;
        }
        if (v instanceof RTFloat f && f.value == Math.rint(f.value)) {
            return (int) f.value;
        }
        try {
            return (int) Double.parseDouble(v.toDisplayString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int coerceIntOrZero(RTValue v) {
        Integer i = coerceInt(v);
        return i == null ? 0 : i;
    }

    /** Lenient numeric view: ints, floats, or numeric strings; {@code null} otherwise. */
    public static Double coerceDouble(RTValue v) {
        if (v instanceof RTInt i) {
            return (double) i.value;
        }
        if (v instanceof RTFloat f) {
            return f.value;
        }
        if (v instanceof RTString s) {
            try {
                return Double.parseDouble(s.value.trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /** Length of lists/dicts/strings; {@code null} for unsupported types. */
    public static Integer lengthOrNull(RTValue v) {
        if (v instanceof RTList l) {
            return l.size();
        }
        if (v instanceof RTString s) {
            return s.value.length();
        }
        if (v instanceof antlr.runtime.values.RTDict d) {
            return d.size();
        }
        return null;
    }

    /**
     * Best-effort iteration: list items, dict keys (as strings), string
     * characters, int range 0..n. Anything else iterates as empty.
     */
    public static List<RTValue> iterate(RTValue value) {
        List<RTValue> out = new ArrayList<>();
        if (value instanceof RTList list) {
            out.addAll(list.snapshot());
        } else if (value instanceof RTString str) {
            for (char c : str.value.toCharArray()) {
                out.add(new RTString(String.valueOf(c)));
            }
        } else if (value instanceof RTInt n) {
            for (int i = 0; i < n.value; i++) {
                out.add(new RTInt(i));
            }
        } else if (value instanceof antlr.runtime.values.RTDict dict) {
            for (String key : dict.keys()) {
                out.add(new RTString(key));
            }
        }
        return out;
    }

    public static String capitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    public static String titleCase(String s) {
        StringBuilder sb = new StringBuilder();
        boolean upperNext = true;
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(upperNext ? Character.toUpperCase(c) : Character.toLowerCase(c));
                upperNext = false;
            } else {
                sb.append(c);
                upperNext = true;
            }
        }
        return sb.toString();
    }

    public static String joinDisplay(List<RTValue> values, String separator) {
        List<String> parts = new ArrayList<>();
        for (RTValue v : values) {
            parts.add(v.toDisplayString());
        }
        return String.join(separator, parts);
    }

    public static List<antlr.runtime.values.RTValue> toRTValues(List<String> values) {
        List<antlr.runtime.values.RTValue> out = new ArrayList<>();
        for (String s : values) {
            out.add(new RTString(s));
        }
        return out;
    }
}
