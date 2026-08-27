package antlr.runtime.values;

public class RTString extends RTValue {

    public final String value;

    public RTString(String value) {
        this.value = value == null ? "" : value;
    }

    @Override
    public String typeName() {
        return "str";
    }

    @Override
    public boolean isTruthy() {
        return !value.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RTString && ((RTString) obj).value.equals(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String describe() {
        return "str('" + value + "')";
    }

    @Override
    public MethodResult callMethod(String name, java.util.List<RTValue> args) {
        switch (name) {
            case "upper":
                return MethodResult.ok(new RTString(value.toUpperCase()));
            case "lower":
                return MethodResult.ok(new RTString(value.toLowerCase()));
            case "strip":
                return MethodResult.ok(new RTString(value.trim()));
            case "capitalize":
                return MethodResult.ok(new RTString(antlr.generator.runtime.ValueOps.capitalize(value)));
            case "title":
                return MethodResult.ok(new RTString(antlr.generator.runtime.ValueOps.titleCase(value)));
            case "join": {
                if (!args.isEmpty()) {
                    java.util.List<String> parts = new java.util.ArrayList<>();
                    for (RTValue v : antlr.generator.runtime.ValueOps.iterate(args.get(0))) {
                        parts.add(v.toDisplayString());
                    }
                    return MethodResult.ok(new RTString(String.join(value, parts)));
                }
                return MethodResult.ok(new RTString(""));
            }
            default:
                return MethodResult.unsupported();
        }
    }
}
