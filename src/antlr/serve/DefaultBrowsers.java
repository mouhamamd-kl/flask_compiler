package antlr.serve;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.net.URI;

/**
 * Opens the OS default browser like `flask run` / Vite do on serve start.
 * Prefers the JDK Desktop API, falling back to a native launcher per OS so a
 * console/SSH session without a GUI still degrades gracefully (never throws).
 */
public final class DefaultBrowsers {

    private DefaultBrowsers() {
        // utility class
    }

    /**
     * @param url absolute http(s) URL to open
     * @return true if a browser launch was attempted successfully
     */
    public static boolean openDefaultBrowser(String url) {
        if (GraphicsEnvironment.isHeadless()) {
            return false;
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(URI.create(url));
                    return true;
                }
            }
        } catch (Exception ignored) {
            // fall through to the native launcher
        }
        return launchNative(url);
    }

    private static boolean launchNative(String url) {
        String[] command = nativeCommand(url);
        if (command == null) {
            return false;
        }
        try {
            Runtime.getRuntime().exec(command);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static String[] nativeCommand(String url) {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", url};
        }
        if (os.contains("mac")) {
            return new String[]{"open", url};
        }
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return new String[]{"xdg-open", url};
        }
        return null;
    }
}