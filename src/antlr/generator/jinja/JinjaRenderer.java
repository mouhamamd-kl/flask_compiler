package antlr.generator.jinja;

import antlr.ast.css.CSSStylesheetNode;
import antlr.ast.jinja2.TemplateNode;
import antlr.ast.jinja2.blocks.ElifBranchNode;
import antlr.ast.jinja2.blocks.ForBlockNode;
import antlr.ast.jinja2.blocks.IfBlockNode;
import antlr.ast.jinja2.content.HtmlAttributeNode;
import antlr.ast.jinja2.content.HtmlElementNode;
import antlr.ast.jinja2.content.HtmlTextNode;
import antlr.ast.jinja2.content.HtmlAttributeValueNode;
import antlr.ast.jinja2.content.NormalHtmlElementNode;
import antlr.ast.jinja2.content.QuoteStyle;
import antlr.ast.jinja2.content.VoidHtmlElementNode;
import antlr.ast.jinja2.expressions.ArgumentNode;
import antlr.ast.jinja2.expressions.DotAccessExprNode;
import antlr.ast.jinja2.expressions.ExpressionBlockNode;
import antlr.ast.jinja2.expressions.FilterNode;
import antlr.ast.jinja2.expressions.FunctionCallExprNode;
import antlr.ast.jinja2.expressions.IndexAccessExprNode;
import antlr.ast.jinja2.expressions.JinjaExpressionNode;
import antlr.ast.jinja2.expressions.ParenExprNode;
import antlr.ast.jinja2.expressions.SliceExprNode;
import antlr.ast.jinja2.expressions.TernaryExprNode;
import antlr.ast.jinja2.expressions.TestExprNode;
import antlr.ast.jinja2.expressions.VariableExprNode;
import antlr.ast.jinja2.expressions.literals.JinjaBooleanNode;
import antlr.ast.jinja2.expressions.literals.JinjaDictEntryNode;
import antlr.ast.jinja2.expressions.literals.JinjaDictNode;
import antlr.ast.jinja2.expressions.literals.JinjaListNode;
import antlr.ast.jinja2.expressions.literals.JinjaNoneNode;
import antlr.ast.jinja2.expressions.literals.JinjaNumberDoubleNode;
import antlr.ast.jinja2.expressions.literals.JinjaNumberIntegerNode;
import antlr.ast.jinja2.expressions.literals.JinjaStringNode;
import antlr.ast.jinja2.expressions.operations.JinjaBinaryOpNode;
import antlr.ast.jinja2.expressions.operations.JinjaComparisonNode;
import antlr.ast.jinja2.expressions.operations.JinjaLogicalOpNode;
import antlr.ast.jinja2.expressions.operations.JinjaUnaryOpNode;
import antlr.ast.jinja2.statements.DoStatementNode;
import antlr.ast.jinja2.statements.IncludeNode;
import antlr.ast.jinja2.statements.SetBlockNode;
import antlr.ast.jinja2.statements.SetStatementNode;
import antlr.ast.jinja2.targets.NamespaceTargetNode;
import antlr.ast.jinja2.targets.SimpleTargetNode;
import antlr.ast.jinja2.targets.TargetNode;
import antlr.ast.jinja2.targets.TupleTargetNode;
import antlr.ast.node.ASTNode;
import antlr.ast.visitor.ASTVisitorAdapter;
import antlr.generator.GenerationLogWriter;
import antlr.generator.runtime.ScopeStack;
import antlr.generator.runtime.ValueOps;
import antlr.runtime.values.RTBool;
import antlr.runtime.values.RTDict;
import antlr.runtime.values.RTFloat;
import antlr.runtime.values.RTInt;
import antlr.runtime.values.RTList;
import antlr.runtime.values.RTNone;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTUndefined;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Renders a parsed Jinja2 TemplateNode into a static HTML string, using the
 * context data produced by the PythonEvaluator.
 */
public class JinjaRenderer extends ASTVisitorAdapter<Object> {

    private final GenerationLogWriter log;
    private final Map<String, TemplateNode> templateRegistry;
    private final ScopeStack scopes = new ScopeStack();
    private final StringBuilder out = new StringBuilder();
    private final Set<String> warnedOnce = new HashSet<>();
    private final FilterRegistry filters = new FilterRegistry(this::warnOnce);

    public JinjaRenderer(GenerationLogWriter log, Map<String, TemplateNode> templateRegistry) {
        this.log = log;
        this.templateRegistry = templateRegistry == null ? Map.of() : templateRegistry;
    }

    public String render(TemplateNode template, String templateName, LinkedHashMap<String, RTValue> context) {
        out.setLength(0);
        scopes.clear();
        scopes.push(new LinkedHashMap<>(context));
        renderChildren(template.getContent());
        log.info("Rendered template '" + templateName + "' (" + out.length() + " characters).");
        return out.toString();
    }

    // ==================== structure ====================
    @Override
    public Object visit(TemplateNode node) {
        renderChildren(node.getContent());
        return null;
    }

    @Override
    public Object visit(HtmlTextNode node) {
        out.append(node.getText());
        return null;
    }

    // ==================== HTML elements ====================
    @Override
    public Object visit(HtmlElementNode node) {
        renderElement(node);
        return null;
    }

    // Specific element classes all delegate to the generic element renderer
    @Override
    public Object visit(antlr.ast.jinja2.content.elements.document.HtmlRootHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.document.HeadHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.document.BodyHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.document.TitleHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.sectioning.DivHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.sectioning.H1HtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.text.ParagraphHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.text.AnchorHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.form.FormHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.form.LabelHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.form.TextareaHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.form.ButtonHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.form.InputHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.media.ImgHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.embedded.StyleHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(antlr.ast.jinja2.content.elements.GenericHtmlNode node) { renderElement(node); return null; }

    @Override
    public Object visit(HtmlAttributeNode node) {
        appendAttribute(node);
        return null;
    }

    private void renderElement(HtmlElementNode element) {
        out.append("<").append(element.getTagName());
        for (HtmlAttributeNode attr : element.getAttributes()) {
            appendAttribute(attr);
        }
        boolean isVoid = element instanceof VoidHtmlElementNode;
        if (isVoid) {
            out.append(">");
            return;
        }
        out.append(">");
        renderChildren(element.getChildNodes());
        out.append("</").append(element.getTagName()).append(">");
    }

    private void appendAttribute(HtmlAttributeNode attr) {
        out.append(" ").append(attr.getNameAsString());
        HtmlAttributeValueNode value = attr.getValue();
        if (value == null || value.getParts().isEmpty()) {
            return; // boolean attribute such as `required`
        }
        StringBuilder rendered = new StringBuilder();
        for (ASTNode part : value.getParts()) {
            if (part instanceof HtmlTextNode text) {
                rendered.append(text.getText());
            } else if (part instanceof ExpressionBlockNode block) {
                RTValue v = applyFilters(evalExpr(block.getExpression()), block.getFilters());
                rendered.append(v.toDisplayString());
            }
        }
        QuoteStyle style = attr.getQuoteStyle();
        if (style == QuoteStyle.UNQUOTED || style == QuoteStyle.NONE) {
            out.append("=").append(rendered);
        } else if (style == QuoteStyle.SINGLE) {
            out.append("='").append(rendered).append("'");
        } else {
            out.append("=\"").append(rendered).append("\"");
        }
    }

    // ==================== Jinja statements ====================
    @Override
    public Object visit(ExpressionBlockNode node) {
        RTValue value = applyFilters(evalExpr(node.getExpression()), node.getFilters());
        out.append(value.toDisplayString());
        return null;
    }

    @Override
    public Object visit(ForBlockNode node) {
        List<String> names = targetNames(node.getTarget());
        List<RTValue> items = iterate(evalExpr(node.getIterable()));
        int emitted = 0;
        pushScope();
        try {
            for (int i = 0; i < items.size(); i++) {
                RTValue item = items.get(i);
                if (node.hasCondition() && !evalExpr(node.getCondition()).isTruthy()) {
                    continue;
                }
                bindTargets(names, item);
                scope().put("loop", loopInfo(i, items.size()));
                renderChildren(node.getBody());
                emitted++;
            }
        } finally {
            popScope();
        }
        if (emitted == 0 && node.hasElseBranch()) {
            renderChildren(node.getElseBranch().getBody());
        }
        return null;
    }

    @Override
    public Object visit(IfBlockNode node) {
        if (evalExpr(node.getCondition()).isTruthy()) {
            renderChildren(node.getBody());
            return null;
        }
        for (ElifBranchNode elif : node.getElifBranches()) {
            if (evalExpr(elif.getCondition()).isTruthy()) {
                renderChildren(elif.getBody());
                return null;
            }
        }
        if (node.hasElseBranch()) {
            renderChildren(node.getElseBranch().getBody());
        }
        return null;
    }

    @Override
    public Object visit(SetStatementNode node) {
        RTValue value = applyFilters(evalExpr(node.getValue()), node.getFilters());
        List<String> names = targetNames(node.getTarget());
        if (names.size() == 1) {
            scope().put(names.get(0), value);
        } else {
            warnOnce("set-multi", "{% set %} with tuple targets is not supported; skipped.");
        }
        return null;
    }

    @Override
    public Object visit(SetBlockNode node) {
        int start = out.length();
        renderChildren(node.getBody());
        scope().put(node.getName(), new RTString(out.substring(start)));
        out.setLength(start);
        return null;
    }

    @Override
    public Object visit(IncludeNode node) {
        String name = node.getIncludedTemplateName();
        TemplateNode included = templateRegistry.get(name);
        if (included == null) {
            log.warn("{% include '" + name + "' %}: template not found in registry; skipped.");
            return null;
        }
        log.info("Inlined include: " + name);
        renderChildren(included.getContent());
        return null;
    }

    @Override
    public Object visit(DoStatementNode node) {
        evalExpr(node.getExpression());
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.blocks.WithBlockNode node) {
        pushScope();
        try {
            for (var assignment : node.getAssignments()) {
                scope().put(assignment.getName(), evalExpr(assignment.getValue()));
            }
            renderChildren(node.getBody());
        } finally {
            popScope();
        }
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.blocks.AutoescapeBlockNode node) {
        log.info("{% autoescape %} blocks are ignored (autoescaping is globally off for generation).");
        renderChildren(node.getBody());
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.blocks.MacroNode node) {
        warnOnce("macro:" + node.getName(), "Macros are outside the supported subset; '{% macro " + node.getName()
                + " %}' was skipped.");
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.blocks.BlockDefinitionNode node) {
        renderChildren(node.getChildren());
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.blocks.FilterBlockNode node) {
        int start = out.length();
        renderChildren(node.getBody());
        RTValue captured = new RTString(out.substring(start));
        out.setLength(start);
        List<JinjaExpressionNode> fakeArgs = List.of();
        out.append(filters.applyNamedFilter(captured, node.getName(), evaluatedArgs(node.getArgs())).toDisplayString());
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.statements.ExtendsNode node) {
        warnOnce("extends", "Template inheritance ({% extends %}) is outside the supported subset; skipped.");
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.statements.ImportNode node) {
        warnOnce("jimport", "{% import %} is outside the supported subset; skipped.");
        return null;
    }

    @Override
    public Object visit(antlr.ast.jinja2.statements.FromImportNode node) {
        warnOnce("jfromimport", "{% from ... import %} is outside the supported subset; skipped.");
        return null;
    }

    // ==================== Jinja expressions ====================
    @Override
    public Object visit(VariableExprNode node) {
        return resolve(node.getName());
    }

    @Override
    public Object visit(DotAccessExprNode node) {
        return getAttr(evalExpr(node.getObject()), node.getProperty());
    }

    @Override
    public Object visit(IndexAccessExprNode node) {
        return getItem(evalExpr(node.getObject()), evalExpr(node.getIndex()));
    }

    @Override
    public Object visit(SliceExprNode node) {
        RTValue container = evalExpr(node.getObject());
        Integer startIdx = node.getStart() == null ? null : asInt(evalExpr(node.getStart()));
        Integer stopIdx = node.getStop() == null ? null : asInt(evalExpr(node.getStop()));
        Integer step = node.getStep() == null ? null : asInt(evalExpr(node.getStep()));

        int from = startIdx == null ? 0 : normalizeSliceBound(startIdx, lengthOf(container), 0);
        int to = stopIdx == null ? lengthOf(container) : normalizeSliceBound(stopIdx, lengthOf(container), 0);
        int stride = step == null || step == 0 ? 1 : step;

        if (container instanceof RTList list) {
            List<RTValue> slice = new ArrayList<>();
            if (stride > 0) {
                for (int i = from; i < Math.min(to, list.size()); i += stride) {
                    slice.add(list.get(i));
                }
            } else {
                for (int i = Math.min(from, list.size() - 1); i >= Math.max(to, 0); i += stride) {
                    if (i >= 0 && i < list.size()) {
                        slice.add(list.get(i));
                    }
                }
            }
            return new RTList(slice);
        }
        if (container instanceof RTString str) {
            StringBuilder sb = new StringBuilder();
            if (stride > 0) {
                for (int i = from; i < Math.min(to, str.value.length()); i += stride) {
                    sb.append(str.value.charAt(i));
                }
            } else {
                for (int i = Math.min(from, str.value.length() - 1); i >= Math.max(to, 0); i += stride) {
                    if (i >= 0 && i < str.value.length()) {
                        sb.append(str.value.charAt(i));
                    }
                }
            }
            return new RTString(sb.toString());
        }
        return RTUndefined.UNDEFINED;
    }

    private int normalizeSliceBound(int idx, int len, int unusedDefault) {
        if (idx < 0) {
            idx += len;
        }
        return Math.max(0, Math.min(idx, len));
    }

    private int lengthOf(RTValue v) {
        Integer n = ValueOps.lengthOrNull(v);
        return n == null ? 0 : n;
    }

    @Override
    public Object visit(ParenExprNode node) {
        return evalExpr(node.getExpression());
    }

    @Override
    public Object visit(TernaryExprNode node) {
        return evalExpr(node.getCondition()).isTruthy()
                ? evalExpr(node.getTrueValue())
                : evalExpr(node.getFalseValue());
    }

    @Override
    public Object visit(TestExprNode node) {
        RTValue subject = evalExpr(node.getExpression());
        String test = node.getTestName();
        switch (test) {
            case "defined":
                return RTBool.of(!(subject instanceof RTUndefined));
            case "undefined":
                return RTBool.of(subject instanceof RTUndefined);
            case "none":
                return RTBool.of(subject instanceof RTNone);
            case "string":
                return RTBool.of(subject instanceof RTString);
            case "number":
                return RTBool.of(subject instanceof RTInt || subject instanceof RTFloat);
            case "iterable":
            case "sequence":
                return RTBool.of(subject instanceof RTList || subject instanceof RTDict || subject instanceof RTString);
            case "odd": {
                Integer n = asInt(subject);
                return RTBool.of(n != null && Math.abs(n) % 2 == 1);
            }
            case "even": {
                Integer n = asInt(subject);
                return RTBool.of(n != null && n % 2 == 0);
            }
            case "divisibleby": {
                List<RTValue> args = evaluatedArgs(node.getArguments());
                Double divisor = args.isEmpty() ? null : asDouble(args.get(0));
                Double value = asDouble(subject);
                return RTBool.of(divisor != null && divisor != 0 && value != null && value % divisor == 0);
            }
            case "equalto":
            case "eq": {
                List<RTValue> args = evaluatedArgs(node.getArguments());
                return RTBool.of(!args.isEmpty() && valuesEqual(subject, args.get(0)));
            }
            default:
                warnOnce("test:" + test, "Unknown test '" + test + "' evaluated to False.");
                return RTBool.of(false);
        }
    }

    @Override
    public Object visit(FunctionCallExprNode node) {
        JinjaExpressionNode callable = node.getCallable();
        String name = callable instanceof VariableExprNode var ? var.getName() : null;
        if (name != null) {
            List<RTValue> args = evaluatedArgs(node.getArguments());
            switch (name) {
                case "range": {
                    List<RTValue> items = new ArrayList<>();
                    int start = 0, stop = 0, step = 1;
                    if (args.size() == 1) {
                        stop = asIntOrZero(args.get(0));
                    } else if (args.size() >= 2) {
                        start = asIntOrZero(args.get(0));
                        stop = asIntOrZero(args.get(1));
                        if (args.size() >= 3) {
                            step = asIntOrZero(args.get(2));
                        }
                    }
                    for (int i = start; step > 0 ? i < stop : i > stop; i += step) {
                        items.add(new RTInt(i));
                    }
                    return new RTList(items);
                }
                case "dict":
                    return new RTDict();
                case "list":
                    return new RTList(iterate(args.isEmpty() ? RTNone.NONE : args.get(0)));
            }
        }
        warnOnce("jinjacall", "Function calls inside templates are limited to range()/dict()/list(); '"
                + (name == null ? "<expression>" : name) + "()' returned None.");
        return RTNone.NONE;
    }

    @Override
    public Object visit(ArgumentNode node) {
        return evalExpr(node.getValue());
    }

    // ==================== Jinja literals ====================
    @Override
    public Object visit(JinjaStringNode node) {
        return new RTString(node.getValue());
    }

    @Override
    public Object visit(JinjaNumberIntegerNode node) {
        return new RTInt(node.getValue());
    }

    @Override
    public Object visit(JinjaNumberDoubleNode node) {
        return new RTFloat(node.getValue());
    }

    @Override
    public Object visit(JinjaBooleanNode node) {
        return RTBool.of(node.getValue());
    }

    @Override
    public Object visit(JinjaNoneNode node) {
        return RTNone.NONE;
    }

    @Override
    public Object visit(JinjaListNode node) {
        List<RTValue> items = new ArrayList<>();
        for (JinjaExpressionNode el : node.getElements()) {
            items.add(evalExpr(el));
        }
        return new RTList(items);
    }

    @Override
    public Object visit(JinjaDictNode node) {
        RTDict dict = new RTDict();
        for (JinjaDictEntryNode entry : node.getElements()) {
            dict.put(evalExpr(entry.getKey()).toDisplayString(), evalExpr(entry.getValue()));
        }
        return dict;
    }

    // ==================== Jinja operations ====================
    @Override
    public Object visit(JinjaBinaryOpNode node) {
        RTValue l = evalExpr(node.getLeft());
        RTValue r = evalExpr(node.getRight());
        return arithBySymbol(node.getOperator().getSymbol(), l, r);
    }

    @Override
    public Object visit(JinjaUnaryOpNode node) {
        RTValue operand = evalExpr(node.getOperand());
        String symbol = node.getOperator().getSymbol();
        if (symbol.contains("not")) {
            return RTBool.of(!operand.isTruthy());
        }
        if (operand instanceof RTInt i) {
            return new RTInt(-i.value);
        }
        if (operand instanceof RTFloat f) {
            return new RTFloat(-f.value);
        }
        return RTNone.NONE;
    }

    @Override
    public Object visit(JinjaComparisonNode node) {
        RTValue l = evalExpr(node.getLeft());
        RTValue r = evalExpr(node.getRight());
        switch (node.getOperator().getSymbol()) {
            case "==":
                return RTBool.of(valuesEqual(l, r));
            case "!=":
                return RTBool.of(!valuesEqual(l, r));
            default: {
                Double a = asDouble(l);
                Double b = asDouble(r);
                int cmp;
                if (a != null && b != null) {
                    cmp = Double.compare(a, b);
                } else if (l instanceof RTString s1 && r instanceof RTString s2) {
                    cmp = s1.value.compareTo(s2.value);
                } else {
                    return RTBool.of(false);
                }
                return switch (node.getOperator().getSymbol()) {
                    case "<" -> RTBool.of(cmp < 0);
                    case ">" -> RTBool.of(cmp > 0);
                    case "<=" -> RTBool.of(cmp <= 0);
                    case ">=" -> RTBool.of(cmp >= 0);
                    default -> RTBool.of(false);
                };
            }
        }
    }

    @Override
    public Object visit(JinjaLogicalOpNode node) {
        RTValue left = evalExpr(node.getLeft());
        if ("and".equals(node.getOperator().getSymbol())) {
            return left.isTruthy() ? evalExpr(node.getRight()) : left;
        }
        return left.isTruthy() ? left : evalExpr(node.getRight());
    }

    // ==================== CSS reconstruction ====================
    @Override
    public Object visit(CSSStylesheetNode node) {
        out.append("\n").append(CssReconstructor.reconstruct(node));
        return null;
    }

    // ==================== evaluation core ====================
    private RTValue evalExpr(JinjaExpressionNode expr) {
        if (expr == null) {
            return RTNone.NONE;
        }
        Object result = expr.accept(this);
        return result instanceof RTValue v ? v : RTNone.NONE;
    }

    private RTValue resolve(String name) {
        RTValue v = scopes.resolveOrNull(name);
        if (v != null) {
            return v;
        }
        warnOnce("undef:" + name,
                "Variable '" + name + "' is undefined in the template context; rendered as empty string.");
        return RTUndefined.UNDEFINED;
    }

    private Map<String, RTValue> scope() {
        return scopes.peek();
    }

    private void pushScope() {
        scopes.push();
    }

    private void popScope() {
        scopes.pop();
    }

    private void renderChildren(List<ASTNode> children) {
        if (children == null) {
            return;
        }
        for (ASTNode child : children) {
            if (child == null) {
                continue;
            }
            if (child instanceof CSSStylesheetNode sheet) {
                out.append("\n").append(CssReconstructor.reconstruct(sheet));
                continue;
            }
            child.accept(this);
        }
    }

    private List<String> targetNames(TargetNode target) {
        if (target instanceof SimpleTargetNode simple) {
            return List.of(simple.getTarget());
        }
        if (target instanceof TupleTargetNode tuple) {
            return tuple.getTargets();
        }
        if (target instanceof NamespaceTargetNode ns) {
            return List.of(ns.getNameSpace());
        }
        return List.of("<target>");
    }

    private void bindTargets(List<String> names, RTValue item) {
        if (names.size() == 1) {
            scope().put(names.get(0), item);
            return;
        }
        if (item instanceof RTList pair && pair.size() >= names.size()) {
            for (int i = 0; i < names.size(); i++) {
                scope().put(names.get(i), pair.get(i));
            }
            return;
        }
        warnOnce("unpack", "Could not unpack loop target; bound whole item to first variable.");
        scope().put(names.get(0), item);
    }

    private RTDict loopInfo(int index, int length) {
        RTDict info = new RTDict();
        info.put("index", new RTInt(index + 1));
        info.put("index0", new RTInt(index));
        info.put("revindex", new RTInt(length - index));
        info.put("revindex0", new RTInt(length - index - 1));
        info.put("first", RTBool.of(index == 0));
        info.put("last", RTBool.of(index == length - 1));
        info.put("length", new RTInt(length));
        return info;
    }

    private List<RTValue> evaluatedArgs(List<? extends JinjaExpressionNode> argNodes) {
        List<RTValue> values = new ArrayList<>();
        if (argNodes != null) {
            for (JinjaExpressionNode arg : argNodes) {
                if (arg instanceof ArgumentNode named) {
                    values.add(evalExpr(named.getValue()));
                } else {
                    values.add(evalExpr(arg));
                }
            }
        }
        return values;
    }

    // ==================== filters ====================
    private RTValue applyFilters(RTValue value, List<FilterNode> filterNodes) {
        if (filterNodes == null) {
            return value;
        }
        for (FilterNode filter : filterNodes) {
            value = filters.applyNamedFilter(value, filter.getName(), evaluatedArgs(filter.getArgs()));
        }
        return value;
    }

    // ==================== shared runtime helpers ====================
    private RTValue getAttr(RTValue obj, String property) {
        if (obj instanceof RTDict dict) {
            RTValue v = dict.get(property);
            return v == null ? RTUndefined.UNDEFINED : v;
        }
        if (obj instanceof RTUndefined || obj instanceof RTNone) {
            return RTUndefined.UNDEFINED;
        }
        warnOnce("tattr:" + property, "Attribute '" + property + "' not available on " + obj.typeName()
                + "; treated as undefined.");
        return RTUndefined.UNDEFINED;
    }

    private RTValue getItem(RTValue container, RTValue key) {
        if (container instanceof RTDict dict) {
            RTValue v = dict.get(key.toDisplayString());
            return v == null ? RTUndefined.UNDEFINED : v;
        }
        if (container instanceof RTList list) {
            Integer index = asInt(key);
            if (index == null) {
                return RTUndefined.UNDEFINED;
            }
            if (index < 0) {
                index += list.size();
            }
            return list.get(index);
        }
        if (container instanceof RTString str) {
            Integer index = asInt(key);
            if (index == null || index < 0 || index >= str.value.length()) {
                return RTUndefined.UNDEFINED;
            }
            return new RTString(String.valueOf(str.value.charAt(index)));
        }
        return RTUndefined.UNDEFINED;
    }

    private RTValue arithBySymbol(String symbol, RTValue l, RTValue r) {
        Double a = asDouble(l);
        Double b = asDouble(r);
        switch (symbol) {
            case "+":
                if (a != null && b != null) {
                    return numberResult(a + b, l instanceof RTInt && r instanceof RTInt);
                }
                if (l instanceof RTString || r instanceof RTString) {
                    return new RTString(l.toDisplayString() + r.toDisplayString());
                }
                if (l instanceof RTList la && r instanceof RTList rb) {
                    List<RTValue> merged = la.snapshot();
                    merged.addAll(rb.snapshot());
                    return new RTList(merged);
                }
                break;
            case "-":
                if (a != null && b != null) {
                    return numberResult(a - b, l instanceof RTInt && r instanceof RTInt);
                }
                break;
            case "*":
                if (a != null && b != null) {
                    return numberResult(a * b, l instanceof RTInt && r instanceof RTInt);
                }
                break;
            case "/":
                if (a != null && b != null) {
                    return b == 0 ? RTNone.NONE : new RTFloat(a / b);
                }
                break;
            case "//":
                if (a != null && b != null && b != 0) {
                    return new RTInt((int) Math.floor(a / b));
                }
                break;
            case "%":
                if (a != null && b != null && b != 0) {
                    return numberResult(a % b, l instanceof RTInt && r instanceof RTInt);
                }
                break;
            case "**":
                if (a != null && b != null) {
                    return numberResult(Math.pow(a, b), false);
                }
                break;
            default:
                warnOnce("jop:" + symbol, "Unsupported operator '" + symbol + "' produced None.");
                return RTNone.NONE;
        }
        warnOnce("jop-type", "Operator '" + symbol + "' on incompatible operands produced None.");
        return RTNone.NONE;
    }

    private RTValue numberResult(double raw, boolean preferInt) {
        if (preferInt && raw == Math.rint(raw)) {
            return new RTInt((int) raw);
        }
        return new RTFloat(raw);
    }

    private boolean valuesEqual(RTValue l, RTValue r) {
        if (l instanceof RTUndefined || r instanceof RTUndefined) {
            return false;
        }
        Double a = asDouble(l);
        Double b = asDouble(r);
        if (a != null && b != null) {
            return a.doubleValue() == b.doubleValue();
        }
        if (l instanceof RTList la && r instanceof RTList rb) {
            if (la.size() != rb.size()) {
                return false;
            }
            for (int i = 0; i < la.size(); i++) {
                if (!valuesEqual(la.get(i), rb.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return l.equals(r) || l == r;
    }

    private List<RTValue> iterate(RTValue value) {
        if (value instanceof RTUndefined) {
            warnOnce("iter-undef", "Attempt to iterate an undefined value produced an empty sequence.");
        }
        return ValueOps.iterate(value);
    }

    private Integer asInt(RTValue v) {
        return ValueOps.coerceInt(v);
    }

    private int asIntOrZero(RTValue v) {
        return ValueOps.coerceIntOrZero(v);
    }

    private Double asDouble(RTValue v) {
        return ValueOps.coerceDouble(v);
    }

    private void warnOnce(String key, String message) {
        if (warnedOnce.add(key)) {
            log.warn(message);
        }
    }
}
