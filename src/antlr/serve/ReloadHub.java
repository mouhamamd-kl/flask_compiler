package antlr.serve;

import antlr.generator.GenerationLogWriter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * The live-reload WebSocket hub for serve mode. Mirrors the {@code npm run dev}
 * pattern: the dev server pushes a "reload" frame to every connected browser
 * The client script injected into served HTML opens a single WebSocket to this
 * hub and calls {@code location.reload()} whenever a frame arrives, so editing
 * app.py or a template refreshes every open tab instantly (no polling, no SSE).
 *
 * Delegates to Java-WebSocket's {@link WebSocketServer} (separate background
 * thread), keeping the single-worker HTTP model untouched. If the requested
 * port is in use it logs a warning and falls back to an OS-assigned free port.
 *
 * Java-WebSocket's {@code getPort()} only reports the port it was constructed
 * with, so for a dynamic (0) port we discover a free one with a throwaway
 * {@link ServerSocket} and hand the concrete number to the server — otherwise
 * the browser would be told to connect to {@code ws://localhost:0}.
 */
public final class ReloadHub {

    private final GenerationLogWriter log;
    private final WebSocketServer server;
    private final int port;

    public ReloadHub(int requestedPort, GenerationLogWriter log) throws IOException {
        this.log = log;
        int resolved = requestedPort != 0 ? requestedPort : findFreePort();
        this.server = createServer(resolved);
        this.port = server.getPort();
        this.server.setConnectionLostTimeout(10);
    }

    private static int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private WebSocketServer createServer(int requestedPort) throws IOException {
        try {
            return newServer(requestedPort);
        } catch (Exception e) {
            if (requestedPort != 0) {
                log.warn("Reload port " + requestedPort + " is in use; falling back to a free ephemeral port.");
                return newServer(findFreePort());
            }
            throw new IOException("Could not bind reload WebSocket server", e);
        }
    }

    private WebSocketServer newServer(int listenPort) {
        return new WebSocketServer(new InetSocketAddress(listenPort)) {
            @Override
            public void onStart() {
            }

            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                log.info("Reload client connected: " + conn.getRemoteSocketAddress());
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                log.info("Reload client disconnected.");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                // clients never send meaningful data — ignore
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                log.warn("Reload WebSocket error: " + ex.getMessage());
            }
        };
    }

    /** Pushes a reload frame to every connected browser. */
    public void broadcastReload() {
        server.broadcast("reload");
    }

    public int port() {
        return port;
    }

    public void start() {
        server.start();
        log.info("Reload WebSocket listening on ws://localhost:" + port);
    }

    public void stop() {
        try {
            server.stop(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
