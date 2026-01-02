// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/jinja2Parser.g4 by ANTLR 4.13.2

   package antlr.gen.jinja2;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link jinja2Parser}.
 */
public interface jinja2ParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(jinja2Parser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(jinja2Parser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HtmlTextPart}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterHtmlTextPart(jinja2Parser.HtmlTextPartContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HtmlTextPart}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitHtmlTextPart(jinja2Parser.HtmlTextPartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionOutput}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterExpressionOutput(jinja2Parser.ExpressionOutputContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionOutput}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitExpressionOutput(jinja2Parser.ExpressionOutputContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForLoopBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterForLoopBlock(jinja2Parser.ForLoopBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForLoopBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitForLoopBlock(jinja2Parser.ForLoopBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfConditionBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterIfConditionBlock(jinja2Parser.IfConditionBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfConditionBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitIfConditionBlock(jinja2Parser.IfConditionBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExtendsStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterExtendsStmt(jinja2Parser.ExtendsStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExtendsStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitExtendsStmt(jinja2Parser.ExtendsStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IncludeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterIncludeStmt(jinja2Parser.IncludeStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IncludeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitIncludeStmt(jinja2Parser.IncludeStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterBlockDefStmt(jinja2Parser.BlockDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitBlockDefStmt(jinja2Parser.BlockDefStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterSetStmt(jinja2Parser.SetStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitSetStmt(jinja2Parser.SetStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetBlockStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterSetBlockStmt(jinja2Parser.SetBlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetBlockStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitSetBlockStmt(jinja2Parser.SetBlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MacroDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterMacroDefStmt(jinja2Parser.MacroDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MacroDefStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitMacroDefStmt(jinja2Parser.MacroDefStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(jinja2Parser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(jinja2Parser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FromImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterFromImportStmt(jinja2Parser.FromImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FromImportStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitFromImportStmt(jinja2Parser.FromImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WithScopeBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterWithScopeBlock(jinja2Parser.WithScopeBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WithScopeBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitWithScopeBlock(jinja2Parser.WithScopeBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilterApplyBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterFilterApplyBlock(jinja2Parser.FilterApplyBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilterApplyBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitFilterApplyBlock(jinja2Parser.FilterApplyBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterDoStmt(jinja2Parser.DoStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitDoStmt(jinja2Parser.DoStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallMacroBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterCallMacroBlock(jinja2Parser.CallMacroBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallMacroBlock}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitCallMacroBlock(jinja2Parser.CallMacroBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AutoescapeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void enterAutoescapeStmt(jinja2Parser.AutoescapeStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AutoescapeStmt}
	 * labeled alternative in {@link jinja2Parser#templateContent}.
	 * @param ctx the parse tree
	 */
	void exitAutoescapeStmt(jinja2Parser.AutoescapeStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#htmlText}.
	 * @param ctx the parse tree
	 */
	void enterHtmlText(jinja2Parser.HtmlTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#htmlText}.
	 * @param ctx the parse tree
	 */
	void exitHtmlText(jinja2Parser.HtmlTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#expressionBlock}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBlock(jinja2Parser.ExpressionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#expressionBlock}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBlock(jinja2Parser.ExpressionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilter(jinja2Parser.FilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilter(jinja2Parser.FilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(jinja2Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(jinja2Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#ternaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpr(jinja2Parser.TernaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#ternaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpr(jinja2Parser.TernaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(jinja2Parser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(jinja2Parser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(jinja2Parser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(jinja2Parser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(jinja2Parser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(jinja2Parser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotPassThrough}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotPassThrough(jinja2Parser.NotPassThroughContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotPassThrough}
	 * labeled alternative in {@link jinja2Parser#notExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotPassThrough(jinja2Parser.NotPassThroughContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void enterCompareExpression(jinja2Parser.CompareExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompareExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void exitCompareExpression(jinja2Parser.CompareExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TestExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void enterTestExpression(jinja2Parser.TestExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TestExpression}
	 * labeled alternative in {@link jinja2Parser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void exitTestExpression(jinja2Parser.TestExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterEqOp(jinja2Parser.EqOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitEqOp(jinja2Parser.EqOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NeqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterNeqOp(jinja2Parser.NeqOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NeqOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitNeqOp(jinja2Parser.NeqOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterLtOp(jinja2Parser.LtOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitLtOp(jinja2Parser.LtOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterGtOp(jinja2Parser.GtOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GtOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitGtOp(jinja2Parser.GtOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterLteOp(jinja2Parser.LteOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitLteOp(jinja2Parser.LteOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterGteOp(jinja2Parser.GteOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GteOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitGteOp(jinja2Parser.GteOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterInOp(jinja2Parser.InOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitInOp(jinja2Parser.InOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotInOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterNotInOp(jinja2Parser.NotInOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotInOp}
	 * labeled alternative in {@link jinja2Parser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitNotInOp(jinja2Parser.NotInOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#testOp}.
	 * @param ctx the parse tree
	 */
	void enterTestOp(jinja2Parser.TestOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#testOp}.
	 * @param ctx the parse tree
	 */
	void exitTestOp(jinja2Parser.TestOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#testName}.
	 * @param ctx the parse tree
	 */
	void enterTestName(jinja2Parser.TestNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#testName}.
	 * @param ctx the parse tree
	 */
	void exitTestName(jinja2Parser.TestNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(jinja2Parser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(jinja2Parser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(jinja2Parser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(jinja2Parser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#powerExpr}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpr(jinja2Parser.PowerExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#powerExpr}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpr(jinja2Parser.PowerExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(jinja2Parser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(jinja2Parser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPassThrough}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPassThrough(jinja2Parser.UnaryPassThroughContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPassThrough}
	 * labeled alternative in {@link jinja2Parser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPassThrough(jinja2Parser.UnaryPassThroughContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(jinja2Parser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(jinja2Parser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DotAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterDotAccessOp(jinja2Parser.DotAccessOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DotAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitDotAccessOp(jinja2Parser.DotAccessOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccessOp(jinja2Parser.IndexAccessOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccessOp(jinja2Parser.IndexAccessOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SliceAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterSliceAccessOp(jinja2Parser.SliceAccessOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SliceAccessOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitSliceAccessOp(jinja2Parser.SliceAccessOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterCallOp(jinja2Parser.CallOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallOp}
	 * labeled alternative in {@link jinja2Parser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitCallOp(jinja2Parser.CallOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(jinja2Parser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(jinja2Parser.NumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(jinja2Parser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(jinja2Parser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(jinja2Parser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(jinja2Parser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(jinja2Parser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(jinja2Parser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNoneLiteral(jinja2Parser.NoneLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNoneLiteral(jinja2Parser.NoneLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpr(jinja2Parser.VariableExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpr(jinja2Parser.VariableExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(jinja2Parser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(jinja2Parser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterDictExpr(jinja2Parser.DictExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitDictExpr(jinja2Parser.DictExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(jinja2Parser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link jinja2Parser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(jinja2Parser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(jinja2Parser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(jinja2Parser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(jinja2Parser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(jinja2Parser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void enterDictEntry(jinja2Parser.DictEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void exitDictEntry(jinja2Parser.DictEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(jinja2Parser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(jinja2Parser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(jinja2Parser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(jinja2Parser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#forBlock}.
	 * @param ctx the parse tree
	 */
	void enterForBlock(jinja2Parser.ForBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#forBlock}.
	 * @param ctx the parse tree
	 */
	void exitForBlock(jinja2Parser.ForBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#forStart}.
	 * @param ctx the parse tree
	 */
	void enterForStart(jinja2Parser.ForStartContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#forStart}.
	 * @param ctx the parse tree
	 */
	void exitForStart(jinja2Parser.ForStartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 */
	void enterSingleTarget(jinja2Parser.SingleTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 */
	void exitSingleTarget(jinja2Parser.SingleTargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 */
	void enterTupleTarget(jinja2Parser.TupleTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleTarget}
	 * labeled alternative in {@link jinja2Parser#forTarget}.
	 * @param ctx the parse tree
	 */
	void exitTupleTarget(jinja2Parser.TupleTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#elseForBranch}.
	 * @param ctx the parse tree
	 */
	void enterElseForBranch(jinja2Parser.ElseForBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#elseForBranch}.
	 * @param ctx the parse tree
	 */
	void exitElseForBranch(jinja2Parser.ElseForBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#forEnd}.
	 * @param ctx the parse tree
	 */
	void enterForEnd(jinja2Parser.ForEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#forEnd}.
	 * @param ctx the parse tree
	 */
	void exitForEnd(jinja2Parser.ForEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(jinja2Parser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(jinja2Parser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#ifStart}.
	 * @param ctx the parse tree
	 */
	void enterIfStart(jinja2Parser.IfStartContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#ifStart}.
	 * @param ctx the parse tree
	 */
	void exitIfStart(jinja2Parser.IfStartContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#elifBranch}.
	 * @param ctx the parse tree
	 */
	void enterElifBranch(jinja2Parser.ElifBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#elifBranch}.
	 * @param ctx the parse tree
	 */
	void exitElifBranch(jinja2Parser.ElifBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#elseBranch}.
	 * @param ctx the parse tree
	 */
	void enterElseBranch(jinja2Parser.ElseBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#elseBranch}.
	 * @param ctx the parse tree
	 */
	void exitElseBranch(jinja2Parser.ElseBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#ifEnd}.
	 * @param ctx the parse tree
	 */
	void enterIfEnd(jinja2Parser.IfEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#ifEnd}.
	 * @param ctx the parse tree
	 */
	void exitIfEnd(jinja2Parser.IfEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#extendsStatement}.
	 * @param ctx the parse tree
	 */
	void enterExtendsStatement(jinja2Parser.ExtendsStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#extendsStatement}.
	 * @param ctx the parse tree
	 */
	void exitExtendsStatement(jinja2Parser.ExtendsStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#includeStatement}.
	 * @param ctx the parse tree
	 */
	void enterIncludeStatement(jinja2Parser.IncludeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#includeStatement}.
	 * @param ctx the parse tree
	 */
	void exitIncludeStatement(jinja2Parser.IncludeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#blockDefinition}.
	 * @param ctx the parse tree
	 */
	void enterBlockDefinition(jinja2Parser.BlockDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#blockDefinition}.
	 * @param ctx the parse tree
	 */
	void exitBlockDefinition(jinja2Parser.BlockDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTarget(jinja2Parser.SimpleTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTarget(jinja2Parser.SimpleTargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleSetTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void enterTupleSetTarget(jinja2Parser.TupleSetTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleSetTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void exitTupleSetTarget(jinja2Parser.TupleSetTargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NamespaceTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceTarget(jinja2Parser.NamespaceTargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NamespaceTarget}
	 * labeled alternative in {@link jinja2Parser#setTarget}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceTarget(jinja2Parser.NamespaceTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(jinja2Parser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(jinja2Parser.SetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#setBlockStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetBlockStatement(jinja2Parser.SetBlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#setBlockStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetBlockStatement(jinja2Parser.SetBlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#macroStatement}.
	 * @param ctx the parse tree
	 */
	void enterMacroStatement(jinja2Parser.MacroStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#macroStatement}.
	 * @param ctx the parse tree
	 */
	void exitMacroStatement(jinja2Parser.MacroStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#macroParams}.
	 * @param ctx the parse tree
	 */
	void enterMacroParams(jinja2Parser.MacroParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#macroParams}.
	 * @param ctx the parse tree
	 */
	void exitMacroParams(jinja2Parser.MacroParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#macroParam}.
	 * @param ctx the parse tree
	 */
	void enterMacroParam(jinja2Parser.MacroParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#macroParam}.
	 * @param ctx the parse tree
	 */
	void exitMacroParam(jinja2Parser.MacroParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#callBlock}.
	 * @param ctx the parse tree
	 */
	void enterCallBlock(jinja2Parser.CallBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#callBlock}.
	 * @param ctx the parse tree
	 */
	void exitCallBlock(jinja2Parser.CallBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(jinja2Parser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(jinja2Parser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#fromImportStatement}.
	 * @param ctx the parse tree
	 */
	void enterFromImportStatement(jinja2Parser.FromImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#fromImportStatement}.
	 * @param ctx the parse tree
	 */
	void exitFromImportStatement(jinja2Parser.FromImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(jinja2Parser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(jinja2Parser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#importItem}.
	 * @param ctx the parse tree
	 */
	void enterImportItem(jinja2Parser.ImportItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#importItem}.
	 * @param ctx the parse tree
	 */
	void exitImportItem(jinja2Parser.ImportItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#withBlock}.
	 * @param ctx the parse tree
	 */
	void enterWithBlock(jinja2Parser.WithBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#withBlock}.
	 * @param ctx the parse tree
	 */
	void exitWithBlock(jinja2Parser.WithBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#assignments}.
	 * @param ctx the parse tree
	 */
	void enterAssignments(jinja2Parser.AssignmentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#assignments}.
	 * @param ctx the parse tree
	 */
	void exitAssignments(jinja2Parser.AssignmentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#withAssignment}.
	 * @param ctx the parse tree
	 */
	void enterWithAssignment(jinja2Parser.WithAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#withAssignment}.
	 * @param ctx the parse tree
	 */
	void exitWithAssignment(jinja2Parser.WithAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#filterBlock}.
	 * @param ctx the parse tree
	 */
	void enterFilterBlock(jinja2Parser.FilterBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#filterBlock}.
	 * @param ctx the parse tree
	 */
	void exitFilterBlock(jinja2Parser.FilterBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(jinja2Parser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(jinja2Parser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link jinja2Parser#autoescapeBlock}.
	 * @param ctx the parse tree
	 */
	void enterAutoescapeBlock(jinja2Parser.AutoescapeBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link jinja2Parser#autoescapeBlock}.
	 * @param ctx the parse tree
	 */
	void exitAutoescapeBlock(jinja2Parser.AutoescapeBlockContext ctx);
}