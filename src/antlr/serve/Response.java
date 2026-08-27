package antlr.serve;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An HTTP response produced by a live route handler, mirroring Flask's
 * response contract: a status code, optional headers (e.g. Location for a
 * redirect) and a body (for rendered HTML / JSON).
 */
public final class Response {

    private final int status;
    private final Map<String, String> headers = new LinkedHashMap<>();
    private final String body;
    private final String contentType;

    public Response(int status, String body, String contentType) {
        this.status = status;
        this.body = body;
        this.contentType = contentType;
    }

    public static Response ok(String body, String contentType) {
        return new Response(200, body == null ? "" : body, contentType);
    }

    public static Response redirect(String location) {
        Response r = new Response(302, "", "text/html");
        r.headers.put("Location", location);
        return r;
    }

    public int status() {
        return status;
    }

    public String body() {
        return body == null ? "" : body;
    }

    public String contentType() {
        return contentType;
    }

    public void header(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> headers() {
        return headers;
    }
}
