package antlr.generator.render;

/**
 * A fully rendered page awaiting the link-rewrite + write step.
 *
 * @param fileName output file name (e.g. "product_1.html")
 * @param html     rendered HTML body
 * @param routeUrl the concrete URL this page was rendered for
 *                 (e.g. "/product/1"); null when not route-bound
 */
public record RenderedPage(String fileName, String html, String routeUrl) {
}
