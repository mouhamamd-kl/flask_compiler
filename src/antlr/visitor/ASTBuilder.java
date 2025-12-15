package antlr.visitor;

import antlr.pythonParser;
import antlr.pythonParserBaseVisitor;
import antlr.ast.node.ASTNode;
import antlr.ast.python.*;
import antlr.ast.python.expressions.*;
import antlr.symbol.Symbol;
import antlr.symbol.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * باني الشجرة المجردة (AST Builder) يحول Parse Tree إلى AST
 *
 * هذا هو الفرق الجوهري بين المفسر والمترجم: - المفسر: يقيّم القيم مباشرة أثناء
 * الزيارة - المترجم: يبني شجرة من العقد للمعالجة لاحقاً
 */
public class ASTBuilder extends pythonParserBaseVisitor<ASTNode> {

    // جدول الرموز
    private final SymbolTable symbolTable;
    private SymbolTable currentScope;

    public ASTBuilder() {
        this.symbolTable = new SymbolTable();
        this.currentScope = symbolTable;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // ==================== ROOT ====================
    @Override
    public ASTNode visitRoot(pythonParser.RootContext ctx) {
        int line = ctx.getStart() != null ? ctx.getStart().getLine() : 1;
        ProgramNode program = new ProgramNode(line);

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                program.addStatement((StatementNode) stmtNode);
            }
        }

        return program;
    }

    // ==================== STATEMENTS ====================
    @Override
    public ASTNode visitStatement(pythonParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitAssignAction(pythonParser.AssignActionContext ctx) {
        String varName = ctx.var.getText();
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        // زيارة التعبير لبناء عقدته
        ExpressionNode valueNode = (ExpressionNode) visit(ctx.expr());

        // إضافة المتغير إلى جدول الرموز
        Symbol symbol = new Symbol(varName, Symbol.SymbolType.VARIABLE, line, col);
        symbol.setValue(valueNode.toValueString());
        currentScope.define(symbol);

        return new AssignmentNode(varName, valueNode, line, col);
    }

    @Override
    public ASTNode visitPrintAction(pythonParser.PrintActionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode exprNode = (ExpressionNode) visit(ctx.expr());

        return new PrintNode(exprNode, line, col);
    }

    // ==================== IF STATEMENT ====================
    @Override
    public ASTNode visitIfStatement(pythonParser.IfStatementContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        List<pythonParser.ConditionContext> conditions = ctx.condition();
        List<pythonParser.BlockContext> blocks = ctx.block();

        // شرط if الرئيسي
        ExpressionNode mainCondition = (ExpressionNode) visit(conditions.get(0));

        // كتلة if
        currentScope = currentScope.enterScope("if_block_" + line);
        BlockNode ifBlock = (BlockNode) visit(blocks.get(0));
        currentScope = currentScope.exitScope();

        // شروط وكتل elif
        List<ExpressionNode> elifConditions = new ArrayList<>();
        List<BlockNode> elifBlocks = new ArrayList<>();

        int elifCount = conditions.size() - 1;
        for (int i = 0; i < elifCount; i++) {
            ExpressionNode elifCond = (ExpressionNode) visit(conditions.get(i + 1));
            elifConditions.add(elifCond);

            currentScope = currentScope.enterScope("elif_block_" + line + "_" + i);
            BlockNode elifBlock = (BlockNode) visit(blocks.get(i + 1));
            currentScope = currentScope.exitScope();
            elifBlocks.add(elifBlock);
        }

        // كتلة else (إن وجدت)
        BlockNode elseBlock = null;
        if (blocks.size() > conditions.size()) {
            currentScope = currentScope.enterScope("else_block_" + line);
            elseBlock = (BlockNode) visit(blocks.get(blocks.size() - 1));
            currentScope = currentScope.exitScope();
        }

        return new IfStatementNode(mainCondition, ifBlock,
                elifConditions, elifBlocks,
                elseBlock, line, col);
    }

    @Override
    public ASTNode visitBlock(pythonParser.BlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        BlockNode block = new BlockNode(line, col);

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof StatementNode) {
                block.addStatement((StatementNode) stmtNode);
            }
        }

        return block;
    }

    // ==================== CONDITIONS ====================
    @Override
    public ASTNode visitAndCondition(pythonParser.AndConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);

        return new LogicalOpNode(left, LogicalOpNode.Operator.AND, right, line, col);
    }

    @Override
    public ASTNode visitOrCondition(pythonParser.OrConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);

        return new LogicalOpNode(left, LogicalOpNode.Operator.OR, right, line, col);
    }

    @Override
    public ASTNode visitNotCondition(pythonParser.NotConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode operand = (ExpressionNode) visit(ctx.condition());

        return new UnaryOpNode(UnaryOpNode.Operator.NOT, operand, line, col);
    }

    @Override
    public ASTNode visitParenCondition(pythonParser.ParenConditionContext ctx) {
        return visit(ctx.condition());
    }

    @Override
    public ASTNode visitCompareCondition(pythonParser.CompareConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);

        // تحديد نوع عامل المقارنة
        String opStr = ctx.comparisonOp().getText();

        return new ComparisonNode(left, opStr, right, line, col);
    }

    @Override
    public ASTNode visitTrueCondition(pythonParser.TrueConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(true, line, col);
    }

    @Override
    public ASTNode visitFalseCondition(pythonParser.FalseConditionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new BooleanNode(false, line, col);
    }

    // ==================== EXPRESSIONS ====================
    @Override
    public ASTNode visitParenExpr(pythonParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ASTNode visitEXPONENT_OP(pythonParser.EXPONENT_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode base = (ExpressionNode) visit(ctx.base);
        ExpressionNode exp = (ExpressionNode) visit(ctx.exp);

        return new BinaryOpNode(base, BinaryOpNode.Operator.POW, exp, line, col);
    }

    @Override
    public ASTNode visitMUL_DIV_OP(pythonParser.MUL_DIV_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);

        BinaryOpNode.Operator op = ctx.MUL() != null
                ? BinaryOpNode.Operator.MUL : BinaryOpNode.Operator.DIV;

        return new BinaryOpNode(left, op, right, line, col);
    }

    @Override
    public ASTNode visitSUM_SUB_OP(pythonParser.SUM_SUB_OPContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ExpressionNode left = (ExpressionNode) visit(ctx.left);
        ExpressionNode right = (ExpressionNode) visit(ctx.right);

        BinaryOpNode.Operator op = ctx.PLUS() != null
                ? BinaryOpNode.Operator.ADD : BinaryOpNode.Operator.SUB;

        return new BinaryOpNode(left, op, right, line, col);
    }

    @Override
    public ASTNode visitNUM(pythonParser.NUMContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        int value = Integer.parseInt(ctx.NUM().getText());

        return new NumberNode(value, line, col);
    }

    @Override
    public ASTNode visitVAR(pythonParser.VARContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        String name = ctx.NAME().getText();

        return new VariableNode(name, line, col);
    }

    @Override
    public ASTNode visitListExpr(pythonParser.ListExprContext  ctx) {  // ✅ visitLIST
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ListNode listNode = new ListNode(line, col);

        // الوصول للعناصر عبر list()
        for (pythonParser.ExprContext elem : ctx.list().expr()) {
            listNode.addElement((ExpressionNode) visit(elem));  // ✅ addElement + visit
        }

        return listNode;  // ✅ return ListNode not VariableNode
    }
}
