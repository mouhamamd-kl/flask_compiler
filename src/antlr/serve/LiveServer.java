package antlr.serve;

import antlr.generator.GenerationLogWriter;
import antlr.runtime.values.RTString;
import antlr.runtime.values.RTValue;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A small JDK-built-in HTTP server (com.sun.net.httpserver) that serves the
 * live Flask runtime. Requests are handled sequentially on a single worker so
 * in-memory app state (e.g. `products`) keeps its order, exactly like a dev
 * server.
 */
public class LiveServer {

    private static final Map<String, String> CONTENT_TYPES = Map.ofEntries(
            Map.entry("html", "text/html; charset=utf-8"),
            Map.entry("htm", "text/html; charset=utf-8"),
            Map.entry("css", "text/css; charset=utf-8"),
            Map.entry("js", "application/javascript; charset=utf-8"),
            Map.entry("json", "application/json"),
            Map.entry("png", "image/png"),
            Map.entry("jpg", "image/jpeg"),
            Map.entry("jpeg", "image/jpeg"),
            Map.entry("gif", "image/gif"),
            Map.entry("svg", "image/svg+xml"),
            Map.entry("ico", "image/x-icon"),
            Map.entry("txt", "text/plain; charset=utf-8"),
            Map.entry("woff", "font/woff"),
            Map.entry("woff2", "font/woff2")
    );

    private final AtomicReference<LiveApp> app;
    private final GenerationLogWriter log;
    private final Path projectRoot;
    private final int port;
    private final HttpServer server;
    private final ThreadPoolExecutor executor;
    private final ReloadHub reloadHub;
    private volatile boolean running = true;

    public LiveServer(LiveApp app, GenerationLogWriter log, Path projectRoot, int requestedPort)
            throws IOException {
        this.app = new AtomicReference<>(app);
        this.log = log;
        this.projectRoot = projectRoot;
        this.server = createServer(requestedPort, log);
        this.port = ((InetSocketAddress) server.getAddress()).getPort();
        this.reloadHub = new ReloadHub(0, log);
        // Exactly one worker: requests execute sequentially, preserving state order.
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        this.server.setExecutor(executor);
        this.server.createContext("/", this::dispatch);
    }

    private static HttpServer createServer(int requestedPort, GenerationLogWriter log) throws IOException {
        try {
            return HttpServer.create(new InetSocketAddress(requestedPort), 0);
        } catch (IOException e) {
            if (requestedPort != 0) {
                log.warn("Port " + requestedPort + " is in use; falling back to a free ephemeral port.");
                return HttpServer.create(new InetSocketAddress(0), 0);
            }
            throw e;
        }
    }

    public void start() {
        server.start();
        reloadHub.start();
        System.out.println("   [INFO] Live server listening on http://localhost:" + port
                + " (single worker, dynamic per-request render).");
        log.info("Live server listening on http://localhost:" + port + " (single worker, dynamic per-request render).");
    }

    /** Pushes a reload frame to every connected browser tab (npm run dev-style). */
    public void reload() {
        reloadHub.broadcastReload();
    }

    public void stop() {
        running = false;
        reloadHub.stop();
        server.stop(0);
        executor.shutdownNow();
        log.info("Live server stopped.");
    }

    /**
     * Swaps in a freshly-booted app instance (simulates a server restart after a
     * source change); in-flight sequential handles see the new app on their next
     * request.
     */
    public void setApp(LiveApp newApp) {
        this.app.set(newApp);
        log.info("Switched to a freshly-booted app (source change applied).");
    }

    public int port() {
        return port;
    }

    // ==================== dispatch ====================
    private void dispatch(HttpExchange exchange) throws IOException {
        try {
            String rawPath = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getRawQuery();
            String method = exchange.getRequestMethod().toUpperCase();

            if ("GET".equals(method)) {
                Map<String, RTValue> args = parseQuery(query);
                Map<String, RTValue> form = Map.of();
                Response response = handle(exchange, method, rawPath, form, args);
                send(exchange, response);
                return;
            }

            if ("POST".equals(method)) {
                Map<String, RTValue> args = parseQuery(query);
                Map<String, RTValue> form = parseForm(exchange.getRequestBody());
                Response response = handle(exchange, method, rawPath, form, args);
                send(exchange, response);
                return;
            }

            Response r = new Response(405, "Method Not Allowed", "text/plain");
            r.header("Allow", "GET, POST");
            send(exchange, r);
        } catch (Throwable t) {
            try {
                String m = t.getClass().getSimpleName()
                        + (t.getMessage() != null ? ": " + t.getMessage() : "");
                log.error("Unhandled server error: " + m);
                LiveApp live = app.get();
                if (live != null) {
                    send(exchange, live.errorPage(new ErrorInfo("Internal server error",
                            List.of(new ErrorIssue(null, 0, 0, "internal", m)),
                            List.of("server"))));
                } else {
                    send(exchange, new Response(500, "Internal server error: " + m, "text/plain"));
                }
            } catch (Throwable ignored) {
                // The exchange is already broken; nothing left to do.
            }
        } finally {
            exchange.close();
        }
    }

    private Response handle(HttpExchange exchange, String method, String path,
                            Map<String, RTValue> form, Map<String, RTValue> args) {
        log.info((method.equals("GET") ? "GET  " : "POST ") + path);
        Response response = app.get().handle(method, path, form, args);
        if (response.status() == 404) {
            // Fall back to a static asset under the project root, then /index.html.
            Response staticResponse = serveStatic(path);
            if (staticResponse != null) {
                return staticResponse;
            }
            if ("/".equals(path)) {
                Response index = serveStatic("/index.html");
                if (index != null) {
                    return index;
                }
            }
        }
        return response;
    }

    private Response serveStatic(String path) {
        String normalized = path.startsWith("/") ? path.substring(1) : path;
        if (normalized.isEmpty() || normalized.contains("..") || normalized.startsWith("templates/")) {
            return null;
        }
        Path file = projectRoot.resolve(normalized);
        if (!Files.exists(file) || Files.isDirectory(file)) {
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(file);
            String ext = extensionOf(normalized);
            String type = CONTENT_TYPES.getOrDefault(ext, "application/octet-stream");
            Response r = new Response(200, new String(bytes, StandardCharsets.UTF_8), type);
            r.header("Content-Length", String.valueOf(bytes.length));
            return r;
        } catch (IOException e) {
            log.error("Could not read static asset '" + path + "': " + e.getMessage());
            return null;
        }
    }

    private static String extensionOf(String name) {
        int dot = name.lastIndexOf('.');
        if (dot < 0 || dot == name.length() - 1) {
            return "";
        }
        return name.substring(dot + 1).toLowerCase();
    }

    // ==================== body / query parsing ====================
    private Map<String, RTValue> parseQuery(String rawQuery) {
        Map<String, RTValue> out = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isEmpty()) {
            return out;
        }
        for (String pair : rawQuery.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) {
                continue;
            }
            String key = decode(pair.substring(0, eq));
            String value = decode(pair.substring(eq + 1));
            out.put(key, new RTString(value));
        }
        return out;
    }

    private Map<String, RTValue> parseForm(java.io.InputStream body) throws IOException {
        Map<String, RTValue> out = new LinkedHashMap<>();
        String raw = new String(body.readAllBytes(), StandardCharsets.UTF_8);
        if (raw.isEmpty()) {
            return out;
        }
        for (String pair : raw.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) {
                continue;
            }
            String key = decode(pair.substring(0, eq));
            String value = decode(pair.substring(eq + 1));
            out.put(key, new RTString(value));
        }
        return out;
    }

    private static String decode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    // ==================== response writing ====================
    private void send(HttpExchange exchange, Response response) throws IOException {
        byte[] bytes = response.body().getBytes(StandardCharsets.UTF_8);
        String contentType = response.contentType();
        if (contentType != null && contentType.startsWith("text/html") && bodyHasNoReloadScript(bytes)) {
            bytes = injectReloadScript(bytes);
        }
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("Server", "antlr-serve");
        for (Map.Entry<String, String> header : response.headers().entrySet()) {
            exchange.getResponseHeaders().set(header.getKey(), header.getValue());
        }
        exchange.sendResponseHeaders(response.status(), bytes.length);
        // HEAD-style bodies are empty; skip writing to avoid an empty-body exception.
        if (bytes.length > 0) {
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private static final String RELOAD_MARKER = "antlr-live-reload";

    private static boolean bodyHasNoReloadScript(byte[] body) {
        String text = new String(body, StandardCharsets.UTF_8);
        return !text.contains(RELOAD_MARKER);
    }

    private byte[] injectReloadScript(byte[] body) {
        String html = new String(body, StandardCharsets.UTF_8);
        String script = "<script data-"
                + RELOAD_MARKER
                + ">(function(){var w=new WebSocket('ws://localhost:"
                + reloadHub.port()
                + "/');w.onmessage=function(){location.reload()}})()</script>";
        if (html.contains("</body>")) {
            html = html.replace("</body>", script + "</body>");
        } else {
            html = html + script;
        }
        return html.getBytes(StandardCharsets.UTF_8);
    }

    public boolean isRunning() {
        return running;
    }
}
