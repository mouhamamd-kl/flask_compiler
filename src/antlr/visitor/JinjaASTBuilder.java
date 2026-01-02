package antlr.visitor;

import antlr.ast.jinja2.TemplateNode;
import antlr.ast.jinja2.blocks.*;
import antlr.ast.jinja2.content.HtmlTextNode;
import antlr.ast.jinja2.expressions.*;
import antlr.ast.jinja2.expressions.literals.*;
import antlr.ast.jinja2.expressions.operations.JinjaBinaryOpNode;
import antlr.ast.jinja2.expressions.operations.JinjaComparisonNode;
import antlr.ast.jinja2.expressions.operations.JinjaLogicalOpNode;
import antlr.ast.jinja2.expressions.operations.JinjaUnaryOpNode;
import antlr.ast.jinja2.statements.*;
import antlr.ast.jinja2.targets.NamespaceTargetNode;
import antlr.ast.jinja2.targets.SimpleTargetNode;
import antlr.ast.jinja2.targets.TargetNode;
import antlr.ast.jinja2.targets.TupleTargetNode;
import antlr.ast.node.ASTNode;
import antlr.ast.python.ProgramNode;
import antlr.ast.python.StatementNode;
import antlr.ast.python.expressions.ExpressionNode;
import antlr.gen.jinja2.jinja2Lexer;
import antlr.gen.jinja2.jinja2Parser;
import antlr.gen.jinja2.jinja2ParserBaseVisitor;
import antlr.gen.jinja2.jinja2ParserVisitor;
import antlr.gen.python.pythonParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import antlr.ast.jinja2.statements.FromImportItemNode;

public class JinjaASTBuilder extends jinja2ParserBaseVisitor<ASTNode> {

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTemplate(jinja2Parser.TemplateContext ctx) {
        int line = ctx.getStart() != null ? ctx.getStart().getLine() : 1;

        TemplateNode templateNode = new TemplateNode(line);

        for (jinja2Parser.TemplateContentContext stmtCtx : ctx.templateContent()) {
            ASTNode stmtNode = visit(stmtCtx);
            if (stmtNode instanceof ASTNode) {
                templateNode.addContent((ASTNode) stmtNode);
            }
        }

        return templateNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitHtmlTextPart(jinja2Parser.HtmlTextPartContext ctx) {
        StringBuilder text = new StringBuilder();
        // ctx.htmlText() returns HtmlTextContext
        // HtmlTextContext.HTML_TEXT() returns List<TerminalNode>
        for (TerminalNode token : ctx.htmlText().HTML_TEXT()) {
            text.append(token.getText());
        }
        return new HtmlTextNode(text.toString(),
                ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine());
    }

    @Override
    public ASTNode visitExpressionOutput(jinja2Parser.ExpressionOutputContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExpressionBlockNode blockNode = new ExpressionBlockNode(line, col);

        ASTNode expressionNode = visit(ctx.expressionBlock().expr);
        if (expressionNode instanceof JinjaExpressionNode) {
            blockNode.setExpression((JinjaExpressionNode) expressionNode);
        }
        for (jinja2Parser.FilterContext filterContext : ctx.expressionBlock().filter()) {
            ASTNode filterNode = visit(filterContext);
            if (filterNode instanceof FilterNode) {
                blockNode.addFilter((FilterNode) filterNode);
            }
        }

        return blockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitForLoopBlock(jinja2Parser.ForLoopBlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        ForBlockNode forBlockNode = new ForBlockNode(line, col);
        ASTNode targetNode = visit(ctx.forBlock().forStart().target);

        if (targetNode instanceof TargetNode) {
            forBlockNode.setTarget((TargetNode) targetNode);
        }

        ASTNode iterable = visit(ctx.forBlock().forStart().iterable);
        if (iterable instanceof JinjaExpressionNode) {
            forBlockNode.setIterable((JinjaExpressionNode) iterable);
        }

        // condition is optional: (IF condition=expression)?
        if (ctx.forBlock().forStart().condition != null) {
            ASTNode condition = visit(ctx.forBlock().forStart().condition);
            if (condition instanceof JinjaExpressionNode) {
                forBlockNode.setCondition((JinjaExpressionNode) condition);
            }
        }

        for (jinja2Parser.TemplateContentContext ctxx : ctx.forBlock().body) {
            ASTNode bodyElement = visit(ctxx);
            forBlockNode.addToBody(bodyElement);
        }

        // elseForBranch is optional: elseForBranch?
        if (ctx.forBlock().elseForBranch() != null) {
            ASTNode elseBranch = visit(ctx.forBlock().elseForBranch());
            if (elseBranch instanceof ElseBranchNode) {
                forBlockNode.setElseBranch((ElseBranchNode) elseBranch);
            }
        }

        return forBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitIfConditionBlock(jinja2Parser.IfConditionBlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        IfBlockNode ifBlockNode = new IfBlockNode(line, col);

        ASTNode condition = visit(ctx.ifBlock().ifStart().condition);
        if (condition instanceof JinjaExpressionNode) {
            ifBlockNode.setCondition((JinjaExpressionNode) condition);
        }

        for (jinja2Parser.TemplateContentContext ctxx : ctx.ifBlock().body) {
            ASTNode bodyElement = visit(ctxx);
            ifBlockNode.addToBody(bodyElement);
        }


        for (jinja2Parser.ElifBranchContext ctxx : ctx.ifBlock().elifBranch()) {

            ASTNode elifNode = visit(ctxx);
            if (elifNode instanceof ElifBranchNode) {
                ifBlockNode.addElifBranch((ElifBranchNode) elifNode);
            }
        }

        ElseBranchNode elseBranchNode = (ElseBranchNode) visit(ctx.ifBlock().elseBranch());
        if (elseBranchNode != null) {
            ifBlockNode.setElseBranch(elseBranchNode);
        }
        return ifBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitExtendsStmt(jinja2Parser.ExtendsStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ExtendsNode extendsNode = new ExtendsNode(line, col);

        extendsNode.setParentTemplateName(ctx.extendsStatement().templateName.getText());

        return extendsNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitIncludeStmt(jinja2Parser.IncludeStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        IncludeNode includeNode = new IncludeNode(line, col);

        // Get template name (remove quotes from string)
        String templateName = ctx.includeStatement().templateName.getText();
        templateName = templateName.substring(1, templateName.length() - 1);  // Remove quotes
        includeNode.setIncludedTemplateName(templateName);

        // Check ignore missing
        if (ctx.includeStatement().ignoreMissing != null) {
            includeNode.setIgnoreMissing(true);
        }

        // Check context mode
        if (ctx.includeStatement().contextMode != null) {
            if (ctx.includeStatement().contextMode.getType() == jinja2Lexer.WITH) {
                includeNode.setContextMode(ContextMode.WITH_CONTEXT);
            } else {
                includeNode.setContextMode(ContextMode.WITHOUT_CONTEXT);
            }
        }

        return includeNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitBlockDefStmt(jinja2Parser.BlockDefStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        BlockDefinitionNode blockDefinitionNode = new BlockDefinitionNode(line, col);

        blockDefinitionNode.setName(ctx.blockDefinition().name.getText());

        for (jinja2Parser.TemplateContentContext ctxx : ctx.blockDefinition().body) {
            ASTNode bodyElement = visit(ctxx);
            blockDefinitionNode.addChild(bodyElement);
        }

        if (ctx.blockDefinition().scoped != null) {
            blockDefinitionNode.setScoped(true);
        }

        return blockDefinitionNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitSetStmt(jinja2Parser.SetStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        SetStatementNode setStatementNode = new SetStatementNode(line, col);
        ASTNode setTarget = visit(ctx.setStatement().setTarget());
        if (setTarget != null && setTarget instanceof TargetNode) {
            setStatementNode.setTarget((TargetNode) setTarget);
        }

        ASTNode value = visit(ctx.setStatement().value);
        if (value != null && value instanceof JinjaExpressionNode) {
            setStatementNode.setValue((JinjaExpressionNode) value);
        }


        for (jinja2Parser.FilterContext filterContext : ctx.setStatement().filter()) {
            ASTNode filterNode = visit(filterContext);
            if (filterNode instanceof FilterNode) {
                setStatementNode.addFilter((FilterNode) filterNode);
            }
        }

        return setStatementNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitSetBlockStmt(jinja2Parser.SetBlockStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        SetBlockNode setBlockNode = new SetBlockNode(line, col);
        if (ctx.setBlockStatement().name != null) {
            setBlockNode.setName(ctx.setBlockStatement().name.getText());
        }

        for (jinja2Parser.TemplateContentContext ctxx : ctx.setBlockStatement().body) {
            ASTNode bodyElement = visit(ctxx);
            setBlockNode.addToBody(bodyElement);
        }
        return setBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitMacroDefStmt(jinja2Parser.MacroDefStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        MacroNode macroNode = new MacroNode(line, col);
        if (ctx.macroStatement().name != null) {
            macroNode.setName(ctx.macroStatement().name.getText());
        }
        if (ctx.macroStatement().macroParams() != null) {
            for (jinja2Parser.MacroParamContext ctxx : ctx.macroStatement().params.macroParam()) {
                ASTNode macroParamNode = visit(ctxx);
                if (macroParamNode instanceof MacroParamNode) {
                    macroNode.addParam((MacroParamNode) macroParamNode);
                }
            }
        }

        for (jinja2Parser.TemplateContentContext ctxx : ctx.macroStatement().body) {
            ASTNode bodyElement = visit(ctxx);
            macroNode.addToBody(bodyElement);
        }

        return macroNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitImportStmt(jinja2Parser.ImportStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ImportNode importNode = new ImportNode(line, col);

        // Get template name (remove quotes)
        String templateName = ctx.importStatement().templateName.getText();
        templateName = templateName.substring(1, templateName.length() - 1);
        importNode.setTemplateName(templateName);

        // Get alias
        if (ctx.importStatement().alias != null) {
            importNode.setAlias(ctx.importStatement().alias.getText());
        }

        // Check context mode
        if (ctx.importStatement().contextMode != null) {
            if (ctx.importStatement().contextMode.getType() == jinja2Lexer.WITH) {
                importNode.setContextMode(ContextMode.WITH_CONTEXT);
            } else {
                importNode.setContextMode(ContextMode.WITHOUT_CONTEXT);
            }
        }

        return importNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitFromImportStmt(jinja2Parser.FromImportStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        FromImportNode fromImportNode = new FromImportNode(line, col);
        if (ctx.fromImportStatement().templateName != null) {
            fromImportNode.setTemplateName(ctx.fromImportStatement().templateName.getText());
        }

        for (jinja2Parser.ImportItemContext ctxx : ctx.fromImportStatement().imports.importItem()) {
            ASTNode fromImportItemNode = visit(ctxx);
            if (fromImportItemNode instanceof FromImportItemNode) {
                fromImportNode.addItem((FromImportItemNode) fromImportItemNode);
            }
        }
        if (ctx.fromImportStatement().contextMode != null) {
            if (ctx.fromImportStatement().contextMode.getType() == jinja2Lexer.WITH) {
                fromImportNode.setContextMode(ContextMode.WITH_CONTEXT);
            } else {
                fromImportNode.setContextMode(ContextMode.WITHOUT_CONTEXT);
            }
        }
        return fromImportNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitWithScopeBlock(jinja2Parser.WithScopeBlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        WithBlockNode withBlockNode = new WithBlockNode(line, col);

        for (jinja2Parser.TemplateContentContext ctxx : ctx.withBlock().body) {
            ASTNode bodyElement = visit(ctxx);
            withBlockNode.addToBody(bodyElement);
        }

        if (ctx.withBlock().assignments() != null) {
            for (jinja2Parser.WithAssignmentContext ctxx : ctx.withBlock().assignments().withAssignment()) {
                ASTNode assignmentNode = visit(ctxx);
                if (assignmentNode instanceof WithAssignmentNode) {
                    withBlockNode.addAssignment((WithAssignmentNode) assignmentNode);
                }
            }
        }

        return withBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitFilterApplyBlock(jinja2Parser.FilterApplyBlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        FilterBlockNode filterBlockNode = new FilterBlockNode(line, col);
        filterBlockNode.setName(ctx.filterBlock().filterName.getText());

        for (jinja2Parser.TemplateContentContext ctxx : ctx.filterBlock().body) {
            ASTNode bodyElement = visit(ctxx);
            filterBlockNode.addToBody(bodyElement);
        }

        if (ctx.filterBlock().args != null) {
            for (jinja2Parser.ArgumentContext ctxx : ctx.filterBlock().args.argument()) {
                ASTNode argumentNode = visit(ctxx);
                if (argumentNode instanceof ArgumentNode) {
                    filterBlockNode.addArg((ArgumentNode) argumentNode);
                }
            }
        }

        return filterBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitDoStmt(jinja2Parser.DoStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        DoStatementNode doStatementNode = new DoStatementNode(line, col);
        if (ctx.doStatement().expr != null) {
            ASTNode exprNode = visit(ctx.doStatement().expr);
            if (exprNode instanceof JinjaExpressionNode) {
                doStatementNode.setExpression((JinjaExpressionNode) exprNode);
            }
        }

        return doStatementNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitCallMacroBlock(jinja2Parser.CallMacroBlockContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        CallBlockNode callBlockNode = new CallBlockNode(line, col);

        if (!ctx.callBlock().body.isEmpty()) {
            for (jinja2Parser.TemplateContentContext ctxx : ctx.callBlock().body) {
                ASTNode bodyElement = visit(ctxx);
                callBlockNode.addToBody(bodyElement);
            }
        }
        if (ctx.callBlock().callArgs != null && !ctx.callBlock().callArgs.argument().isEmpty()) {
            for (jinja2Parser.ArgumentContext ctxx : ctx.callBlock().callArgs.argument()) {
                ASTNode argumentNode = visit(ctxx);
                if (argumentNode instanceof ArgumentNode) {
                    callBlockNode.addCallArg((ArgumentNode) argumentNode);
                }
            }
        }

        if (ctx.callBlock().callee != null) {
            ASTNode caleeNode = visit(ctx.callBlock().callee);
            if (caleeNode instanceof JinjaExpressionNode) {
                callBlockNode.setCallee((JinjaExpressionNode) caleeNode);
            }
        }

        return callBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAutoescapeStmt(jinja2Parser.AutoescapeStmtContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        AutoescapeBlockNode autoescapeBlockNode = new AutoescapeBlockNode(line, col);

        if (!ctx.autoescapeBlock().body.isEmpty()) {
            for (jinja2Parser.TemplateContentContext ctxx : ctx.autoescapeBlock().body) {
                ASTNode bodyElement = visit(ctxx);
                autoescapeBlockNode.addToBody(bodyElement);
            }
        }

        if (ctx.autoescapeBlock().escapeMode != null) {
            Token escapeModeToken = (Token) ctx.autoescapeBlock().escapeMode;
            int tokenType = escapeModeToken.getType();
            if (tokenType == jinja2Lexer.TRUE) {
                BooleanEscapeModeNode booleanEscapeModeNode = new BooleanEscapeModeNode(line, col);
                booleanEscapeModeNode.setValue(true);
                autoescapeBlockNode.setEscapeMode(booleanEscapeModeNode);
            } else if (tokenType == jinja2Lexer.FALSE) {
                BooleanEscapeModeNode booleanEscapeModeNode = new BooleanEscapeModeNode(line, col);
                booleanEscapeModeNode.setValue(false);
                autoescapeBlockNode.setEscapeMode(booleanEscapeModeNode);
            } else {
                // STRING case
                StringEscapeModeNode stringEscapeModeNode = new StringEscapeModeNode(line, col);
                String rawValue = escapeModeToken.getText();
                // Strip surrounding quotes
                String value = rawValue.substring(1, rawValue.length() - 1);
                stringEscapeModeNode.setValue(value);
                autoescapeBlockNode.setEscapeMode(stringEscapeModeNode);
            }
        }

        return autoescapeBlockNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitFilter(jinja2Parser.FilterContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        FilterNode filterNode = new FilterNode(line, col);
        filterNode.setName(ctx.name.getText());

        if (ctx.argList() != null) {
            for (jinja2Parser.ArgumentContext argCtx : ctx.argList().argument()) {
                ASTNode arg = visit(argCtx);
                if (arg instanceof ArgumentNode) {
                    filterNode.addArg(((ArgumentNode) arg).getValue());
                }
            }
        }
        return filterNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTernaryExpr(jinja2Parser.TernaryExprContext ctx) {
        // If no ternary operator (no IF...ELSE), pass through to the inner expression
        if (ctx.condition == null) {
            return visit(ctx.value);
        }

        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        TernaryExprNode ternaryExprNode = new TernaryExprNode(line, col);

        ASTNode conditionNode = visit(ctx.condition);
        if (conditionNode instanceof JinjaExpressionNode) {
            ternaryExprNode.setCondition((JinjaExpressionNode) conditionNode);
        }

        ASTNode valueNode = visit(ctx.value);
        if (valueNode instanceof JinjaExpressionNode) {
            ternaryExprNode.setTrueValue((JinjaExpressionNode) valueNode);
        }

        ASTNode elseValueNode = visit(ctx.elseValue);
        if (elseValueNode instanceof JinjaExpressionNode) {
            ternaryExprNode.setFalseValue((JinjaExpressionNode) elseValueNode);
        }

        return ternaryExprNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitOrExpr(jinja2Parser.OrExprContext ctx) {
        java.util.List<jinja2Parser.AndExprContext> operands = ctx.andExpr();

        // If only one operand, pass through (no OR operator present)
        if (operands.size() == 1) {
            return visit(operands.get(0));
        }

        // Build chain of OR operations left-to-right
        JinjaExpressionNode result = (JinjaExpressionNode) visit(operands.get(0));
        for (int i = 1; i < operands.size(); i++) {
            jinja2Parser.AndExprContext rightCtx = operands.get(i);
            int line = rightCtx.getStart().getLine();
            int col = rightCtx.getStart().getCharPositionInLine();
            JinjaExpressionNode right = (JinjaExpressionNode) visit(rightCtx);
            result = new JinjaLogicalOpNode(result, JinjaLogicalOpNode.Operator.OR, right, line, col);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAndExpr(jinja2Parser.AndExprContext ctx) {
        java.util.List<jinja2Parser.NotExprContext> operands = ctx.notExpr();

        // If only one operand, pass through (no AND operator present)
        if (operands.size() == 1) {
            return visit(operands.get(0));
        }

        // Build chain of AND operations left-to-right
        JinjaExpressionNode result = (JinjaExpressionNode) visit(operands.get(0));
        for (int i = 1; i < operands.size(); i++) {
            jinja2Parser.NotExprContext rightCtx = operands.get(i);
            int line = rightCtx.getStart().getLine();
            int col = rightCtx.getStart().getCharPositionInLine();
            JinjaExpressionNode right = (JinjaExpressionNode) visit(rightCtx);
            result = new JinjaLogicalOpNode(result, JinjaLogicalOpNode.Operator.AND, right, line, col);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNotExpression(jinja2Parser.NotExpressionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode operand = (JinjaExpressionNode) visit(ctx.operand);
        return new JinjaUnaryOpNode(JinjaUnaryOpNode.Operator.NOT, operand, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNotPassThrough(jinja2Parser.NotPassThroughContext ctx) {
        return visit(ctx.comparisonExpr());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitCompareExpression(jinja2Parser.CompareExpressionContext ctx) {
        if (ctx.compOp() == null) {
            return visit(ctx.left);
        }

        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode left = (JinjaExpressionNode) visit(ctx.left);
        JinjaExpressionNode right = (JinjaExpressionNode) visit(ctx.right);
        JinjaComparisonNode.Operator op = getComparisonOperator(ctx.compOp());
        return new JinjaComparisonNode(left, op, right, line, col);
    }

    private JinjaComparisonNode.Operator getComparisonOperator(jinja2Parser.CompOpContext ctx) {
        if (ctx instanceof jinja2Parser.EqOpContext) return JinjaComparisonNode.Operator.EQ;
        if (ctx instanceof jinja2Parser.NeqOpContext) return JinjaComparisonNode.Operator.NEQ;
        if (ctx instanceof jinja2Parser.LtOpContext) return JinjaComparisonNode.Operator.LT;
        if (ctx instanceof jinja2Parser.GtOpContext) return JinjaComparisonNode.Operator.GT;
        if (ctx instanceof jinja2Parser.LteOpContext) return JinjaComparisonNode.Operator.LTE;
        if (ctx instanceof jinja2Parser.GteOpContext) return JinjaComparisonNode.Operator.GTE;
        if (ctx instanceof jinja2Parser.InOpContext) return JinjaComparisonNode.Operator.IN;
        if (ctx instanceof jinja2Parser.NotInOpContext) return JinjaComparisonNode.Operator.NOT_IN;
        return JinjaComparisonNode.Operator.EQ;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTestExpression(jinja2Parser.TestExpressionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode expression = (JinjaExpressionNode) visit(ctx.additiveExpr());

        jinja2Parser.TestOpContext testOp = ctx.testOp();
        String testName = testOp.testName().NAME().getText();
        boolean negated = testOp.NOT() != null;

        TestExprNode testNode = new TestExprNode(expression, testName, negated, line, col);

        if (testOp.argList() != null) {
            for (jinja2Parser.ArgumentContext argCtx : testOp.argList().argument()) {
                ASTNode arg = visit(argCtx);
                if (arg instanceof ArgumentNode) {
                    testNode.addArgument(((ArgumentNode) arg).getValue());
                }
            }
        }
        return testNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAdditiveExpr(jinja2Parser.AdditiveExprContext ctx) {
        java.util.List<jinja2Parser.MultiplicativeExprContext> exprs = ctx.multiplicativeExpr();
        if (exprs.size() == 1) {
            return visit(exprs.get(0));
        }

        JinjaExpressionNode result = (JinjaExpressionNode) visit(exprs.get(0));
        for (int i = 1; i < exprs.size(); i++) {
            Token opToken = ctx.op.get(i - 1);
            int line = opToken.getLine();
            int col = opToken.getCharPositionInLine();
            JinjaExpressionNode right = (JinjaExpressionNode) visit(exprs.get(i));

            JinjaBinaryOpNode.Operator op = switch (opToken.getType()) {
                case jinja2Lexer.PLUS -> JinjaBinaryOpNode.Operator.ADD;
                case jinja2Lexer.MINUS -> JinjaBinaryOpNode.Operator.SUB;
                case jinja2Lexer.TILDE -> JinjaBinaryOpNode.Operator.CONCAT;
                default -> JinjaBinaryOpNode.Operator.ADD;
            };
            result = new JinjaBinaryOpNode(result, op, right, line, col);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitMultiplicativeExpr(jinja2Parser.MultiplicativeExprContext ctx) {
        java.util.List<jinja2Parser.PowerExprContext> exprs = ctx.powerExpr();
        if (exprs.size() == 1) {
            return visit(exprs.get(0));
        }

        JinjaExpressionNode result = (JinjaExpressionNode) visit(exprs.get(0));
        for (int i = 1; i < exprs.size(); i++) {
            Token opToken = ctx.op.get(i - 1);
            int line = opToken.getLine();
            int col = opToken.getCharPositionInLine();
            JinjaExpressionNode right = (JinjaExpressionNode) visit(exprs.get(i));

            JinjaBinaryOpNode.Operator op = switch (opToken.getType()) {
                case jinja2Lexer.STAR -> JinjaBinaryOpNode.Operator.MUL;
                case jinja2Lexer.SLASH -> JinjaBinaryOpNode.Operator.DIV;
                case jinja2Lexer.FLOORDIV -> JinjaBinaryOpNode.Operator.FLOORDIV;
                case jinja2Lexer.MOD -> JinjaBinaryOpNode.Operator.MOD;
                default -> JinjaBinaryOpNode.Operator.MUL;
            };
            result = new JinjaBinaryOpNode(result, op, right, line, col);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitPowerExpr(jinja2Parser.PowerExprContext ctx) {
        if (ctx.exponent == null) {
            return visit(ctx.base);
        }

        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode base = (JinjaExpressionNode) visit(ctx.base);
        JinjaExpressionNode exponent = (JinjaExpressionNode) visit(ctx.exponent);
        return new JinjaBinaryOpNode(base, JinjaBinaryOpNode.Operator.POW, exponent, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitUnaryExpression(jinja2Parser.UnaryExpressionContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode operand = (JinjaExpressionNode) visit(ctx.operand);

        JinjaUnaryOpNode.Operator op = ctx.op.getType() == jinja2Lexer.MINUS
            ? JinjaUnaryOpNode.Operator.NEGATE
            : JinjaUnaryOpNode.Operator.POSITIVE;

        return new JinjaUnaryOpNode(op, operand, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitUnaryPassThrough(jinja2Parser.UnaryPassThroughContext ctx) {
        return visit(ctx.postfixExpr());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitPostfixExpr(jinja2Parser.PostfixExprContext ctx) {
        JinjaExpressionNode result = (JinjaExpressionNode) visit(ctx.object);

        for (jinja2Parser.PostfixOpContext opCtx : ctx.ops) {
            result = applyPostfixOp(result, opCtx);
        }
        return result;
    }

    private JinjaExpressionNode applyPostfixOp(JinjaExpressionNode object, jinja2Parser.PostfixOpContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();

        if (ctx instanceof jinja2Parser.DotAccessOpContext dotCtx) {
            DotAccessExprNode node = new DotAccessExprNode(line, col);
            node.setObject(object);
            node.setProperty(dotCtx.name.getText());
            return node;
        } else if (ctx instanceof jinja2Parser.IndexAccessOpContext indexCtx) {
            IndexAccessExprNode node = new IndexAccessExprNode(line, col);
            node.setObject(object);
            node.setIndex((JinjaExpressionNode) visit(indexCtx.index));
            return node;
        } else if (ctx instanceof jinja2Parser.SliceAccessOpContext sliceCtx) {
            SliceExprNode node = new SliceExprNode(line, col);
            node.setObject(object);
            if (sliceCtx.start != null) {
                node.setStart((JinjaExpressionNode) visit(sliceCtx.start));
            }
            if (sliceCtx.stop != null) {
                node.setStop((JinjaExpressionNode) visit(sliceCtx.stop));
            }
            if (sliceCtx.step != null) {
                node.setStep((JinjaExpressionNode) visit(sliceCtx.step));
            }
            return node;
        } else if (ctx instanceof jinja2Parser.CallOpContext callCtx) {
            FunctionCallExprNode node = new FunctionCallExprNode(line, col);
            node.setCallable(object);
            if (callCtx.args != null) {
                for (jinja2Parser.ArgumentContext argCtx : callCtx.args.argument()) {
                    ASTNode arg = visit(argCtx);
                    if (arg instanceof ArgumentNode) {
                        node.addArgument((ArgumentNode) arg);
                    }
                }
            }
            return node;
        }
        return object;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNumberLiteral(jinja2Parser.NumberLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String text = ctx.NUMBER().getText();

        if (text.contains(".")) {
            return new JinjaNumberDoubleNode(text, line, col);
        } else {
            return new JinjaNumberIntegerNode(text, line, col);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitStringLiteral(jinja2Parser.StringLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        String text = ctx.STRING().getText();
        String value = text.substring(1, text.length() - 1);
        return new JinjaStringNode(value, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTrueLiteral(jinja2Parser.TrueLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new JinjaBooleanNode(true, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitFalseLiteral(jinja2Parser.FalseLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new JinjaBooleanNode(false, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNoneLiteral(jinja2Parser.NoneLiteralContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        return new JinjaNoneNode(line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitVariableExpr(jinja2Parser.VariableExprContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        VariableExprNode node = new VariableExprNode(line, col);
        node.setName(ctx.NAME().getText());
        return node;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitListExpr(jinja2Parser.ListExprContext ctx) {
        return visit(ctx.list());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitDictExpr(jinja2Parser.DictExprContext ctx) {
        return visit(ctx.dict());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitParenExpr(jinja2Parser.ParenExprContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaExpressionNode inner = (JinjaExpressionNode) visit(ctx.expression());
        return new ParenExprNode(inner, line, col);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitList(jinja2Parser.ListContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaListNode listNode = new JinjaListNode(line, col);

        for (jinja2Parser.ExpressionContext elemCtx : ctx.elements) {
            ASTNode elem = visit(elemCtx);
            if (elem instanceof JinjaExpressionNode) {
                listNode.addElement((JinjaExpressionNode) elem);
            }
        }
        return listNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitDictEntry(jinja2Parser.DictEntryContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaDictEntryNode entryNode = new JinjaDictEntryNode(line, col);

        ASTNode key = visit(ctx.key);
        if (key instanceof JinjaExpressionNode) {
            entryNode.setKey((JinjaExpressionNode) key);
        }

        ASTNode value = visit(ctx.value);
        if (value instanceof JinjaExpressionNode) {
            entryNode.setValue((JinjaExpressionNode) value);
        }

        return entryNode;
    }

    @Override
    public ASTNode visitDict(jinja2Parser.DictContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        JinjaDictNode dictNode = new JinjaDictNode(line, col);

        for (jinja2Parser.DictEntryContext entryCtx : ctx.dictEntry()) {
            ASTNode entry = visit(entryCtx);
            if (entry instanceof JinjaDictEntryNode) {
                dictNode.addElement((JinjaDictEntryNode) entry);
            }
        }
        return dictNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitArgument(jinja2Parser.ArgumentContext ctx) {

        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ArgumentNode argumentNode = new ArgumentNode(line, col);
        if (ctx.name != null) {
            argumentNode.setName(ctx.name.getText());
        }

        if (ctx.value != null) {
            ASTNode value = visit(ctx.value);
            if (value instanceof JinjaExpressionNode) {
                argumentNode.setValue((JinjaExpressionNode) value);
            }
        }

        return argumentNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitSingleTarget(jinja2Parser.SingleTargetContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        SimpleTargetNode targetNode = new SimpleTargetNode(line, col);
        targetNode.setTarget(ctx.NAME().getText());
        return targetNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTupleTarget(jinja2Parser.TupleTargetContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        TupleTargetNode targetNode = new TupleTargetNode(line, col);

        for (TerminalNode name : ctx.NAME()) {
            targetNode.addTarget(name.getText());
        }
        return targetNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitElseForBranch(jinja2Parser.ElseForBranchContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ElseBranchNode elseBranchNode = new ElseBranchNode(line, col);

        for (jinja2Parser.TemplateContentContext bodyCtx : ctx.body) {
            ASTNode bodyElement = visit(bodyCtx);
            elseBranchNode.addToBody(bodyElement);
        }
        return elseBranchNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitElifBranch(jinja2Parser.ElifBranchContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ElifBranchNode elifBranchNode = new ElifBranchNode(line, col);

        ASTNode condition = visit(ctx.condition);
        if (condition instanceof JinjaExpressionNode) {
            elifBranchNode.setCondition((JinjaExpressionNode) condition);
        }
        for (jinja2Parser.TemplateContentContext ctxx : ctx.body) {
            ASTNode bodyElement = visit(ctxx);
            elifBranchNode.addToBody(bodyElement);
        }

        return elifBranchNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitElseBranch(jinja2Parser.ElseBranchContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        ElseBranchNode elseBranchNode = new ElseBranchNode(line, col);
        for (jinja2Parser.TemplateContentContext ctxx : ctx.body) {
            ASTNode bodyElement = visit(ctxx);
            elseBranchNode.addToBody(bodyElement);
        }

        return elseBranchNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitSimpleTarget(jinja2Parser.SimpleTargetContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        SimpleTargetNode simpleTargetNode = new SimpleTargetNode(line, col);
        String target = ctx.name.getText();
        simpleTargetNode.setTarget(target);
        return simpleTargetNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitTupleSetTarget(jinja2Parser.TupleSetTargetContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        TupleTargetNode targetNode = new TupleTargetNode(line, col);
        targetNode.addTarget(ctx.first.getText());
        if (ctx.rest != null) {
            for (Token text : ctx.rest) {
                targetNode.addTarget(text.getText());
            }
        }
        return targetNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitNamespaceTarget(jinja2Parser.NamespaceTargetContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        NamespaceTargetNode namespaceTargetNode = new NamespaceTargetNode(line, col);
        if (ctx.namespace.getText() != null) {
            namespaceTargetNode.setNameSpace(ctx.namespace.getText());
        }
        if (ctx.attributes != null) {
            for (Token text : ctx.attributes) {
                namespaceTargetNode.addAttribute(text.getText());
            }
        }
        return namespaceTargetNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitMacroParam(jinja2Parser.MacroParamContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        MacroParamNode macroParamNode = new MacroParamNode(line, col);
        if (ctx.name != null) {
            macroParamNode.setName(ctx.name.getText());
        }
        if (ctx.defaultValue != null) {
            JinjaExpressionNode expressionNode = (JinjaExpressionNode) visit(ctx.defaultValue);
            macroParamNode.setDefaultValue(expressionNode);
        }
        return macroParamNode;
    }


    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitImportItem(jinja2Parser.ImportItemContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        FromImportItemNode fromImportItemNode = new FromImportItemNode(line, col);
        if (ctx.name != null) {
            fromImportItemNode.setName(ctx.name.getText());

        }
        if (ctx.alias != null) {
            fromImportItemNode.setAlias(ctx.alias.getText());
        }
        return fromImportItemNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitWithAssignment(jinja2Parser.WithAssignmentContext ctx) {
        int line = ctx.getStart().getLine();
        int col = ctx.getStart().getCharPositionInLine();
        WithAssignmentNode withAssignmentNode = new WithAssignmentNode(line, col);
        if (ctx.name != null) {
            withAssignmentNode.setName(ctx.name.getText());
        }
        if (ctx.value != null) {
            ASTNode valueNode = (ASTNode) visit(ctx.value);
            if (valueNode instanceof JinjaExpressionNode) {
                withAssignmentNode.setValue((JinjaExpressionNode) valueNode);
            }
        }
        return withAssignmentNode;
    }


}
