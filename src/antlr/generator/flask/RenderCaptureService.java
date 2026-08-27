package antlr.generator.flask;

import antlr.generator.runtime.EvaluatorContext;
import antlr.runtime.values.RTNone;
import antlr.generator.render.RTRenderRequest;
import antlr.runtime.values.RTValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Collects every render_template(...) capture produced during evaluation,
 * tagging each request with the route it was captured under (when any).
 */
public final class RenderCaptureService {

    /** Route info attached to the next captured render request; null outside route invocations. */
    public record PendingRouteInfo(String sourceUrl, String routePattern) {
    }

    private final List<RTRenderRequest> requests = new ArrayList<>();
    private PendingRouteInfo pendingRouteInfo;
    private final EvaluatorContext ctx;

    public RenderCaptureService(EvaluatorContext ctx) {
        this.ctx = ctx;
    }

    /** Tags the next capture with a route (source URL + pattern). */
    public void tagNextCapture(PendingRouteInfo info) {
        this.pendingRouteInfo = info;
    }

    public RTValue captureRequest(String templateName, LinkedHashMap<String, RTValue> context, int line) {
        PendingRouteInfo route = pendingRouteInfo;
        RTRenderRequest request = route != null
                ? new RTRenderRequest(templateName, context, line, route.sourceUrl(), route.routePattern())
                : new RTRenderRequest(templateName, context, line);
        requests.add(request);
        ctx.log().info("Captured render_template('" + templateName + "')"
                + (route != null ? " for URL " + route.sourceUrl() : "") + " at line " + line
                + " with context keys " + context.keySet() + ".");
        return RTNone.NONE;
    }

    public List<RTRenderRequest> requests() {
        return requests;
    }
}
