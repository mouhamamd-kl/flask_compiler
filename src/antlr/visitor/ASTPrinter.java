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
import antlr.ast.python.expressions.ExpressionNode;
import antlr.ast.python.expressions.ListNode;
import antlr.ast.python.expressions.LogicalOpNode;
import antlr.ast.python.expressions.NumberNode;
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

    private int indentLevel = 0;
    private final String INDENT = "  ";
    private final String BRANCH = "├── ";
    private final String LAST_BRANCH = "└── ";
    private final String VERTICAL = "│   ";
    private final String SPACE = "    ";

    /**
     * طباعة الشجرة
     */
    public void print(ASTNode node) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   Abstract Syntax Tree (AST)                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
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
        sb.append(node.getNodeName());
        sb.append(" [Line: ").append(node.getLineNumber());
        sb.append(", Col: ").append(node.getColumnNumber()).append("]");
        if (extra != null && !extra.isEmpty()) {
            sb.append(" ").append(extra);
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
        sb.append(indent()).append(LAST_BRANCH).append("Value: ");
        sb.append(node.getValue().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(PrintNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "")).append("\n");

        indentLevel++;
        sb.append(indent()).append(LAST_BRANCH).append("Expression: ");
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
        sb.append(indent()).append(BRANCH).append("Condition: ");
        sb.append(node.getCondition().accept(this));

        // If block
        sb.append(indent()).append(BRANCH).append("Then: ");
        sb.append(node.getIfBlock().accept(this));

        // Elif blocks
        var elifConditions = node.getElifConditions();
        var elifBlocks = node.getElifBlocks();
        for (int i = 0; i < elifConditions.size(); i++) {
            sb.append(indent()).append(BRANCH).append("Elif ").append(i + 1).append(":\n");
            indentLevel++;
            sb.append(indent()).append(BRANCH).append("Condition: ");
            sb.append(elifConditions.get(i).accept(this));
            sb.append(indent()).append(LAST_BRANCH).append("Block: ");
            sb.append(elifBlocks.get(i).accept(this));
            indentLevel--;
        }

        // Else block
        if (node.hasElse()) {
            sb.append(indent()).append(LAST_BRANCH).append("Else: ");
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
        sb.append(indent()).append(BRANCH).append("Left: ");
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append("Right: ");
        sb.append(node.getRight().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(UnaryOpNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(LAST_BRANCH).append("Operand: ");
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
        sb.append(indent()).append(BRANCH).append("Left: ");
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append("Right: ");
        sb.append(node.getRight().accept(this));
        indentLevel--;

        return sb.toString();
    }

    @Override
    public String visit(LogicalOpNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeInfo(node, "(op: " + node.getOperator().getSymbol() + ")")).append("\n");

        indentLevel++;
        sb.append(indent()).append(BRANCH).append("Left: ");
        sb.append(node.getLeft().accept(this));
        sb.append(indent()).append(LAST_BRANCH).append("Right: ");
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
