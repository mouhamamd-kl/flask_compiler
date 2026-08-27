package antlr.generator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects timestamped generation events and writes generation_log.txt.
 */
public class GenerationLogWriter {

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final List<String> lines = new ArrayList<>();

    public GenerationLogWriter() {
        add("INFO", "Generation log started");
    }

    public void info(String message) {
        add("INFO", message);
    }

    public void warn(String message) {
        add("WARN", message);
        System.out.println("   [WARN] " + message);
    }

    public void error(String message) {
        add("ERROR", message);
        System.out.println("   [ERROR] " + message);
    }

    public void section(String title) {
        lines.add("");
        lines.add("============================================================");
        lines.add("  " + title);
        lines.add("============================================================");
    }

    private void add(String level, String message) {
        lines.add(String.format("[%s] %-5s %s", LocalDateTime.now().format(TS), level, message));
    }

    public void writeTo(Path file) throws IOException {
        Files.createDirectories(file.getParent());
        Files.write(file, lines, StandardCharsets.UTF_8);
    }
}
