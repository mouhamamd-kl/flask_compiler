package antlr.generator.runtime;

import antlr.runtime.values.RTValue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lexically nested variable scopes shared by the python evaluator and the
 * jinja renderer. Lookups walk from the innermost scope outwards.
 */
public final class ScopeStack {

    private final Deque<Map<String, RTValue>> scopes = new ArrayDeque<>();

    public void push() {
        scopes.push(new LinkedHashMap<>());
    }

    /** Pushes a scope seeded with the given bindings (copied). */
    public void push(Map<String, RTValue> initial) {
        scopes.push(new LinkedHashMap<>(initial));
    }

    public void pop() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    /** Drops every scope (used between top-level renders). */
    public void clear() {
        scopes.clear();
    }

    /** Innermost scope map; callers may read or mutate it directly. */
    public Map<String, RTValue> peek() {
        return scopes.peek();
    }

    /** Innermost-first lookup; {@code null} when the name is not defined anywhere. */
    public RTValue resolveOrNull(String name) {
        for (Map<String, RTValue> s : scopes) {
            RTValue v = s.get(name);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    /** Iteration view over all scope maps, innermost first (lookup order). */
    public Iterable<Map<String, RTValue>> innermostFirst() {
        return scopes;
    }
}
