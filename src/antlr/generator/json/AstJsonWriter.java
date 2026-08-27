package antlr.generator.json;

import antlr.ast.node.ASTNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Serializes AST nodes to pretty-printed JSON without external dependencies.
 *
 * Node shape:
 * {
 *   "name": "Assignment",
 *   "line": 5,
 *   "column": 1,
 *   "value": "...",          // only when the node provides toValueString()
 *   "children": [ ... ]
 * }
 */
public final class AstJsonWriter {

    private static final int MAX_NODES = 50000;
    private static final int MAX_DEPTH = 900;

    private AstJsonWriter() {
    }

    /** Serialize a single root node as a standalone JSON document. */
    public static String toJson(ASTNode root) {
        Map<String, Object> doc = new LinkedHashMap<>();
        doc.put("ast", toTree(root));
        return serialize(doc, 0);
    }

    /** Serialize several templates under a {"templates": {...}} wrapper. */
    public static String templatesToJson(Map<String, ? extends ASTNode> templates) {
        Map<String, Object> inner = new LinkedHashMap<>();
        for (Map.Entry<String, ? extends ASTNode> entry : templates.entrySet()) {
            inner.put(entry.getKey(), toTree(entry.getValue()));
        }
        Map<String, Object> doc = new LinkedHashMap<>();
        doc.put("templates", inner);
        return serialize(doc, 0);
    }

    // ==================== AST -> plain tree ====================
    private static class Budget {
        int remaining = MAX_NODES;
    }

    @SuppressWarnings("unchecked")
    private static Object toTree(ASTNode node) {
        return toTree(node, new Budget(), 0);
    }

    private static Object toTree(ASTNode node, Budget budget, int depth) {
        if (node == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        if (--budget.remaining < 0 || depth > MAX_DEPTH) {
            map.put("name", "<budget or depth limit reached>");
            return map;
        }
        map.put("name", node.getNodeName());
        map.put("line", node.getLineNumber());
        map.put("column", node.getColumnNumber());

        String value = extractValue(node);
        if (value != null && !value.isEmpty()) {
            map.put("value", value);
        }

        List<Object> children = new ArrayList<>();
        try {
            Object rawChildren = node.getChildren();
            if (rawChildren instanceof List) {
                for (Object child : (List<Object>) rawChildren) {
                    if (child instanceof ASTNode childNode) {
                        children.add(toTree(childNode, budget, depth + 1));
                    }
                }
            }
        } catch (Exception ignored) {
            // children unavailable on this node type
        }
        map.put("children", children.isEmpty() ? Collections.emptyList() : children);
        return map;
    }

    /**
     * Nodes may expose a toValueString() summary (literals, assignments, ...).
     * Discovered reflectively so this writer stays generic.
     */
    private static String extractValue(ASTNode node) {
        try {
            java.lang.reflect.Method m = node.getClass().getMethod("toValueString");
            Object result = m.invoke(node);
            return result == null ? null : String.valueOf(result);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // ==================== serialization ====================
    private static String serialize(Object value, int indent) {
        StringBuilder sb = new StringBuilder();
        writeValue(sb, value, indent);
        return sb.toString();
    }

    private static void writeValue(StringBuilder sb, Object value, int indent) {
        if (value == null) {
            sb.append("null");
        } else if (value instanceof Map<?, ?> map) {
            if (map.isEmpty()) {
                sb.append("{}");
                return;
            }
            sb.append("{\n");
            int i = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                indent(sb, indent + 1);
                sb.append(quote(String.valueOf(entry.getKey()))).append(": ");
                writeValue(sb, entry.getValue(), indent + 1);
                if (++i < map.size()) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            indent(sb, indent);
            sb.append("}");
        } else if (value instanceof List<?> list) {
            if (list.isEmpty()) {
                sb.append("[]");
                return;
            }
            sb.append("[\n");
            for (int i = 0; i < list.size(); i++) {
                indent(sb, indent + 1);
                writeValue(sb, list.get(i), indent + 1);
                if (i < list.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            indent(sb, indent);
            sb.append("]");
        } else if (value instanceof String s) {
            sb.append(quote(s));
        } else if (value instanceof Boolean || value instanceof Number) {
            sb.append(value);
        } else {
            sb.append(quote(String.valueOf(value)));
        }
    }

    private static void indent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
    }

    private static String quote(String raw) {
        StringBuilder out = new StringBuilder("\"");
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '"':
                    out.append("\\\"");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
            }
        }
        return out.append("\"").toString();
    }
}
