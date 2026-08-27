package antlr.generator;

import antlr.symbol.Symbol;
import antlr.symbol.SymbolTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Produces semantic_report.txt: a standalone artifact describing the symbol
 * table and semantic errors. Written even when generation is aborted.
 */
public class SemanticReportWriter {

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String write(SymbolTable globalScope, List<String> semanticErrors) {
        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append("  SEMANTIC ANALYSIS REPORT\n");
        sb.append("  Generated: ").append(LocalDateTime.now().format(TS)).append("\n");
        sb.append("============================================================\n\n");

        if (semanticErrors == null || semanticErrors.isEmpty()) {
            sb.append("[OK] No semantic errors detected.\n");
        } else {
            sb.append("[ERROR] ").append(semanticErrors.size()).append(" semantic error(s) detected:\n");
            for (String error : semanticErrors) {
                sb.append("   - ").append(error).append("\n");
            }
        }
        sb.append("\n");

        if (globalScope != null) {
            appendScope(sb, globalScope);
            int total = countSymbols(globalScope);
            sb.append("\nTotal symbols across all scopes: ").append(total).append("\n");
        } else {
            sb.append("(No symbol table available)\n");
        }

        return sb.toString();
    }

    private static void appendScope(StringBuilder sb, SymbolTable scope) {
        sb.append(repeat("  ", Math.max(0, scope.getScopeLevel() - 1)));
        sb.append("SCOPE '").append(scope.getScopeName()).append("'")
                .append(" [").append(scope.getSymbols().size()).append(" symbols]\n");

        for (Symbol symbol : scope.getSymbols()) {
            sb.append(repeat("  ", scope.getScopeLevel()));
            sb.append("- ").append(symbol.getName())
                    .append(" : ").append(symbol.getType());
            String dataType = symbol.getDataType();
            if (dataType != null && !dataType.isEmpty()) {
                sb.append(" (").append(dataType).append(")");
            }
            Object value = symbol.getValue();
            if (value != null) {
                String rendered = String.valueOf(value);
                if (rendered.length() > 60) {
                    rendered = rendered.substring(0, 57) + "...";
                }
                sb.append(" = ").append(rendered.replace("\n", "\\n"));
            }
            sb.append("  @line ").append(symbol.getLineNumber())
                    .append(":").append(symbol.getColumnNumber());
            sb.append("\n");
        }
        if (!scope.getChildren().isEmpty()) {
            sb.append("\n");
        }
        for (SymbolTable child : scope.getChildren()) {
            appendScope(sb, child);
        }
    }

    private static int countSymbols(SymbolTable scope) {
        int total = scope.getSymbols().size();
        for (SymbolTable child : scope.getChildren()) {
            total += countSymbols(child);
        }
        return total;
    }

    private static String repeat(String s, int times) {
        return s.repeat(Math.max(0, times));
    }
}
