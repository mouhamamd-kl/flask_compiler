package antlr.generator;

import antlr.ast.jinja2.TemplateNode;
import antlr.ast.node.ASTNode;
import antlr.ast.python.ProgramNode;
import antlr.gen.jinja2.jinja2Lexer;
import antlr.gen.jinja2.jinja2Parser;
import antlr.gen.python.pythonLexer;
import antlr.gen.python.pythonParser;
import antlr.generator.jinja.JinjaRenderer;
import antlr.generator.json.AstJsonWriter;
import antlr.generator.python.PythonEvaluator;
import antlr.generator.render.RenderedPage;
import antlr.generator.render.RTRenderRequest;
import antlr.runtime.values.RTValue;
import antlr.symbol.SymbolTable;
import antlr.visitor.ASTBuilder;
import antlr.visitor.JinjaASTBuilder;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Orchestrates the code generation phase:
 *
 *   app.py  -> [lex/parse/AST] -> ast_python.json + semantic_report.txt
 *           -> [evaluate]      -> context data + render_template(...) requests
 *   *.html  -> [lex/parse/AST] -> ast_jinja.json
 *           -> [render]        -> output/<page>.html (static)
 *           -> copy companion files (app.py, style.css, script.js) untouched
 *           -> generation_log.txt
 */
public class ProjectGenerator {

    public static final String OUTPUT_DIR = "output";
    public static final String COMPILER_OUTPUT_DIR = "compiler_output";

    private static final String TEAL = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    private final GenerationLogWriter log;

    // populated during generation
    private Path projectRoot;
    private Path templatesDir;

    /** Parsed template cache shared by the renderer for include support. */
    private final Map<String, TemplateNode> templateRegistry = new LinkedHashMap<>();

    // state persisted across generate() + regenerateIncremental()
    private Path appFile;
    private ProgramNode program;
    private PythonEvaluator.EvaluationResult evaluation;
    private List<RTRenderRequestLite> requests;
    private Map<String, String> urlToPage;

    public ProjectGenerator(GenerationLogWriter log) {
        this.log = log;
    }

    public void generate(Path target) {
        long startMs = System.currentTimeMillis();
        System.out.println("\n" + TEAL + "=".repeat(70) + RESET);
        System.out.println(TEAL + "  CODE GENERATION PHASE - Flask project -> static HTML" + RESET);
        System.out.println(TEAL + "=".repeat(70) + RESET);

        try {
            resolveLayout(target);

            prepareRunDirs();

            // ---------- Phase A: parse app.py ----------
            log.section("PHASE A - Parsing app.py");
            appFile = projectRoot.resolve("app.py");
            String source = Files.readString(appFile, StandardCharsets.UTF_8);
            PythonParseResult parsed = parsePython(source);

            if (!parsed.errors.isEmpty()) {
                log.error("Syntax errors in app.py; generation aborted:");
                parsed.errors.forEach(log::error);
                finishLog(startMs);
                return;
            }

            Files.writeString(compilerOut("ast_python.json"), AstJsonWriter.toJson(parsed.program),
                    StandardCharsets.UTF_8);
            log.info("Wrote " + COMPILER_OUTPUT_DIR + "/ast_python.json");

            Files.writeString(compilerOut("semantic_report.txt"),
                    SemanticReportWriter.write(parsed.symbolTable, parsed.semanticErrors), StandardCharsets.UTF_8);
            log.info("Wrote " + COMPILER_OUTPUT_DIR + "/semantic_report.txt");

            if (!parsed.semanticErrors.isEmpty()) {
                log.error("Semantic errors detected; HTML rendering aborted (artifacts were still written).");
                parsed.semanticErrors.forEach(log::error);
                finishLog(startMs);
                return;
            }

            // ---------- Phase B: evaluate data-preparation code ----------
            log.section("PHASE B - Evaluating Python program");
            PythonEvaluator evaluator = new PythonEvaluator(log);
            program = parsed.program;
            evaluation = evaluator.run(program);

            // ---------- Phase C: parse referenced templates ----------
            log.section("PHASE C - Parsing templates");
            requests = new ArrayList<>();
            for (antlr.generator.render.RTRenderRequest req : evaluation.renderRequests) {
                TemplateNode template = loadTemplate(req.getTemplateName());
                requests.add(new RTRenderRequestLite(req, template));
            }

            Files.writeString(compilerOut("ast_jinja.json"), AstJsonWriter.templatesToJson(templateRegistry),
                    StandardCharsets.UTF_8);
            log.info("Wrote " + COMPILER_OUTPUT_DIR + "/ast_jinja.json (" + templateRegistry.size()
                    + " template(s))");

            // ---------- Phase D: render ----------
            log.section("PHASE D - Rendering templates");
            int rendered = renderAllPages();

            // ---------- Phase E: companion files ----------
            log.section("PHASE E - Companion files");
            copyCompanion(appFile, "app.py");
            copyCompanionIfExists("style.css");
            copyCompanionIfExists("script.js");

            // ---------- Summary ----------
            finishLog(startMs);
            System.out.println(GREEN + "\n  Generation complete: " + rendered + " page(s) in "
                    + OUTPUT_DIR + "/ , artifacts in " + COMPILER_OUTPUT_DIR + "/" + RESET);
        } catch (Exception e) {
            log.error("Generation failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            try {
                finishLog(startMs);
            } catch (Exception ignored) {
            }
            e.printStackTrace();
        }
    }

    // ==================== incremental regeneration ====================

    /**
     * Called by the file watcher when a file changes during generate+watch mode.
     * Routes to the minimal re-generation path based on which file changed.
     */
    public void regenerateIncremental(Path changedFile) {
        long startMs = System.currentTimeMillis();
        try {
            Path changedAbs = changedFile.toAbsolutePath().normalize();
            Path appFileAbs = appFile.toAbsolutePath().normalize();

            if (changedAbs.equals(appFileAbs)) {
                System.out.println(TEAL + "  app.py changed — re-running all phases" + RESET);
                regenerateFromAppPy(startMs);
            } else {
                String baseName = changedFile.getFileName().toString();
                System.out.println(TEAL + "  Template changed: " + baseName + " — re-parsing and re-rendering affected pages" + RESET);
                regenerateFromTemplate(baseName, startMs);
            }
        } catch (Exception e) {
            System.err.println(RED + "Incremental regeneration failed: " + e.getMessage() + RESET);
            e.printStackTrace();
        }
    }

    private void regenerateFromAppPy(long startMs) throws Exception {
        // Phase A: re-parse app.py
        log.section("INCREMENTAL - Re-parsing app.py");
        String source = Files.readString(appFile, StandardCharsets.UTF_8);
        PythonParseResult parsed = parsePython(source);

        if (!parsed.errors.isEmpty()) {
            log.error("Syntax errors in app.py; regeneration aborted:");
            parsed.errors.forEach(log::error);
            finishLog(startMs);
            return;
        }

        Files.writeString(compilerOut("ast_python.json"), AstJsonWriter.toJson(parsed.program),
                StandardCharsets.UTF_8);

        Files.writeString(compilerOut("semantic_report.txt"),
                SemanticReportWriter.write(parsed.symbolTable, parsed.semanticErrors), StandardCharsets.UTF_8);

        if (!parsed.semanticErrors.isEmpty()) {
            log.error("Semantic errors detected; HTML rendering aborted.");
            parsed.semanticErrors.forEach(log::error);
            finishLog(startMs);
            return;
        }

        // Phase B: re-evaluate
        log.section("INCREMENTAL - Re-evaluating Python program");
        program = parsed.program;
        PythonEvaluator evaluator = new PythonEvaluator(log);
        evaluation = evaluator.run(program);

        // Phase C: re-parse all referenced templates (clear cache first)
        log.section("INCREMENTAL - Re-parsing templates");
        templateRegistry.clear();
        requests = new ArrayList<>();
        for (antlr.generator.render.RTRenderRequest req : evaluation.renderRequests) {
            TemplateNode template = loadTemplate(req.getTemplateName());
            requests.add(new RTRenderRequestLite(req, template));
        }

        Files.writeString(compilerOut("ast_jinja.json"), AstJsonWriter.templatesToJson(templateRegistry),
                StandardCharsets.UTF_8);

        // Phase D: re-render all pages
        int rendered = renderAllPages();

        // Phase E: companion files
        log.section("INCREMENTAL - Companion files");
        copyCompanion(appFile, "app.py");
        copyCompanionIfExists("style.css");
        copyCompanionIfExists("script.js");

        finishLog(startMs);
        System.out.println(GREEN + "\n  Regeneration complete: " + rendered + " page(s) in "
                + OUTPUT_DIR + "/ , artifacts in " + COMPILER_OUTPUT_DIR + "/" + RESET);
    }

    private void regenerateFromTemplate(String baseName, long startMs) throws Exception {
        // Re-parse just the changed template
        templateRegistry.remove(baseName);
        loadTemplate(baseName);

        // Rebuild requests so the updated template is reflected
        requests = new ArrayList<>();
        for (antlr.generator.render.RTRenderRequest req : evaluation.renderRequests) {
            TemplateNode tmpl = loadTemplate(req.getTemplateName());
            requests.add(new RTRenderRequestLite(req, tmpl));
        }

        Files.writeString(compilerOut("ast_jinja.json"), AstJsonWriter.templatesToJson(templateRegistry),
                StandardCharsets.UTF_8);

        // Re-render all pages (template content may affect any page)
        int rendered = renderAllPages();

        finishLog(startMs);
        System.out.println(GREEN + "\n  Template regeneration complete: " + rendered + " page(s) in "
                + OUTPUT_DIR + "/" + RESET);
    }

    /**
     * Render all current requests to output/ files. Returns the number of pages written.
     */
    private int renderAllPages() throws Exception {
        log.section("RENDER - Rendering templates");
        urlToPage = new LinkedHashMap<>();
        List<RenderedPage> pages = new ArrayList<>();

        for (RTRenderRequestLite req : requests) {
            if (req.template == null) {
                continue;
            }
            LinkedHashMap<String, RTValue> context = new LinkedHashMap<>(req.request.getContext());
            for (Map.Entry<String, RTValue> g : evaluation.globals.entrySet()) {
                context.putIfAbsent(g.getKey(), g.getValue());
            }
            JinjaRenderer renderer = new JinjaRenderer(log, templateRegistry);
            String html = renderer.render(req.template, req.request.getTemplateName(), context);

            String sourceUrl = req.request.getSourceUrl();
            String routePattern = req.request.getRoutePattern();
            boolean dynamicPage = sourceUrl != null && !sourceUrl.equals(routePattern);
            String outName;
            if (dynamicPage) {
                outName = sourceUrl.replaceAll("^/", "").replace('/', '_') + ".html";
                urlToPage.put(sourceUrl, outName);
            } else {
                outName = Paths.get(req.request.getTemplateName()).getFileName().toString();
                if (sourceUrl != null) {
                    urlToPage.putIfAbsent(sourceUrl, outName);
                }
            }
            pages.add(new RenderedPage(outName, html, dynamicPage ? sourceUrl : null));
        }

        int rendered = 0;
        for (RenderedPage page : pages) {
            String html = StaticLinkRewriter.rewrite(page.html(), urlToPage);
            Path outFile = Paths.get(OUTPUT_DIR).resolve(page.fileName());
            Files.writeString(outFile, html, StandardCharsets.UTF_8);
            rendered++;
            log.info("Generated " + OUTPUT_DIR + "/" + page.fileName()
                    + (page.routeUrl() != null ? " (route: " + page.routeUrl() + ")" : ""));
        }
        return rendered;
    }

    // ==================== layout ====================
    private void resolveLayout(Path target) throws IOException {
        Path dir = Files.isDirectory(target) ? target : target.getParent();
        projectRoot = dir.toAbsolutePath().normalize();

        Path directTemplates = projectRoot.resolve("templates");
        if (Files.isDirectory(directTemplates)) {
            templatesDir = directTemplates;
        } else if (projectRoot.getFileName() != null && projectRoot.getFileName().toString().equals("templates")) {
            templatesDir = projectRoot;
            projectRoot = projectRoot.getParent();
        } else {
            templatesDir = projectRoot; // fall back: templates sit beside app.py
        }
        log.info("Project root: " + projectRoot);
        log.info("Templates directory: " + templatesDir);
    }

    private void prepareRunDirs() throws IOException {
        wipeDir(Paths.get(OUTPUT_DIR));
        wipeDir(Paths.get(COMPILER_OUTPUT_DIR));
        Files.createDirectories(Paths.get(OUTPUT_DIR));
        Files.createDirectories(Paths.get(COMPILER_OUTPUT_DIR));
        log.info("Prepared clean " + OUTPUT_DIR + "/ and " + COMPILER_OUTPUT_DIR + "/ directories.");
    }

    private static void wipeDir(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(dir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException ignored) {
                }
            });
        }
    }

    private Path compilerOut(String fileName) {
        return Paths.get(COMPILER_OUTPUT_DIR).resolve(fileName);
    }

    // ==================== parsing ====================
    private static class PythonParseResult {
        ProgramNode program;
        SymbolTable symbolTable;
        List<String> semanticErrors = new ArrayList<>();
        List<String> errors = new ArrayList<>();
    }

    private PythonParseResult parsePython(String source) {
        PythonParseResult result = new PythonParseResult();
        CollectingErrors errors = attachListeners();
        runPython(result, source, errors);
        result.errors.addAll(errors.collected);
        return result;
    }

    private void runPython(PythonParseResult result, String source, CollectingErrors errors) {
        pythonLexer lexer = new pythonLexer(CharStreams.fromString(source));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errors);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pythonParser parser = new pythonParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errors);
        ParseTree tree = parser.root();
        if (!errors.collected.isEmpty()) {
            return;
        }
        ASTBuilder builder = new ASTBuilder();
        ASTNode ast = builder.visit(tree);
        result.program = (ProgramNode) ast;
        result.symbolTable = builder.getSymbolTable();
        result.semanticErrors.addAll(builder.getSemanticErrors());
        log.info("app.py parsed successfully.");
    }

    private TemplateNode loadTemplate(String templateName) {
        String baseName = Paths.get(templateName).getFileName().toString();
        if (templateRegistry.containsKey(baseName)) {
            return templateRegistry.get(baseName);
        }
        Path file = templatesDir.resolve(baseName);
        if (!Files.exists(file)) {
            log.error("Template '" + templateName + "' not found under " + templatesDir + "; skipped.");
            templateRegistry.put(baseName, null);
            return null;
        }
        try {
            String source = Files.readString(file, StandardCharsets.UTF_8);
            CollectingErrors errors = attachListeners();
            jinja2Lexer lexer = new jinja2Lexer(CharStreams.fromString(source));
            lexer.removeErrorListeners();
            lexer.addErrorListener(errors);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            jinja2Parser parser = new jinja2Parser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(errors);
            ParseTree tree = parser.template();
            if (!errors.collected.isEmpty()) {
                log.error("Template '" + baseName + "' has syntax errors:");
                errors.collected.forEach(log::error);
                templateRegistry.put(baseName, null);
                return null;
            }
            JinjaASTBuilder builder = new JinjaASTBuilder();
            ASTNode ast = builder.visit(tree);
            templateRegistry.put(baseName, (TemplateNode) ast);
            log.info("Template '" + baseName + "' parsed successfully.");
            return (TemplateNode) ast;
        } catch (IOException e) {
            log.error("Could not read template '" + templateName + "': " + e.getMessage());
            templateRegistry.put(baseName, null);
            return null;
        }
    }

    private static final class CollectingErrors extends BaseErrorListener {
        final List<String> collected = new ArrayList<>();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                int charPositionInLine, String msg, RecognitionException e) {
            collected.add(String.format("Line %d:%d - %s", line, charPositionInLine, msg));
        }
    }

    private CollectingErrors attachListeners() {
        return new CollectingErrors();
    }

    // ==================== companions ====================
    private void copyCompanion(Path source, String name) throws IOException {
        Path dest = Paths.get(OUTPUT_DIR).resolve(name);
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        log.info("Copied companion file " + name + " (unmodified).");
    }

    private void copyCompanionIfExists(String name) throws IOException {
        Path found = findInProject(projectRoot, name);
        if (found == null) {
            log.warn("Companion file '" + name + "' not found in project; nothing copied.");
            return;
        }
        Path dest = Paths.get(OUTPUT_DIR).resolve(name);
        Files.copy(found, dest, StandardCopyOption.REPLACE_EXISTING);
        log.info("Copied companion file " + name + " from " + projectRoot.relativize(found) + " (unmodified).");
    }

    private Path findInProject(Path root, String name) throws IOException {
        try (Stream<Path> walk = Files.walk(root)) {
            return walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase(name))
                    .filter(p -> !within(p, root.resolve(OUTPUT_DIR))
                            && !within(p, root.resolve(COMPILER_OUTPUT_DIR)))
                    .sorted(Comparator.comparingInt(p -> p.getNameCount()))
                    .findFirst()
                    .orElse(null);
        }
    }

    private static boolean within(Path candidate, Path dir) {
        return candidate.normalize().startsWith(dir.normalize());
    }

    // ==================== logging ====================
    private void finishLog(long startMs) throws IOException {
        log.section("SUMMARY");
        log.info("Total time: " + (System.currentTimeMillis() - startMs) + " ms");
        log.writeTo(Paths.get(COMPILER_OUTPUT_DIR).resolve("generation_log.txt"));
        System.out.println(TEAL + "  Log written to " + COMPILER_OUTPUT_DIR + "/generation_log.txt" + RESET);
    }

    /** Internal pairing of a captured request with its parsed template. */
    private static final class RTRenderRequestLite {
        final antlr.generator.render.RTRenderRequest request;
        final TemplateNode template;

        RTRenderRequestLite(antlr.generator.render.RTRenderRequest request, TemplateNode template) {
            this.request = request;
            this.template = template;
        }
    }
}
