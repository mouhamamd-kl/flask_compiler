package antlr.runtime.values;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Python dict restricted to string keys (sufficient for the project scope:
 * JSON-like data structures passed to templates).
 */
public class RTDict extends RTValue {

    private final LinkedHashMap<String, RTValue> entries = new LinkedHashMap<>();

    public void put(String key, RTValue value) {
        entries.put(key, value);
    }

    public RTValue get(String key) {
        return entries.get(key);
    }

    public boolean hasKey(String key) {
        return entries.containsKey(key);
    }

    public List<String> keys() {
        return new java.util.ArrayList<>(entries.keySet());
    }

    public int size() {
        return entries.size();
    }

    @Override
    public MethodResult callMethod(String name, java.util.List<RTValue> args) {
        switch (name) {
            case "get": {
                if (args.isEmpty()) {
                    return MethodResult.ok(RTNone.NONE);
                }
                RTValue v = get(args.get(0).toDisplayString());
                if (v != null) {
                    return MethodResult.ok(v);
                }
                return MethodResult.ok(args.size() >= 2 ? args.get(1) : RTNone.NONE);
            }
            case "keys": {
                List<RTValue> out = new java.util.ArrayList<>();
                for (String key : keys()) {
                    out.add(new RTString(key));
                }
                return MethodResult.ok(new RTList(out));
            }
            case "values":
                return MethodResult.ok(new RTList(new java.util.ArrayList<>(entries.values())));
            case "items": {
                List<RTValue> pairs = new java.util.ArrayList<>();
                for (java.util.Map.Entry<String, RTValue> e : entries.entrySet()) {
                    pairs.add(new RTList(java.util.List.of(new RTString(e.getKey()), e.getValue())));
                }
                return MethodResult.ok(new RTList(pairs));
            }
            case "pop":
                if (!args.isEmpty()) {
                    RTValue v = get(args.get(0).toDisplayString());
                    entries.remove(args.get(0).toDisplayString());
                    return MethodResult.ok(v == null ? (args.size() >= 2 ? args.get(1) : RTNone.NONE) : v);
                }
                return MethodResult.ok(RTNone.NONE);
            case "update":
                if (!args.isEmpty() && args.get(0) instanceof RTDict other) {
                    entries.putAll(other.entries);
                }
                return MethodResult.ok(RTNone.NONE);
            default:
                return MethodResult.unsupported();
        }
    }

    @Override
    public String typeName() {
        return "dict";
    }

    @Override
    public boolean isTruthy() {
        return !entries.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RTDict)) {
            return false;
        }
        RTDict other = (RTDict) obj;
        if (entries.size() != other.entries.size()) {
            return false;
        }
        for (String key : entries.keySet()) {
            if (!other.hasKey(key) || !entries.get(key).equals(other.get(key))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (String key : entries.keySet()) {
            if (!first) sb.append(", ");
            first = false;
            sb.append("'").append(key).append("': ").append(entries.get(key));
        }
        return sb.append("}").toString();
    }

    @Override
    public String describe() {
        return "dict(size=" + entries.size() + ")";
    }
}
