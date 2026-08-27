import antlr.ast.node.ASTNode;
import antlr.gen.jinja2.jinja2Lexer;
import antlr.gen.jinja2.jinja2Parser;
import antlr.gen.python.pythonLexer;
import antlr.gen.python.pythonParser;
import antlr.generator.GenerationLogWriter;
import antlr.generator.ProjectGenerator;
import antlr.symbol.SymbolTable;
import antlr.visitor.ASTBuilder;
import antlr.visitor.ASTPrinter;
import antlr.visitor.JinjaASTBuilder;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Compiler {
    public static class Configs {
        public boolean hideWhitespace = false;
        public boolean watchMode = false;
        public boolean generateMode = false;
        public String generateTarget = null;
    }

    public Compiler(Configs configs) {
        this.configs = configs;

    }

    private final java.util.concurrent.atomic.AtomicBoolean compiling = new java.util.concurrent.atomic.AtomicBoolean(false);
    private volatile long lastCompileTime = 0;
    private final Configs configs;

    /**
     * Code generation phase: turn a Flask project (app.py + templates) into
     * static HTML plus compiler artifacts. See antlr.generator.ProjectGenerator.
     * Returns the ProjectGenerator for use with incremental watch mode.
     */
    public ProjectGenerator generate(String projectDir) {
        ProjectGenerator gen = new ProjectGenerator(new GenerationLogWriter());
        gen.generate(Paths.get(projectDir));
        return gen;
    }

    public void run(String testsDir, String watchDir, boolean showUsage) {
        if (watchDir != null) {
            // Main already determined the target — just compile it
            Path inputPath = Paths.get(watchDir);
            if (Files.isDirectory(inputPath)) {
                runAllTests(watchDir);
            } else {
                compile(watchDir);
            }
        } else {
            runAllTests(testsDir);
        }

        // Print usage if no args and not watching
        if (showUsage && !configs.watchMode) {
            System.out.println(Colors.TEAL + "Usage: " + Colors.RESET + "java Main [--generate | -g] [--watch | -W] [--hide-whitespace | -w] [file | dir]");
            System.out.println("  " + Colors.TEAL + "--generate, -g" + Colors.RESET + "         Code generation phase: render templates + write artifacts");
            System.out.println("  " + Colors.TEAL + "--watch, -W" + Colors.RESET + "            Watch mode: recompile on file changes");
            System.out.println("  " + Colors.TEAL + "--hide-whitespace, -w" + Colors.RESET + "  Hide whitespace-only text nodes in AST output");
            System.out.println("\n" + Colors.LIGHT + "Examples:" + Colors.RESET);
            System.out.println("  java Main                                    # Run all tests");
            System.out.println("  java Main tests/flask/templates/products.html");
            System.out.println("  java Main -w tests/flask/templates/products.html");
            System.out.println("  java Main --watch                            # Watch all tests");
            System.out.println("  java Main --watch tests/flask                # Watch specific directory");
        }

        // Start watcher if in watch mode
        if (configs.watchMode) {
            startWatcher(watchDir != null ? watchDir : testsDir);
        }
    }

    public void runAllTests(String testsDir) {
        try {
            Files.walk(Paths.get(testsDir))
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String name = path.toString().toLowerCase();
                        return name.endsWith(".py") || name.endsWith(".html");
                    })
                    .sorted()
                    .forEach(this::compile);
        } catch (IOException e) {
            System.err.println("Error reading tests directory: " + e.getMessage());
        }
    }

    private void startWatcher(String watchDir) {
        try {
            Path watchPath = Paths.get(watchDir);
            System.out.println(Colors.TEAL + "\n👁  Watching for changes in: " + Colors.LIGHT + watchPath.toAbsolutePath() + Colors.RESET);
            System.out.println(Colors.GRAY + "   Press Ctrl+C to stop" + Colors.RESET + "\n");


            DirectoryWatcher watcher = DirectoryWatcher.builder()
                    .path(watchPath)
                    .fileHashing(true)
                    .listener(event -> {
                        if (event.eventType() == DirectoryChangeEvent.EventType.MODIFY
                                || event.eventType() == DirectoryChangeEvent.EventType.CREATE) {
                            Path changed = event.path();
                            String fileName = changed.getFileName().toString().toLowerCase();
                            if (fileName.endsWith(".py") || fileName.endsWith(".html")) {
                                if (!compiling.getAndSet(true)) {

                                    try {

                                        long now = System.currentTimeMillis();
                                        if (now - lastCompileTime > 10000) {
                                            System.out.println("\n" + Colors.TEAL + "═".repeat(70) + Colors.RESET);
                                            System.out.println(Colors.TEAL + "  🔄 Change detected: " + Colors.LIGHT + changed.getFileName() + Colors.RESET);
                                            System.out.println(Colors.TEAL + "═".repeat(70) + Colors.RESET);
                                            lastCompileTime = now;
                                            compile(changed);
                                            System.out.println(Colors.TEAL + "\n👁  Watching for changes in: " + Colors.LIGHT + watchPath.toAbsolutePath() + Colors.RESET);
                                            System.out.println(Colors.GRAY + "   Press Ctrl+C to stop" + Colors.RESET + "\n");
                                        }
                                    } finally {
                                        compiling.set(false);
                                    }
                                }
                            }
                        }
                    })
                    .build();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    watcher.close();
                } catch (Exception ignored) {
                }
                System.out.println(Colors.TEAL + "\n👋 Watcher stopped." + Colors.RESET);
            }));

            watcher.watch(); // blocks until process is killed

        } catch (Exception e) {
            System.err.println(Colors.RED + "Error starting watcher: " + e.getMessage() + Colors.RESET);
        }
    }

    /**
     * Watch mode for generate: re-runs incremental generation on file changes.
     */
    public void startGenerateWatcher(String watchDir, ProjectGenerator generator) {
        try {
            Path watchPath = Paths.get(watchDir);
            System.out.println(Colors.TEAL + "\n👁  Watching for changes in: " + Colors.LIGHT + watchPath.toAbsolutePath() + Colors.RESET);
            System.out.println(Colors.GRAY + "   Press Ctrl+C to stop" + Colors.RESET + "\n");

            DirectoryWatcher watcher = DirectoryWatcher.builder()
                    .path(watchPath)
                    .fileHashing(true)
                    .listener(event -> {
                        if (event.eventType() == DirectoryChangeEvent.EventType.MODIFY
                                || event.eventType() == DirectoryChangeEvent.EventType.CREATE) {
                            Path changed = event.path();
                            String fileName = changed.getFileName().toString().toLowerCase();
                            if (fileName.endsWith(".py") || fileName.endsWith(".html")) {
                                if (!compiling.getAndSet(true)) {
                                    try {
                                        long now = System.currentTimeMillis();
                                        if (now - lastCompileTime > 10000) {
                                            System.out.println("\n" + Colors.TEAL + "═".repeat(70) + Colors.RESET);
                                            System.out.println(Colors.TEAL + "  🔄 Change detected: " + Colors.LIGHT + changed.getFileName() + Colors.RESET);
                                            System.out.println(Colors.TEAL + "═".repeat(70) + Colors.RESET);
                                            lastCompileTime = now;
                                            generator.regenerateIncremental(changed);
                                            System.out.println(Colors.TEAL + "\n👁  Watching for changes in: " + Colors.LIGHT + watchPath.toAbsolutePath() + Colors.RESET);
                                            System.out.println(Colors.GRAY + "   Press Ctrl+C to stop" + Colors.RESET + "\n");
                                        }
                                    } finally {
                                        compiling.set(false);
                                    }
                                }
                            }
                        }
                    })
                    .build();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    watcher.close();
                } catch (Exception ignored) {
                }
                System.out.println(Colors.TEAL + "\n👋 Watcher stopped." + Colors.RESET);
            }));

            watcher.watch(); // blocks until process is killed

        } catch (Exception e) {
            System.err.println(Colors.RED + "Error starting generate watcher: " + e.getMessage() + Colors.RESET);
        }
    }

    public void compile(String filePath) {
        compile(Paths.get(filePath));
    }

    public void compile(Path filePath) {
        try {
            String fileName = filePath.getFileName().toString();
            String filePathStr = filePath.toString().toLowerCase();

            // Determine file type
            String fileType = filePathStr.endsWith(".py") ? "Python" :
                    filePathStr.endsWith(".html") ? "Jinja2" : "Unknown";

            printHeader(fileType, fileName);

            String sourceCode = Files.readString(filePath);

            // عرض الكود المصدري
            System.out.println("\n" + Colors.TEAL + "📄 Source Code:" + Colors.RESET);
            System.out.println(Colors.GRAY + "─".repeat(40) + Colors.RESET);
            System.out.println(Colors.LIGHT + sourceCode + Colors.RESET);
            System.out.println(Colors.GRAY + "─".repeat(40) + Colors.RESET);

            // بدء الترجمة - Route based on file extension
            CompilationResult result;
            if (filePathStr.endsWith(".py")) {
                result = compilePythonSource(sourceCode);
            } else if (filePathStr.endsWith(".html")) {
                result = compileJinjaSource(sourceCode);
            } else {
                result = new CompilationResult();
                result.errors.add("Unsupported file type: " + fileName);
            }

            printResult(result);

        } catch (IOException e) {
            System.err.println(Colors.RED + "Error reading file: " + filePath + " - " + e.getMessage() + Colors.RESET);
        }
    }

    /**
     * ترجمة كود Python
     */
    public CompilationResult compilePythonSource(String sourceCode) {
        CompilationResult result = new CompilationResult();

        try {
            // ==================== Phase 1: Lexical Analysis ====================
            printPhase(1, "Lexical Analysis");
            CharStream input = CharStreams.fromString(sourceCode);
            pythonLexer lexer = new pythonLexer(input);

            // جمع الأخطاء المعجمية
            lexer.removeErrorListeners();
            lexer.addErrorListener(new CompilerErrorListener(result));

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // عرض الـ Tokens
            tokens.fill();
            System.out.println("\n" + Colors.TEAL + "   Tokens:" + Colors.RESET);
            for (Token token : tokens.getTokens()) {
                if (token.getType() != Token.EOF) {
                    String tokenName = pythonLexer.VOCABULARY.getSymbolicName(token.getType());
                    if (tokenName == null) {
                        // Handle parser tokens (INDENT, DEDENT from DenterHelper)
                        if (token.getType() == pythonParser.INDENT) {
                            tokenName = "INDENT";
                        } else if (token.getType() == pythonParser.DEDENT) {
                            tokenName = "DEDENT";
                        } else {
                            tokenName = "UNKNOWN_" + token.getType();
                        }
                    }
                    System.out.printf("   " + Colors.LIGHT + "[%-15s]" + Colors.RESET + " " + Colors.TEAL + "'%s'" + Colors.RESET + " " + Colors.GRAY + "(Line %d, Col %d)" + Colors.RESET + "%n",
                            tokenName,
                            token.getText().replace("\n", "newline"),
                            token.getLine(),
                            token.getCharPositionInLine());
                }
            }

            // ==================== Phase 2: Syntax Analysis ====================
            printPhase(2, "Syntax Analysis (Parsing)");
            pythonParser parser = new pythonParser(tokens);

            // جمع الأخطاء النحوية
            parser.removeErrorListeners();
            parser.addErrorListener(new CompilerErrorListener(result));

            ParseTree parseTree = parser.root();

            if (!result.errors.isEmpty()) {
                result.success = false;
                return result;
            }

            // ==================== Phase 3: AST Building ====================
            printPhase(3, "Building AST");
            ASTBuilder builder = new ASTBuilder();
            ASTNode ast = builder.visit(parseTree);
            result.ast = ast;
            result.symbolTable = builder.getSymbolTable();

            // جمع الأخطاء الدلالية
            if (!builder.getSemanticErrors().isEmpty()) {
                result.success = false;
                result.errors.addAll(builder.getSemanticErrors());
                return result;
            }

            // ==================== Phase 4: Print AST ====================
            System.out.println("\n" + Colors.TEAL + "🌳 Abstract Syntax Tree:" + Colors.RESET);
            ASTPrinter printer = new ASTPrinter(configs.hideWhitespace);
            printer.print(ast);

            // ==================== Phase 5: Symbol Table ====================
            System.out.println("\n" + Colors.TEAL + "📋 Symbol Table:" + Colors.RESET);
            result.symbolTable.printAll();

            result.success = true;

        } catch (Exception e) {
            result.success = false;
            result.errors.add("Compilation error: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ترجمة قوالب Jinja2
     */
    public CompilationResult compileJinjaSource(String sourceCode) {
        CompilationResult result = new CompilationResult();

        try {
            // ==================== Phase 1: Lexical Analysis ====================
            printPhase(1, "Lexical Analysis");
            CharStream input = CharStreams.fromString(sourceCode);
            jinja2Lexer lexer = new jinja2Lexer(input);

            // جمع الأخطاء المعجمية
            lexer.removeErrorListeners();
            lexer.addErrorListener(new CompilerErrorListener(result));

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // عرض الـ Tokens
            tokens.fill();
            System.out.println("\n" + Colors.TEAL + "   Tokens:" + Colors.RESET);
            for (Token token : tokens.getTokens()) {
                if (token.getType() != Token.EOF) {
                    String tokenName = jinja2Lexer.VOCABULARY.getSymbolicName(token.getType());
                    if (tokenName == null) {
                        tokenName = "UNKNOWN_" + token.getType();
                    }
                    System.out.printf("   " + Colors.LIGHT + "[%-15s]" + Colors.RESET + " " + Colors.TEAL + "'%s'" + Colors.RESET + " " + Colors.GRAY + "(Line %d, Col %d)" + Colors.RESET + "%n",
                            tokenName,
                            token.getText().replace("\n", "\\n").replace("\r", "\\r"),
                            token.getLine(),
                            token.getCharPositionInLine());
                }
            }

            // ==================== Phase 2: Syntax Analysis ====================
            printPhase(2, "Syntax Analysis (Parsing)");
            jinja2Parser parser = new jinja2Parser(tokens);

            // جمع الأخطاء النحوية
            parser.removeErrorListeners();
            parser.addErrorListener(new CompilerErrorListener(result));

            ParseTree parseTree = parser.template();

            if (!result.errors.isEmpty()) {
                result.success = false;
                return result;
            }

            // ==================== Phase 3: AST Building ====================
            printPhase(3, "Building AST");
            JinjaASTBuilder builder = new JinjaASTBuilder();
            ASTNode ast = builder.visit(parseTree);
            result.ast = ast;

            // ==================== Phase 4: Print AST ====================
            System.out.println("\n" + Colors.TEAL + "🌳 Abstract Syntax Tree:" + Colors.RESET);
            ASTPrinter printer = new ASTPrinter(configs.hideWhitespace);
            printer.print(ast);

            // Note: JinjaASTBuilder doesn't have a symbol table

            result.success = true;

        } catch (Exception e) {
            result.success = false;
            result.errors.add("Compilation error: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    private void printHeader(String fileType, String fileName) {
        System.out.println("\n" + Colors.TEAL + "═".repeat(70) + Colors.RESET);
        System.out.println(Colors.TEAL + "  " + Colors.BOLD + "COMPILING (" + fileType + "): " + Colors.LIGHT + fileName + Colors.RESET);
        System.out.println(Colors.TEAL + "═".repeat(70) + Colors.RESET);
    }

    private void printPhase(int phase, String name) {
        System.out.println("\n" + Colors.TEAL + "🔍 Phase " + phase + ": " + Colors.LIGHT + name + "..." + Colors.RESET);
    }

    private void printResult(CompilationResult result) {
        if (result.success) {
            System.out.println("\n" + Colors.TEAL + "✅ Compilation successful!" + Colors.RESET);
        } else {
            System.out.println("\n" + Colors.RED + "❌ Compilation failed!" + Colors.RESET);
            for (String error : result.errors) {
                System.out.println(Colors.RED + "   Error: " + error + Colors.RESET);
            }
        }
    }

    /**
     * نتيجة الترجمة
     */
    private static class CompilationResult {
        public boolean success = false;
        public ASTNode ast = null;
        public SymbolTable symbolTable = null;
        public java.util.List<String> errors = new java.util.ArrayList<>();
    }

    /**
     * مستمع الأخطاء
     */
    private static class CompilerErrorListener extends BaseErrorListener {
        private final CompilationResult result;

        public CompilerErrorListener(CompilationResult result) {
            this.result = result;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            result.errors.add(String.format("Line %d:%d - %s", line, charPositionInLine, msg));
        }
    }
}
