// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/pythonParser.g4 by ANTLR 4.13.2

   package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pythonParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pythonParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(pythonParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pythonParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignAction(pythonParser.AssignActionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintAction}
	 * labeled alternative in {@link pythonParser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintAction(pythonParser.PrintActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(pythonParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotCondition(pythonParser.NotConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenCondition(pythonParser.ParenConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseCondition(pythonParser.FalseConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareCondition(pythonParser.CompareConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueCondition(pythonParser.TrueConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCondition(pythonParser.OrConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndCondition}
	 * labeled alternative in {@link pythonParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCondition(pythonParser.AndConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#comparisonOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOp(pythonParser.ComparisonOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(pythonParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#dict}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDict(pythonParser.DictContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MUL_DIV_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMUL_DIV_OP(pythonParser.MUL_DIV_OPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VAR}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVAR(pythonParser.VARContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SUM_SUB_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSUM_SUB_OP(pythonParser.SUM_SUB_OPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NUM}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNUM(pythonParser.NUMContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EXPONENT_OP}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEXPONENT_OP(pythonParser.EXPONENT_OPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DICTIONARY}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDICTIONARY(pythonParser.DICTIONARYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTRING_LITERAL(pythonParser.STRING_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(pythonParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(pythonParser.ParenExprContext ctx);
}