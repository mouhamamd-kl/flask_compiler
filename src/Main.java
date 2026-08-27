import java.nio.file.*;

public class Main {

    private record ArgsResult(int fileArgIndex, Compiler.Configs configs) {
    }

    public static void main(String[] args) {
        String testsDir = "tests/flask";
        Compiler.Configs configs = new Compiler.Configs();
        ArgsResult parsed = solveArgs(args, configs);

        if (configs.generateMode) {
            String target = configs.generateTarget != null ? configs.generateTarget : testsDir;
            Compiler compiler = new Compiler(configs);
            antlr.generator.ProjectGenerator gen = compiler.generate(target);
            if (configs.watchMode) {
                compiler.startGenerateWatcher(target, gen);
            }
            return;
        }

        String watchDir = null;
        boolean showUsage = parsed.fileArgIndex() < 0;

        if (parsed.fileArgIndex() >= 0) {
            Path inputPath = Paths.get(args[parsed.fileArgIndex()]);
            if (Files.isDirectory(inputPath)) {
                watchDir = args[parsed.fileArgIndex()];
            } else if (configs.watchMode) {
                watchDir = inputPath.getParent().toString();
            }
        } else if (configs.watchMode) {
            watchDir = testsDir;
        }

        Compiler compiler = new Compiler(configs);
        compiler.run(testsDir, watchDir, showUsage);
    }

    public static ArgsResult solveArgs(String[] args, Compiler.Configs configs) {
        int fileArgIndex = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--hide-whitespace") || args[i].equals("-w")) {
                configs.hideWhitespace = true;
            } else if (args[i].equals("--watch") || args[i].equals("-W")) {
                configs.watchMode = true;
            } else if (args[i].equals("--generate") || args[i].equals("-g")) {
                configs.generateMode = true;
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    configs.generateTarget = args[i + 1];
                    i++;
                }
            } else {
                fileArgIndex = i;
                break;
            }
        }
        return new ArgsResult(fileArgIndex, configs);
    }
}
