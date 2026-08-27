package antlr.generator.flask;

import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.access.DotAccessNode;
import antlr.ast.python.expressions.access.FunctionCallNode;
import antlr.ast.python.expressions.access.KeywordArgumentNode;
import antlr.ast.python.expressions.literals.StringNode;
import antlr.ast.python.statements.DecoratorNode;
import antlr.ast.python.statements.FunctionDefinitionNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Route-decorator parsing for flask-style programs: extracts the route path
 * from {@code @app.route("...")} decorators and derives concrete candidate
 * values for dynamic routes like {@code /product/<int:product_id>}.
 */
public final class RouteResolver {

    private RouteResolver() {
    }

    public record DynamicRoute(String prefix, String paramName) {
    }

    public static String extractRoutePath(FunctionDefinitionNode node) {
        for (DecoratorNode decorator : node.getDecorators()) {
            ExpressionNode expr = decorator.getExpression();
            if (expr instanceof FunctionCallNode call
                    && call.getCallee() instanceof DotAccessNode dot
                    && "route".equals(dot.getProperty())
                    && !call.getParameters().isEmpty()
                    && unwrapPositionalArg(call.getParameters().get(0)) instanceof StringNode pathArg) {
                return unquote(pathArg.getValue());
            }
        }
        return null;
    }

    /**
     * The AST builder wraps every call argument in a KeywordArgumentNode,
     * using a null name for positional arguments; unwrap those.
     */
    static ExpressionNode unwrapPositionalArg(ExpressionNode arg) {
        if (arg instanceof KeywordArgumentNode kw && kw.getName() == null) {
            return kw.getValue();
        }
        return arg;
    }

    private static String unquote(String s) {
        if (s != null && s.length() >= 2) {
            char first = s.charAt(0);
            char last = s.charAt(s.length() - 1);
            if ((first == '\'' || first == '"') && first == last) {
                return s.substring(1, s.length() - 1);
            }
        }
        return s;
    }

    /**
     * Splits "/product/&lt;int:product_id&gt;" into prefix "/product/" and param name
     * "product_id". Returns null for static routes or unparseable patterns.
     */
    public static DynamicRoute parseDynamicRoute(String routePath) {
        if (routePath == null) {
            return null;
        }
        Matcher m = Pattern.compile("^(.*)<[^<>]*?([^<>/:]*)>$").matcher(routePath);
        if (!m.matches()) {
            return null;
        }
        String paramName = m.group(2);
        if (paramName.isBlank()) {
            return null;
        }
        return new DynamicRoute(m.group(1), paramName);
    }

    /**
     * Finds concrete values for a route parameter by scanning top-level
     * collections in the global scope: lists of dicts whose entries carry the
     * parameter's key (falling back to an "id" key). Order-preserving, deduped.
     */
    public static List<antlr.runtime.values.RTValue> deriveRouteCandidates(
            String paramName, Map<String, antlr.runtime.values.RTValue> globals) {
        List<antlr.runtime.values.RTValue> byParam = new ArrayList<>();
        List<antlr.runtime.values.RTValue> byId = new ArrayList<>();
        Set<String> seenParam = new LinkedHashSet<>();
        Set<String> seenId = new LinkedHashSet<>();
        for (antlr.runtime.values.RTValue global : globals.values()) {
            if (!(global instanceof antlr.runtime.values.RTList list)) {
                continue;
            }
            for (antlr.runtime.values.RTValue item : list.snapshot()) {
                if (!(item instanceof antlr.runtime.values.RTDict dict)) {
                    continue;
                }
                collectCandidate(dict, paramName, byParam, seenParam);
                if (!"id".equals(paramName)) {
                    collectCandidate(dict, "id", byId, seenId);
                }
            }
        }
        if (!byParam.isEmpty()) {
            return byParam;
        }
        return byId;
    }

    private static void collectCandidate(antlr.runtime.values.RTDict dict, String key,
                                         List<antlr.runtime.values.RTValue> out, Set<String> seen) {
        antlr.runtime.values.RTValue value = dict.get(key);
        if (value == null || value instanceof antlr.runtime.values.RTUndefined) {
            return;
        }
        String identity = value.describe();
        if (seen.add(identity)) {
            out.add(value);
        }
    }
}
