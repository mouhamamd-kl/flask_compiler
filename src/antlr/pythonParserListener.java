// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/pythonParser.g4 by ANTLR 4.13.2

   package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pythonParser}.
 */
public interface pythonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pythonParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(pythonParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(pythonParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(pythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(pythonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAssignAction(pythonParser.AssignActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAssignAction(pythonParser.AssignActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 */
	void enterPrintAction(pythonParser.PrintActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 */
	void exitPrintAction(pythonParser.PrintActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(pythonParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(pythonParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterNotCondition(pythonParser.NotConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitNotCondition(pythonParser.NotConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterParenCondition(pythonParser.ParenConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitParenCondition(pythonParser.ParenConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterFalseCondition(pythonParser.FalseConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitFalseCondition(pythonParser.FalseConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompareCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCompareCondition(pythonParser.CompareConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCompareCondition(pythonParser.CompareConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterTrueCondition(pythonParser.TrueConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitTrueCondition(pythonParser.TrueConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterOrCondition(pythonParser.OrConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitOrCondition(pythonParser.OrConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterAndCondition(pythonParser.AndConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitAndCondition(pythonParser.AndConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#comparisonOp}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOp(pythonParser.ComparisonOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#comparisonOp}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOp(pythonParser.ComparisonOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(pythonParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(pythonParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(pythonParser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(pythonParser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void enterDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void exitDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MUL_DIV_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMUL_DIV_OP(pythonParser.MUL_DIV_OPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MUL_DIV_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMUL_DIV_OP(pythonParser.MUL_DIV_OPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VAR}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVAR(pythonParser.VARContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VAR}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVAR(pythonParser.VARContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SUM_SUB_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSUM_SUB_OP(pythonParser.SUM_SUB_OPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SUM_SUB_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSUM_SUB_OP(pythonParser.SUM_SUB_OPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NUM}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNUM(pythonParser.NUMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NUM}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNUM(pythonParser.NUMContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EXPONENT_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEXPONENT_OP(pythonParser.EXPONENT_OPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EXPONENT_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEXPONENT_OP(pythonParser.EXPONENT_OPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DICTIONARY}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDICTIONARY(pythonParser.DICTIONARYContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DICTIONARY}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDICTIONARY(pythonParser.DICTIONARYContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSTRING_LITERAL(pythonParser.STRING_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSTRING_LITERAL(pythonParser.STRING_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(pythonParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(pythonParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(pythonParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(pythonParser.ParenExprContext ctx);
}