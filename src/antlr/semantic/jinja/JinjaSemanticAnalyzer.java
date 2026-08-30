package antlr.semantic.jinja;

import antlr.ast.jinja2.TemplateNode;
import antlr.ast.jinja2.blocks.BlockDefinitionNode;
import antlr.ast.jinja2.blocks.ForBlockNode;
import antlr.ast.jinja2.blocks.MacroNode;
import antlr.ast.jinja2.blocks.MacroParamNode;
import antlr.ast.jinja2.blocks.WithAssignmentNode;
import antlr.ast.jinja2.blocks.WithBlockNode;
import antlr.ast.jinja2.expressions.VariableExprNode;
import antlr.ast.jinja2.statements.FromImportItemNode;
import antlr.ast.jinja2.statements.FromImportNode;
import antlr.ast.jinja2.statements.ImportNode;
import antlr.ast.jinja2.statements.SetBlockNode;
import antlr.ast.jinja2.statements.SetStatementNode;
import antlr.ast.jinja2.targets.SimpleTargetNode;
import antlr.ast.jinja2.targets.TargetNode;
import antlr.ast.jinja2.targets.TupleTargetNode;
import antlr.ast.node.ASTNode;

import java.util.List;
import java.util.Set;

/**
 * Semantically analyses one Jinja2 template: tracks which variable names the
 * template provides internally and which names it references, then (in a
 * caller-supplied context) reports the referenced-but-never-provided ones.
 *
 * Traversal is template-wide and order-insensitive, mirroring Jinja's lenient
 * "undefined renders as empty" semantics: only a reference that no source can
 * satisfy is flagged (MISSING_TEMPLATE_VARIABLE).
 */
public final class JinjaSemanticAnalyzer {

    private final Set<String> externalProvided;
    private TemplateVariableAnalysis analysis;

    /**
     * Lenient mode: tracks provided/required names but never reports any
     * missing. Used for standalone .html compilation where the supplying
     * render site is unknown.
     */
    public JinjaSemanticAnalyzer() {
        this(null);
    }

    /**
     * @param externalProvided names the render site supplies
     *                         (render_template kwargs + evaluation globals);
     *                         null runs lenient mode (missing = none)
     */
    public JinjaSemanticAnalyzer(Set<String> externalProvided) {
        this.externalProvided = externalProvided;
    }

    public TemplateVariableAnalysis analyze(TemplateNode template) {
        analysis = new TemplateVariableAnalysis();
        if (template != null) {
            walk(template);
        }
        return analysis;
    }

    /**
     * Missing-variable findings for the last analysed template.
     * Empty in lenient mode.
     */
    public List<TemplateVariableAnalysis.MissingTemplateVariable> getMissingVariables() {
        if (analysis == null) {
            return List.of();
        }
        return analysis.computeMissing(externalProvided);
    }

    // ==================== traversal ====================

    private void walk(ASTNode node) {
        if (node == null) {
            return;
        }

        // A bare variable reference is the only node that *needs* a value.
        if (node instanceof VariableExprNode variable) {
            analysis.require(variable.getName(), variable.getLineNumber(), variable.getColumnNumber());
            return;
        }

        // ---- nodes that introduce names ----

        if (node instanceof ForBlockNode block) {
            provideTargets(block.getTarget());
            analysis.provide("loop");
            walk(block.getIterable());
            if (block.hasCondition()) {
                walk(block.getCondition());
            }
            walkAll(block.getBody());
            if (block.hasElseBranch()) {
                walk(block.getElseBranch());
            }
            return;
        }

        if (node instanceof SetStatementNode set) {
            provideTargets(set.getTarget());
            walk(set.getValue());
            walkAll(set.getFilters());
            return;
        }

        if (node instanceof SetBlockNode set) {
            analysis.provide(set.getName());
            walkAll(set.getBody());
            return;
        }

        if (node instanceof WithBlockNode with) {
            walkAll(with.getAssignments());
            walkAll(with.getBody());
            return;
        }

        if (node instanceof WithAssignmentNode assignment) {
            analysis.provide(assignment.getName());
            walk(assignment.getValue());
            return;
        }

        if (node instanceof MacroNode macro) {
            analysis.provide(macro.getName());
            walkAll(macro.getParams());
            walkAll(macro.getBody());
            return;
        }

        if (node instanceof MacroParamNode param) {
            analysis.provide(param.getName());
            if (param.hasDefaultValue()) {
                walk(param.getDefaultValue());
            }
            return;
        }

        if (node instanceof ImportNode importNode) {
            analysis.provide(importNode.getAlias());
            return;
        }

        if (node instanceof FromImportNode fromImport) {
            for (FromImportItemNode item : fromImport.getItems()) {
                analysis.provide(item.hasAlias() ? item.getAlias() : item.getName());
            }
            return;
        }

        if (node instanceof FromImportItemNode item) {
            analysis.provide(item.hasAlias() ? item.getAlias() : item.getName());
            return;
        }

        if (node instanceof BlockDefinitionNode block) {
            analysis.provide(block.getName());
            walkAll(block.getChildren());
            return;
        }

        // Everything else (HTML elements, expression trees, literals,
        // operations, text, CSS...) only contributes its children's references.
        walkAll(node.getChildren());
    }

    private void walkAll(List<? extends ASTNode> nodes) {
        if (nodes == null) {
            return;
        }
        for (ASTNode node : nodes) {
            walk(node);
        }
    }

    private void provideTargets(TargetNode target) {
        if (target instanceof SimpleTargetNode simple) {
            analysis.provide(simple.getTarget());
        } else if (target instanceof TupleTargetNode tuple) {
            for (String name : tuple.getTargets()) {
                analysis.provide(name);
            }
        }
    }
}