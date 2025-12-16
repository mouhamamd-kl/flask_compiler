package antlr.visitor;

import java.util.List;

import antlr.ast.css.StylesheetNode;
import antlr.ast.html.HtmlDocumentNode;
import antlr.ast.jinja2.TemplateNode;
import antlr.ast.node.ASTNode;
import antlr.ast.python.AssignmentNode;
import antlr.ast.python.BlockNode;
import antlr.ast.python.IfStatementNode;
import antlr.ast.python.PrintNode;
import antlr.ast.python.ProgramNode;
import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.BinaryOpNode;
import antlr.ast.python.expressions.BooleanNode;
import antlr.ast.python.expressions.ComparisonNode;
import antlr.ast.python.expressions.DictEntryNode;
import antlr.ast.python.expressions.DictNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.ListNode;
import antlr.ast.python.expressions.LogicalOpNode;
import antlr.ast.python.expressions.NumberNode;
import antlr.ast.python.expressions.StringNode;
import antlr.ast.python.expressions.UnaryOpNode;
import antlr.ast.python.expressions.VariableNode;
import antlr.ast.visitor.ASTVisitor;

/**
 * طابعة الشجرة المجردة (AST Printer) تطبع الشجرة بشكل مقروء ومنسق
 *
 * المتطلبات المحققة: - طباعة كل عقدة مع معلوماتها - طباعة الأبناء بشكل متداخل -
 * طباعة مقروءة وواضحة
 */
public class ASTPrinter implements ASTVisitor<String> {

    // ==================== ANSI Colors (Custom Palette) ====================
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";

    // Custom palette: 222831, 393E46, 00ADB5, EEEEEE
    public static final String DARK = "\u001B[38;2;8;217;214m";       // 08D9D6 - Bright cyan
    public static final String GRAY = "\u001B[38;2;255;46;99m";      // FF2E63 - Pink/red accent
    public static final String TEAL = "\u001B[38;2;0;173;181m";      // 00ADB5 - Primary accent
    public static final String LIGHT = "\u001B[38;2;238;238;238m";   // EEEEEE - Main text

    private int indentLevel = 0;
    private final String INDENT = "  ";
    private final String BRANCH = GRAY + "├── " + RESET;
    private final String LAST_BRANCH = GRAY + "└── " + RESET;
    private final String VERTICAL = GRAY + "│   " + RESET;
    private final String SPACE = "    ";

    /**
     * طباعة الشجرة
     */
    public void print(ASTNode node) {
        System.out.println("\n" + TEAL + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(TEAL + "║" + RESET + BOLD + LIGHT + "                   Abstract Syntax Tree (AST)                  " + RESET + TEAL + "║" + RESET);
        System.out.println(TEAL + "╚══════════════════════════════════════════════════════════════╝" + RESET + "\n");
        System.out.println(node.accept(this));
    }

    /**
     * إنشاء مسافة بادئة
     */
    private String indent() {
        return INDENT.repeat(indentLevel);
    }

    /**
     * طباعة معلومات العقدة
     */
    private String nodeInfo(ASTNode node, String extra) {
        StringBuilder sb = new StringBuilder();
        sb.append(TEAL).append(BOLD).append(node.getNodeName()).append(RESET);
        sb.append(GRAY).append(" [Line: ").append(node.getLineNumber());
        sb.append(", Col: ").append(node.getColumnNumber()).append("]").append(RESET);
        if (extra != null && !extra.isEmpty()) {
            sb.append(" ").append(LIGHT).append(extra).append(RESET);
        }
        return sb.toString();
    }

    // ==================== Python Nodes ====================
    @Override
    public String visit(ProgramNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(statements: " + node.getStatementCount() + ")")).append("\n");

        indentLevel++;
        var statements = node.getStatements();
        for (int i = 0; i < statements.size(); i++) {
            StatementNode stmt = statements.get(i);
            boolean isLast = (i == statements.size() - 1);
            sb.append(indent()).append(isLast ? LAST_BRANCH : BRANCH);
            sb.append(stmt.accept(this));
        }
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(BlockNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(statements: " + node.getStatementCount() + ")")).append("\n");

        indentLevel++;
        var statements = node.getStatements();
        for (int i = 0; i < statements.size(); i++) {
            StatementNode stmt = statements.get(i);
            boolean isLast = (i == statements.size() - 1);
            sb.append(indent()).append(isLast ? LAST_BRANCH : BRANCH);
            sb.append(stmt.accept(this));
        }
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(AssignmentNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(var: " + node.getVariableName() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Value: ").append(RESET);
        sb.append(node.getValue().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(PrintNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "")).append("\n");

        indentLevel++;
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Expression: ").append(RESET);
        sb.append(node.getExpression().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(IfStatementNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "")).append("\n");

        indentLevel++;

        // Condition
        sb.append(indent()).append(BRANCH).append(TEAL).append("Condition: ").append(RESET);
        sb.append(node.getCondition().accept(this));

        // If block
        sb.append(indent()).append(BRANCH).append(TEAL).append("Then: ").append(RESET);
        sb.append(node.getIfBlock().accept(this));

        // Elif blocks
        var elifConditions = node.getElifConditions();
        var elifBlocks = node.getElifBlocks();
        for (int i = 0; i < elifConditions.size(); i++) {
            sb.append(indent()).append(BRANCH).append(TEAL).append("Elif ").append(i + 1).append(":").append(RESET).append("\n");
            indentLevel++;
            sb.append(indent()).append(BRANCH).append(TEAL).append("Condition: ").append(RESET);
            sb.append(elifConditions.get(i).accept(this));
            sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Block: ").append(RESET);
            sb.append(elifBlocks.get(i).accept(this));
            indentLevel--;
        }

        // Else block
        if (node.hasElse()) {
            sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Else: ").append(RESET);
            sb.append(node.getElseBlock().accept(this));
        }

        indentLevel--;

        return sb.toString();
    }

    // ==================== Expression Nodes ====================
    @Override
    public String visit(BinaryOpNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(BRANCH).append(TEAL).append("Left: ").append(RESET);
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Right: ").append(RESET);
        sb.append(node.getRight().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(UnaryOpNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Operand: ").append(RESET);
        sb.append(node.getOperand().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(NumberNode node) {
        return nodeInfo(node, "(value: " + node.getValue() + ")") + "\n";
    }

    @Override
    public String visit(VariableNode node) {
        return nodeInfo(node, "(name: " + node.getName() + ")") + "\n";
    }

    @Override
    public String visit(BooleanNode node) {
        return nodeInfo(node, "(value: " + node.getValue() + ")") + "\n";
    }

    @Override
    public String visit(ComparisonNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(BRANCH).append(TEAL).append("Left: ").append(RESET);
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Right: ").append(RESET);
        sb.append(node.getRight().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(LogicalOpNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(BRANCH).append(TEAL).append("Left: ").append(RESET);
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append(TEAL).append("Right: ").append(RESET);
        sb.append(node.getRight().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(ListNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(elements: " + node.getElements().size() + ")")).append("\n");

        indentLevel++;
        List<ExpressionNode> elements = node.getElements();
        for (int i = 0; i < elements.size(); i++) {
            boolean isLast = (i == elements.size() - 1);
            sb.append(indent()).append(isLast ? LAST_BRANCH : BRANCH);
            sb.append(elements.get(i).accept(this));
        }
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(StringNode node) {
        return nodeInfo(node, "(value: " + node.getValue() + ")") + "\n";
    }

    @Override
    public String visit(DictEntryNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(TEAL).append("Key: ").append(RESET).append(node.getKey().accept(this));
        sb.append(indent()).append(SPACE).append(TEAL).append("Value: ").append(RESET).append(node.getValue().accept(this));
        return sb.toString();
    }

    @Override
    public String visit(DictNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(elements: " + node.getElements().size() + ")")).append("\n");
        indentLevel++;
        List<DictEntryNode> elements = node.getElements();
        for (int i = 0; i < elements.size(); i++) {
            boolean isLast = (i == elements.size() - 1);
            sb.append(indent()).append(isLast ? LAST_BRANCH : BRANCH);
            sb.append(elements.get(i).accept(this));
        }
        indentLevel--;
        return sb.toString();
    }

    // ==================== Jinja2 Nodes (Placeholders) ====================
    @Override
    public String visit(TemplateNode node) {
        return "TemplateNode [Not implemented yet]\n";
    }

    // @Override
    // public String visit(ExpressionBlockNode node) {
    //     return "ExpressionBlockNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(StatementBlockNode node) {
    //     return "StatementBlockNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(ForLoopNode node) {
    //     return "ForLoopNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(JinjaIfNode node) {
    //     return "JinjaIfNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(IncludeNode node) {
    //     return "IncludeNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(ExtendsNode node) {
    //     return "ExtendsNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(BlockDefinitionNode node) {
    //     return "BlockDefinitionNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(RawTextNode node) {
    //     return "RawTextNode [Not implemented yet]\n";
    // }
    // ==================== HTML Nodes (Placeholders) ====================
    @Override
    public String visit(HtmlDocumentNode node) {
        return "HtmlDocumentNode [Not implemented yet]\n";
    }

    // @Override
    // public String visit(HtmlElementNode node) {
    //     return "HtmlElementNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(HtmlAttributeNode node) {
    //     return "HtmlAttributeNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(HtmlTextNode node) {
    //     return "HtmlTextNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(HtmlCommentNode node) {
    //     return "HtmlCommentNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(DoctypeNode node) {
    //     return "DoctypeNode [Not implemented yet]\n";
    // }
    // ==================== CSS Nodes (Placeholders) ====================
    @Override
    public String visit(StylesheetNode node) {
        return "StylesheetNode [Not implemented yet]\n";
    }

    // @Override
    // public String visit(CssRuleNode node) {
    //     return "CssRuleNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(SelectorNode node) {
    //     return "SelectorNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(DeclarationNode node) {
    //     return "DeclarationNode [Not implemented yet]\n";
    // }
    // @Override
    // public String visit(CssValueNode node) {
    //     return "CssValueNode [Not implemented yet]\n";
    // }
}
