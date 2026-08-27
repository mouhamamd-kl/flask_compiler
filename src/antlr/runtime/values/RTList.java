package antlr.runtime.values;

import java.util.ArrayList;
import java.util.List;

/**
 * Python list. Elements are indexed from 0.
 */
public class RTList extends RTValue {

    private final List<RTValue> items;

    public RTList(List<RTValue> items) {
        this.items = new ArrayList<>(items);
    }

    public static RTList empty() {
        return new RTList(new ArrayList<>());
    }

    /** Defensive copy of the current elements, in order. */
    public List<RTValue> snapshot() {
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }

    public RTValue get(int index) {
        if (index < 0 || index >= items.size()) {
            return RTUndefined.UNDEFINED;
        }
        return items.get(index);
    }

    public void set(int index, RTValue value) {
        while (items.size() <= index) {
            items.add(RTNone.NONE);
        }
        items.set(index, value);
    }

    public void append(RTValue value) {
        items.add(value);
    }

    @Override
    public MethodResult callMethod(String name, java.util.List<RTValue> args) {
        switch (name) {
            case "append":
                if (!args.isEmpty()) {
                    append(args.get(0));
                }
                return MethodResult.ok(RTNone.NONE);
            case "extend": {
                List<RTValue> extra = new ArrayList<>();
                if (!args.isEmpty()) {
                    RTValue src = args.get(0);
                    if (src instanceof RTList other) {
                        extra.addAll(other.items);
                    } else if (src instanceof RTString s) {
                        for (char c : s.value.toCharArray()) {
                            extra.add(new RTString(String.valueOf(c)));
                        }
                    } else if (src instanceof RTInt n) {
                        for (int i = 0; i < n.value; i++) {
                            extra.add(new RTInt(i));
                        }
                    }
                }
                items.addAll(extra);
                return MethodResult.ok(RTNone.NONE);
            }
            case "insert":
                if (args.size() >= 2) {
                    int idx = Math.max(0, Math.min(items.size(), asInt(args.get(0))));
                    items.add(idx, args.get(1));
                }
                return MethodResult.ok(RTNone.NONE);
            case "pop":
                if (!items.isEmpty()) {
                    int idx = args.isEmpty() ? items.size() - 1 : asInt(args.get(0));
                    if (idx < 0) idx += items.size();
                    if (idx >= 0 && idx < items.size()) {
                        return MethodResult.ok(items.remove(idx));
                    }
                }
                return MethodResult.ok(RTNone.NONE);
            case "sort":
                items.sort((a, b) -> {
                    Double da = a instanceof RTInt i ? (double) i.value
                            : a instanceof RTFloat f ? f.value : null;
                    Double db = b instanceof RTInt i ? (double) i.value
                            : b instanceof RTFloat f ? f.value : null;
                    if (da != null && db != null) {
                        return Double.compare(da, db);
                    }
                    return a.toDisplayString().compareTo(b.toDisplayString());
                });
                return MethodResult.ok(RTNone.NONE);
            case "clear":
                items.clear();
                return MethodResult.ok(RTNone.NONE);
            default:
                return MethodResult.unsupported();
        }
    }

    private static int asInt(RTValue v) {
        if (v instanceof RTInt i) {
            return i.value;
        }
        if (v instanceof RTFloat f && f.value == Math.rint(f.value)) {
            return (int) f.value;
        }
        return 0;
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public boolean isTruthy() {
        return !items.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(items.get(i));
        }
        return sb.append("]").toString();
    }

    @Override
    public String describe() {
        return "list(size=" + items.size() + ")";
    }
}
