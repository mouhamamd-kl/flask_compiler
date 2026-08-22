public class Colors {
    // ==================== ANSI Colors (Custom Palette) ====================
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    // Custom palette: 222831, 393E46, 00ADB5, EEEEEE
    public static final String DARK = "\u001B[38;2;8;217;214m";       // 08D9D6 - Bright cyan
    public static final String GRAY = "\u001B[38;2;255;46;99m";      // FF2E63 - Pink/red accent
    public static final String TEAL = "\u001B[38;2;0;173;181m";      // 00ADB5 - Primary accent
    public static final String LIGHT = "\u001B[38;2;238;238;238m";   // EEEEEE - Main text

    // Keep RED for errors
    public static final String RED = "\u001B[31m";
}
