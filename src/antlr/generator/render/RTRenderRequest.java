package antlr.generator.render;

import antlr.runtime.values.RTValue;

import java.util.LinkedHashMap;

/**
 * One captured call to flask's render_template(...) during evaluation.
 * Holds the template name plus the evaluated context (keyword arguments).
 *
 * For dynamic routes (e.g. /product/&lt;int:product_id&gt;) the request is
 * additionally tagged with the concrete URL it was captured for and the raw
 * route pattern, so the generator can emit one page per entity and rewrite
 * links to it.
 */
public class RTRenderRequest {

    private final String templateName;
    private final LinkedHashMap<String, RTValue> context;
    private final int lineNumber;
    /** Concrete URL this render was captured for (e.g. "/product/1"); null when not route-bound. */
    private final String sourceUrl;
    /** Raw route pattern with converter segments (e.g. "/product/<int:product_id>"); null when none. */
    private final String routePattern;

    public RTRenderRequest(String templateName, LinkedHashMap<String, RTValue> context, int lineNumber) {
        this(templateName, context, lineNumber, null, null);
    }

    public RTRenderRequest(String templateName, LinkedHashMap<String, RTValue> context, int lineNumber,
                           String sourceUrl, String routePattern) {
        this.templateName = templateName;
        this.context = context;
        this.lineNumber = lineNumber;
        this.sourceUrl = sourceUrl;
        this.routePattern = routePattern;
    }

    public String getTemplateName() {
        return templateName;
    }

    public LinkedHashMap<String, RTValue> getContext() {
        return context;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getRoutePattern() {
        return routePattern;
    }
}
