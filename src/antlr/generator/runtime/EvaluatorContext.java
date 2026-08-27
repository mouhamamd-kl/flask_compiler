package antlr.generator.runtime;

import antlr.generator.GenerationLogWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Shared evaluation services passed through the runtime components:
 * generation-log access and once-only warnings.
 */
public final class EvaluatorContext {

    private final GenerationLogWriter log;
    private final Set<String> warnedOnce = new HashSet<>();

    public EvaluatorContext(GenerationLogWriter log) {
        this.log = log;
    }

    public GenerationLogWriter log() {
        return log;
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void info(String message) {
        log.info(message);
    }

    /** Logs only the first warning for the given deduplication key. */
    public void warnOnce(String key, String message) {
        if (warnedOnce.add(key)) {
            log.warn(message);
        }
    }
}
