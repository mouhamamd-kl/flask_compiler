package antlr.serve;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Renders the Vite/Flask-style error overlay. Two presentations share one
 * component and one stylesheet:
 *
 * <ul>
 *   <li>{@link #inject} — floats the overlay over an already-rendered page
 *       (e.g. missing template variables keep the visible page underneath).</li>
 *   <li>{@link #fullPage} — a full-screen dark overlay used when no page can
 *       exist at all (app.py boot failures, template parse errors, handler
 *       crashes).</li>
 * </ul>
 *
 * The emitted HTML is self-contained (style + div + script) and sits safely
 * alongside the live-reload script: it carries its own {@code data-} marker and
 * is always inserted before {@code </body>}.
 */
public final class ErrorOverlay {

    private static final String MARKER = "antlr-error-overlay";

    /** Injects the overlay into an existing HTML page body. */
    public static String inject(String pageHtml, ErrorInfo info, Path projectRoot) {
        String overlay = render(info, projectRoot);
        if (pageHtml.contains("</body>")) {
            return pageHtml.replace("</body>", overlay + "</body>");
        }
        return pageHtml + overlay;
    }

    /** A full-screen overlay page (no renderable content underneath). */
    public static String fullPage(ErrorInfo info, Path projectRoot) {
        return inject("<!DOCTYPE html><html><meta charset=\"utf-8\"><body></body></html>",
                info, projectRoot);
    }

    private static String render(ErrorInfo info, Path projectRoot) {
        StringBuilder sb = new StringBuilder();

        sb.append("<style data-").append(MARKER).append(">")
          .append("body{margin:0}.antlr-ol{position:fixed;inset:0;z-index:99999;overflow:auto;")
          .append("background:rgba(36,36,36,.88);backdrop-filter:blur(2px);")
          .append("font-family:ui-monospace,SFMono-Regular,Menlo,Consolas,monospace;font-size:14px;line-height:1.6;color:#e8e6e3}")
          .append(".antlr-ol-panel{max-width:920px;margin:48px auto;padding:24px;background:#181818;")
          .append("border:1px solid #3a3a3a;border-radius:10px;box-shadow:0 12px 48px rgba(0,0,0,.5)}")
          .append(".antlr-ol-title{color:#ff7b72;font-size:19px;font-weight:700;margin:0 0 4px}")
          .append(".antlr-ol-trace{color:#9da2a8;font-size:13px;margin:8px 0 16px}")
          .append(".antlr-ol-trace b{color:#e6edf3;font-weight:600}")
          .append(".antlr-ol-issue{display:flex;gap:10px;align-items:baseline;padding:6px 0;border-top:1px solid #262626}")
          .append(".antlr-ol-issue:first-of-type{border-top:0}")
          .append(".antlr-ol-kind{flex:none;min-width:88px;text-align:center;padding:1px 8px;font-size:12px;")
          .append("border-radius:4px;color:#0d1117;font-weight:700;text-transform:uppercase;background:#8b949e}")
          .append(".antlr-ol-kind-syntax{background:#ffa657}.antlr-ol-kind-runtime{background:#d2a8ff}")
          .append(".antlr-ol-kind-template{background:#79c0ff}.antlr-ol-kind-abort{background:#ffa198}")
          .append(".antlr-ol-kind-internal{background:#ff7b72}")
          .append(".antlr-ol-body{min-width:0}.antlr-ol-loc{color:#79c0ff;font-size:12px;word-break:break-all}")
          .append(".antlr-ol-msg{color:#e6edf3;word-break:break-word}")
          .append(".antlr-ol-snippet{background:#0d1117;border:1px solid #30363d;border-radius:6px;padding:10px 14px;")
          .append("margin-top:14px;overflow-x:auto;white-space:pre}")
          .append(".antlr-ol-snippet-head{color:#79c0ff;font-size:12px;margin-bottom:6px}")
          .append(".antlr-ol-code{margin:0;min-width:max-content}.antlr-ol-ln{color:#484f58;user-select:none;padding-right:14px}")
          .append(".antlr-ol-caret{color:#ff7b72;font-weight:700}")
          .append(".antlr-ol-tools{display:flex;gap:10px;margin-top:18px}")
          .append(".antlr-ol-btn{cursor:pointer;border:1px solid #3a3a3a;background:#21262d;color:#e6edf3;")
          .append("padding:7px 16px;border-radius:6px;font:inherit}")
          .append(".antlr-ol-btn:hover{background:#30363d}")
          .append(".antlr-ol-btn-primary{border-color:#f85149;background:#f85149;color:#0d1117;font-weight:700}")
          .append(".antlr-ol-btn-primary:hover{background:#ff7b72}")
          .append("</style>");

        sb.append("<div class=\"antlr-ol\" id=\"").append(MARKER).append("-root\">")
          .append("<div class=\"antlr-ol-panel\">")
          .append("<h2 class=\"antlr-ol-title\">").append(esc(info.title())).append("</h2>");

        if (!info.requestTrace().isEmpty()) {
            sb.append("<div class=\"antlr-ol-trace\">");
            List<String> trace = info.requestTrace();
            for (int i = 0; i < trace.size(); i++) {
                String frame = trace.get(i);
                boolean has = frame.contains(" \u2192 ") || frame.startsWith("\u2192 ");
                sb.append(has ? "\u2192 <b>" : "").append(esc(frame)).append(has ? "</b>" : "");
                if (i < trace.size() - 1) {
                    sb.append(" \u00b7 ");
                }
            }
            sb.append("</div>");
        }

        for (ErrorIssue issue : info.issues()) {
            sb.append("<div class=\"antlr-ol-issue\">")
              .append("<span class=\"antlr-ol-kind antlr-ol-kind-").append(esc(kindClass(issue.kind()))).append("\">")
              .append(esc(issue.kind())).append("</span>")
              .append("<span class=\"antlr-ol-body\">");
            if (issue.hasLocation()) {
                sb.append("<div class=\"antlr-ol-loc\">").append(esc(issue.file()))
                  .append(issue.line() > 0 ? ":" + issue.line() : "")
                  .append(issue.column() > 0 ? ":" + issue.column() : "")
                  .append("</div>");
            } else if (issue.file() != null && !issue.file().isEmpty()) {
                sb.append("<div class=\"antlr-ol-loc\">").append(esc(issue.file())).append("</div>");
            }
            sb.append("<div class=\"antlr-ol-msg\">").append(esc(issue.message())).append("</div>")
              .append("</span></div>");
        }

        String snippet = snippet(info, projectRoot);
        if (snippet != null) {
            sb.append(snippet);
        }

        sb.append("<div class=\"antlr-ol-tools\">")
          .append("<button class=\"antlr-ol-btn antlr-ol-btn-primary\" data-ol-reload>Reload</button>")
          .append("<button class=\"antlr-ol-btn\" data-ol-dismiss>Dismiss \u2715</button>")
          .append("</div></div></div>");

        String sig = info.signature();
        sb.append("<script data-").append(MARKER).append(">")
          .append("(function(){var r=document.getElementById('")
          .append(MARKER).append("-root'),s='").append(esc(sig)).append("';")
          .append("if(s&&r&&sessionStorage.getItem('ol.'+s)==='1'){r.style.display='none';}")
          .append("if(r){var b=r.querySelector('[data-ol-reload]'),d=r.querySelector('[data-ol-dismiss]');")
          .append("if(b){b.onclick=function(){location.reload();}}")
          .append("if(d){d.onclick=function(){if(s)sessionStorage.setItem('ol.'+s,'1');r.style.display='none';}}})()")
          .append("</script>");

        return sb.toString();
    }

    private static String kindClass(String kind) {
        if (kind == null) {
            return "internal";
        }
        String lower = kind.toLowerCase();
        if (lower.contains("syntax")) {
            return "syntax";
        }
        if (lower.contains("runtime")) {
            return "runtime";
        }
        if (lower.contains("template")) {
            return "template";
        }
        if (lower.contains("abort")) {
            return "abort";
        }
        return "internal";
    }

    /** Renders the source excerpt (lines around the error) with a caret at column. */
    private static String snippet(ErrorInfo info, Path projectRoot) {
        if (!info.hasSnippet() || projectRoot == null) {
            return null;
        }
        String rel = info.snippetPath();
        if (rel.contains("..")) {
            return null;
        }
        List<String> lines;
        try {
            Path file = projectRoot.resolve(rel);
            if (!Files.isRegularFile(file)) {
                return null;
            }
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
        int errLine = info.snippetLine();
        int errCol = info.snippetColumn();
        int start = Math.max(1, errLine - 3);
        int end = Math.min(lines.size(), errLine + 2);
        int width = String.valueOf(end).length();

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"antlr-ol-snippet\"><div class=\"antlr-ol-snippet-head\">")
          .append(esc(rel)).append(errLine > 0 ? ":" + errLine : "").append(errCol > 0 ? ":" + errCol : "")
          .append("</div><pre class=\"antlr-ol-code\">");
        int caretLen = Math.max(4, 1);
        for (int i = start; i <= end; i++) {
            String src = i <= lines.size() ? lines.get(i - 1) : "";
            sb.append("<span class=\"antlr-ol-ln\">").append(pad(i, width)).append("</span>")
              .append(esc(src)).append("\n");
            if (i == errLine) {
                int col = Math.min(Math.max(errCol - 1, 0), Math.max(0, src.length() - 1));
                sb.append("<span class=\"antlr-ol-ln\">").append(" ".repeat(width)).append("</span>")
                  .append(" ".repeat(col))
                  .append("<span class=\"antlr-ol-caret\">^^^^</span>\n");
            }
        }
        sb.append("</pre></div>");
        return sb.toString();
    }

    private static String pad(int n, int width) {
        String s = String.valueOf(n);
        return " ".repeat(Math.max(0, width - s.length())) + s;
    }

    private static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private ErrorOverlay() {
    }
}