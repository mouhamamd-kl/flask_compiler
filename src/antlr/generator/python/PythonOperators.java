package antlr.generator.python;

import antlr.ast.python.expressions.operations.BinaryOpNode;
import antlr.ast.python.expressions.operations.ComparisonNode;
import antlr.generator.runtime.EvaluatorContext;
import antlr.generator.runtime.ValueOps;
import antlr.runtime.values.RTBool;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Python arithmetic, comparison and equality semantics for the evaluator.
 * Warning texts are part of the generation log contract — keep verbatim.
 */
public final class PythonOperators {

    private PythonOperators() {
    }

    public static RTValue arith(BinaryOpNode.Operator op, RTValue l, RTValue r, int line, EvaluatorContext ctx) {
        if (!ValueOps.isNumber(l) || !ValueOps.isNumber(r)) {
            if (op == BinaryOpNode.Operator.ADD) {
                if (l instanceof RTString || r instanceof RTString) {
                    return new RTString(l.toDisplayString() + r.toDisplayString());
                }
                if (l instanceof RTList a && r instanceof RTList b) {
                    List<RTValue> merged = a.snapshot();
                    merged.addAll(b.snapshot());
                    return new RTList(merged);
                }
            }
            ctx.warnOnce("arith", "Line " + line + ": arithmetic on non-numeric operands produced None.");
            return RTNone.NONE;
        }
        double x = ValueOps.asNumberOrNull(l);
        double y = ValueOps.asNumberOrNull(r);
        boolean bothInts = l instanceof RTInt && r instanceof RTInt;
        double result;
        switch (op) {
            case ADD:
                result = x + y;
                break;
            case SUB:
                result = x - y;
                break;
            case MUL:
                result = x * y;
                break;
            case DIV:
                if (y == 0) {
                    ctx.warn("Line " + line + ": division by zero produced None.");
                    return RTNone.NONE;
                }
                return new RTFloat(x / y); // python3 division is always float
            case POW:
                result = Math.pow(x, y);
                break;
            default:
                return RTNone.NONE;
        }
        if (bothInts && result == Math.rint(result) && Math.abs(result) < 1e15) {
            return new RTInt((int) result);
        }
        return new RTFloat(result);
    }

    public static boolean compare(ComparisonNode.Operator op, RTValue l, RTValue r, EvaluatorContext ctx) {
        if (op == ComparisonNode.Operator.EQ) {
            return valuesEqual(l, r);
        }
        if (op == ComparisonNode.Operator.NEQ) {
            return !valuesEqual(l, r);
        }
        Double a = ValueOps.asNumberOrNull(l);
        Double b = ValueOps.asNumberOrNull(r);
        int cmp;
        if (a != null && b != null) {
            cmp = Double.compare(a, b);
        } else if (l instanceof RTString s1 && r instanceof RTString s2) {
            cmp = s1.value.compareTo(s2.value);
        } else {
            ctx.warnOnce("order", "Ordered comparison between incompatible types evaluated to False.");
            return false;
        }
        switch (op) {
            case LT:
                return cmp < 0;
            case GT:
                return cmp > 0;
            case LTE:
                return cmp <= 0;
            case GTE:
                return cmp >= 0;
            default:
                return false;
        }
    }

    public static boolean valuesEqual(RTValue l, RTValue r) {
        if (l instanceof RTUndefined || r instanceof RTUndefined) {
            return false;
        }
        if (ValueOps.isNumber(l) && ValueOps.isNumber(r)) {
            Double a = ValueOps.asNumberOrNull(l);
            Double b = ValueOps.asNumberOrNull(r);
            return a.doubleValue() == b.doubleValue();
        }
        if (l instanceof RTList la && r instanceof RTList rb) {
            if (la.size() != rb.size()) {
                return false;
            }
            for (int i = 0; i < la.size(); i++) {
                if (!valuesEqual(la.get(i), rb.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return l.equals(r) || l == r;
    }
}
