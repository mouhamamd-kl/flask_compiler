package antlr.serve;

import antlr.runtime.values.RTString;
import antlr.runtime.values.RTValue;

import java.util.Map;

/**
 * A live HTTP request, exposed to handlers exactly like Flask's {@code request}
 * object: {@code request.method}, {@code request.path}, {@code request.form[key]}
 * and {@code request.args.get(key)}. It is an RTValue so it can be bound into the
 * scope; attribute / index / method access is resolved by the LiveApp evaluator.
 */
public final class Request extends RTValue {

    private final String method;
    private final String path;
    private final Map<String, RTValue> form;
    private final Map<String, RTValue> args;

    public Request(String method, String path,
                   Map<String, RTValue> form, Map<String, RTValue> args) {
        this.method = method;
        this.path = path;
        this.form = form;
        this.args = args;
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }

    public Map<String, RTValue> form() {
        return form;
    }

    public Map<String, RTValue> args() {
        return args;
    }

    @Override
    public String typeName() {
        return "Request";
    }

    @Override
    public String describe() {
        return "Request(" + method + " " + path + ")";
    }

    @Override
    public String toString() {
        return "<Request " + method + " " + path + ">";
    }
}
