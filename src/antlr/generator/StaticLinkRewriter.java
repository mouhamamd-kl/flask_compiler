package antlr.generator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rewrites captured Flask URLs (e.g. href="/product/1", href="/") in
 * generated static HTML to relative links between the generated pages, so
 * navigation works when the output folder is opened directly.
 *
 * Only exact attribute-value matches against known page URLs are rewritten;
 * anything else (external links, anchors, CSS text) is left untouched.
 */
final class StaticLinkRewriter {

    private StaticLinkRewriter() {
    }

    /**
     * @param html      rendered page content
     * @param urlToPage map of concrete URL -> output file name (e.g. "/product/1" ->
     *                  "product_1.html", "/" -> "products.html")
     * @return html with (href|src) attributes rewritten where a mapping exists
     */
    static String rewrite(String html, Map<String, String> urlToPage) {
        if (html == null || html.isEmpty() || urlToPage.isEmpty()) {
            return html;
        }
        StringBuffer out = new StringBuffer();
        Matcher m = ATTRIBUTE_URL.matcher(html);
        while (m.find()) {
            String attr = m.group(1);
            String quote = m.group(2);
            String url = m.group(3);
            String replacement = urlToPage.get(stripFragment(url));
            String rewritten = replacement != null ? replacement : url;
            m.appendReplacement(out, Matcher.quoteReplacement(
                    attr + "=" + quote + rewritten + quote));
        }
        m.appendTail(out);
        return out.toString();
    }

    private static final Pattern ATTRIBUTE_URL = Pattern.compile(
            "(href|src)=([\"'])(.*?)\\2");

    private static final Pattern FRAGMENT = Pattern.compile("[#?].*$");

    private static String stripFragment(String url) {
        return FRAGMENT.matcher(url).replaceFirst("");
    }
}
