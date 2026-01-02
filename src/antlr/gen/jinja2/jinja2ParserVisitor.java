// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/jinja2Parser.g4 by ANTLR 4.13.2

   package antlr.gen.jinja2;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link jinja2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface jinja2ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#template}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(jinja2Parser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HtmlTextPart}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlTextPart(jinja2Parser.HtmlTextPartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionOutput}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionOutput(jinja2Parser.ExpressionOutputContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForLoopBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoopBlock(jinja2Parser.ForLoopBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfConditionBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfConditionBlock(jinja2Parser.IfConditionBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExtendsStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtendsStmt(jinja2Parser.ExtendsStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IncludeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludeStmt(jinja2Parser.IncludeStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockDefStmt(jinja2Parser.BlockDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStmt(jinja2Parser.SetStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBlockStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBlockStmt(jinja2Parser.SetBlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MacroDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroDefStmt(jinja2Parser.MacroDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStmt(jinja2Parser.ImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FromImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromImportStmt(jinja2Parser.FromImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WithScopeBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithScopeBlock(jinja2Parser.WithScopeBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilterApplyBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterApplyBlock(jinja2Parser.FilterApplyBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStmt(jinja2Parser.DoStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallMacroBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallMacroBlock(jinja2Parser.CallMacroBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AutoescapeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutoescapeStmt(jinja2Parser.AutoescapeStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#htmlText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlText(jinja2Parser.HtmlTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#expressionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionBlock(jinja2Parser.ExpressionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilter(jinja2Parser.FilterContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(jinja2Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#ternaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpr(jinja2Parser.TernaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(jinja2Parser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(jinja2Parser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(jinja2Parser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotPassThrough}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotPassThrough(jinja2Parser.NotPassThroughContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareExpression(jinja2Parser.CompareExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TestExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestExpression(jinja2Parser.TestExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqOp(jinja2Parser.EqOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NeqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeqOp(jinja2Parser.NeqOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtOp(jinja2Parser.LtOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtOp(jinja2Parser.GtOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLteOp(jinja2Parser.LteOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGteOp(jinja2Parser.GteOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInOp(jinja2Parser.InOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotInOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotInOp(jinja2Parser.NotInOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#testOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestOp(jinja2Parser.TestOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#testName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestName(jinja2Parser.TestNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(jinja2Parser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(jinja2Parser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#powerExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpr(jinja2Parser.PowerExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(jinja2Parser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPassThrough}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPassThrough(jinja2Parser.UnaryPassThroughContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(jinja2Parser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DotAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotAccessOp(jinja2Parser.DotAccessOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAccessOp(jinja2Parser.IndexAccessOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SliceAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSliceAccessOp(jinja2Parser.SliceAccessOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallOp(jinja2Parser.CallOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(jinja2Parser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(jinja2Parser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(jinja2Parser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(jinja2Parser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoneLiteral(jinja2Parser.NoneLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpr(jinja2Parser.VariableExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(jinja2Parser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DictExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictExpr(jinja2Parser.DictExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(jinja2Parser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(jinja2Parser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#dict}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDict(jinja2Parser.DictContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#dictEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictEntry(jinja2Parser.DictEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(jinja2Parser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(jinja2Parser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#forBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForBlock(jinja2Parser.ForBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#forStart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStart(jinja2Parser.ForStartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SingleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleTarget(jinja2Parser.SingleTargetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleTarget(jinja2Parser.TupleTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#elseForBranch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseForBranch(jinja2Parser.ElseForBranchContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#forEnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForEnd(jinja2Parser.ForEndContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(jinja2Parser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#ifStart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStart(jinja2Parser.IfStartContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#elifBranch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElifBranch(jinja2Parser.ElifBranchContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#elseBranch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBranch(jinja2Parser.ElseBranchContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#ifEnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfEnd(jinja2Parser.IfEndContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#extendsStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtendsStatement(jinja2Parser.ExtendsStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#includeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludeStatement(jinja2Parser.IncludeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#blockDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockDefinition(jinja2Parser.BlockDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTarget(jinja2Parser.SimpleTargetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleSetTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleSetTarget(jinja2Parser.TupleSetTargetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NamespaceTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceTarget(jinja2Parser.NamespaceTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(jinja2Parser.SetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#setBlockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBlockStatement(jinja2Parser.SetBlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#macroStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroStatement(jinja2Parser.MacroStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#macroParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroParams(jinja2Parser.MacroParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#macroParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroParam(jinja2Parser.MacroParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#callBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallBlock(jinja2Parser.CallBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(jinja2Parser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#fromImportStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromImportStatement(jinja2Parser.FromImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(jinja2Parser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#importItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportItem(jinja2Parser.ImportItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#withBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithBlock(jinja2Parser.WithBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#assignments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignments(jinja2Parser.AssignmentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#withAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithAssignment(jinja2Parser.WithAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#filterBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterBlock(jinja2Parser.FilterBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(jinja2Parser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link jinja2Parser#autoescapeBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAutoescapeBlock(jinja2Parser.AutoescapeBlockContext ctx);
}