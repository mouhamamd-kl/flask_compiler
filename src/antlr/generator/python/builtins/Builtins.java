package antlr.generator.python.builtins;

import antlr.generator.runtime.EvaluatorContext;
import antlr.generator.runtime.ValueOps;
import antlr.runtime.values.RTDict;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The supported python builtin functions. Bodies are part of the generation
 * log contract — warning texts must stay verbatim.
 */
public final class Builtins {

    private Builtins() {
    }

    public static Map<String, Builtin> create() {
        Map<String, Builtin> m = new LinkedHashMap<>();
        m.put("len", Builtins::len);
        m.put("str", Builtins::str);
        m.put("int", Builtins::intOf);
        m.put("float", Builtins::floatOf);
        m.put("print", Builtins::print);
        m.put("max", (args, line, ctx) -> extremum(args, true, line, ctx));
        m.put("min", (args, line, ctx) -> extremum(args, false, line, ctx));
        m.put("sum", Builtins::sum);
        m.put("range", Builtins::range);
        m.put("dict", (args, line, ctx) -> new RTDict());
        m.put("list", Builtins::listOf);
        return Collections.unmodifiableMap(m);
    }

    private static RTValue len(List<RTValue> args, int line, EvaluatorContext ctx) {
        if (args.isEmpty()) {
            return new RTInt(0);
        }
        Integer n = ValueOps.lengthOrNull(args.get(0));
        if (n != null) {
            return new RTInt(n);
        }
        ctx.log().warn("len() of unsupported type at line " + line + "; returned 0.");
        return new RTInt(0);
    }

    private static RTValue str(List<RTValue> args, int line, EvaluatorContext ctx) {
        return new RTString(args.isEmpty() ? "" : args.get(0).toDisplayString());
    }

    private static RTValue intOf(List<RTValue> args, int line, EvaluatorContext ctx) {
        if (args.isEmpty()) {
            return new RTInt(0);
        }
        RTValue v = args.get(0);
        if (v instanceof RTInt i) {
            return i;
        }
        if (v instanceof RTFloat f) {
            return new RTInt((int) f.value);
        }
        try {
            return new RTInt((int) Double.parseDouble(v.toDisplayString().trim()));
        } catch (NumberFormatException ex) {
            ctx.log().warn("int('" + v.toDisplayString() + "') conversion failed; produced 0.");
            return new RTInt(0);
        }
    }

    private static RTValue floatOf(List<RTValue> args, int line, EvaluatorContext ctx) {
        if (args.isEmpty()) {
            return new RTFloat(0.0);
        }
        RTValue v = args.get(0);
        if (v instanceof RTFloat f) {
            return f;
        }
        if (v instanceof RTInt i) {
            return new RTFloat(i.value);
        }
        try {
            return new RTFloat(Double.parseDouble(v.toDisplayString().trim()));
        } catch (NumberFormatException ex) {
            ctx.log().warn("float('" + v.toDisplayString() + "') conversion failed; produced 0.0.");
            return new RTFloat(0.0);
        }
    }

    private static RTValue print(List<RTValue> args, int line, EvaluatorContext ctx) {
        System.out.println("   [print] " + ValueOps.joinDisplay(args, " "));
        return RTNone.NONE;
    }

    private static RTValue sum(List<RTValue> args, int line, EvaluatorContext ctx) {
        double total = 0;
        boolean ints = true;
        for (RTValue v : iterate(args.isEmpty() ? RTList.empty() : args.get(0), ctx)) {
            Double d = ValueOps.asNumberOrNull(v);
            if (d == null) {
                continue;
            }
            total += d;
            ints &= v instanceof RTInt;
        }
        return ints ? new RTInt((int) total) : new RTFloat(total);
    }

    private static RTValue range(List<RTValue> args, int line, EvaluatorContext ctx) {
        List<RTValue> items = new ArrayList<>();
        int start = 0, stop = 0, step = 1;
        if (args.size() == 1) {
            stop = ValueOps.intValueOrZero(args.get(0));
        } else if (args.size() >= 2) {
            start = ValueOps.intValueOrZero(args.get(0));
            stop = ValueOps.intValueOrZero(args.get(1));
            if (args.size() >= 3) {
                step = ValueOps.intValueOrZero(args.get(2));
            }
        }
        for (int i = start; step > 0 ? i < stop : i > stop; i += step) {
            items.add(new RTInt(i));
        }
        return new RTList(items);
    }

    private static RTValue listOf(List<RTValue> args, int line, EvaluatorContext ctx) {
        return new RTList(iterate(args.isEmpty() ? RTNone.NONE : args.get(0), ctx));
    }

    private static RTValue extremum(List<RTValue> args, boolean max, int line, EvaluatorContext ctx) {
        List<RTValue> candidates;
        if (args.size() == 1) {
            candidates = iterate(args.get(0), ctx);
        } else {
            candidates = args;
        }
        RTValue best = null;
        Double bestVal = null;
        for (RTValue v : candidates) {
            Double d = ValueOps.asNumberOrNull(v);
            if (d == null) {
                continue;
            }
            if (bestVal == null || (max ? d > bestVal : d < bestVal)) {
                bestVal = d;
                best = v;
            }
        }
        if (best == null) {
            ctx.log().warn((max ? "max" : "min") + "() found no comparable values at line "
                    + line + "; returned None.");
            return RTNone.NONE;
        }
        return best;
    }

    /** Iteration with the evaluator's once-only undefined-value warning. */
    public static List<RTValue> iterate(RTValue value, EvaluatorContext ctx) {
        if (value instanceof RTUndefined) {
            ctx.warnOnce("iter-undef", "Attempt to iterate an undefined value produced an empty sequence.");
        }
        return ValueOps.iterate(value);
    }
}
