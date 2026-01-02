import antlr.ast.node.ASTNode;
import antlr.symbol.SymbolTable;
import antlr.visitor.ASTBuilder;
import antlr.visitor.ASTPrinter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


// public class Main {

//     public static void main(String[] args) {
//         String testsDir = "tests";

//         // Run all tests or specific test from command line
//         if (args.length > 0) {
//             // Run specific test file
//             runTest(args[0]);
//         } else {
//             // Run all tests in the tests folder
//             runAllTests(testsDir);
//         }
//     }

//     public static void runAllTests(String testsDir) {
//         try {
//             Files.list(Paths.get(testsDir))
//                 .filter(path -> path.toString().endsWith(".py"))
//                 .sorted()
//                 .forEach(Main::runTest);
//         } catch (IOException e) {
//             System.err.println("Error reading tests directory: " + e.getMessage());
//         }
//     }

//     public static void runTest(String filePath) {
//         runTest(Paths.get(filePath));
//     }

//     public static void runTest(Path filePath) {
//         try {
//             String fileName = filePath.getFileName().toString();
//             System.out.println("=== Running: " + fileName + " ===");

//             String program = Files.readString(filePath);
//             evaluate(program);

//             System.out.println();
//         } catch (IOException e) {
//             System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
//         }
//     }

//     public static void evaluate(String program) {
//         ProgLangEvaluator visitor = new ProgLangEvaluator();
//         CharStream input = CharStreams.fromString(program);
//         pythonLexer lexer = new pythonLexer(input);
//         CommonTokenStream tokens = new CommonTokenStream(lexer);
//         pythonParser parser = new pythonParser(tokens);
//         ParseTree tree = parser.root();
//         visitor.visit(tree);
//     }
// }


import antlr.gen.python.pythonLexer;
import antlr.gen.python.pythonParser;
import antlr.gen.jinja2.jinja2Lexer;
import antlr.gen.jinja2.jinja2Parser;
import antlr.visitor.JinjaASTBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * نقطة الدخول الرئيسية للمترجم
 * 
 * خطوات الترجمة:
 * 1. Lexical Analysis (Lexer) - تحليل معجمي
 * 2. Syntax Analysis (Parser) - تحليل نحوي
 * 3. AST Building - بناء الشجرة المجردة
 * 4. Semantic Analysis - تحليل دلالي
 * 5. Code Generation - توليد الكود
 */
public class Main {

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

    public static void main(String[] args) {
        String testsDir = "tests/flask";

        if (args.length > 0) {
            // تشغيل ملف محدد
            compile(args[0]);
        } else {
            // تشغيل جميع الاختبارات
            runAllTests(testsDir);
        }
    }
    public static void runAllTests(String testsDir) {
        try {
            Files.walk(Paths.get(testsDir))
                .filter(Files::isRegularFile)
                .filter(path -> {
                    String name = path.toString().toLowerCase();
                    return name.endsWith(".py") || name.endsWith(".html");
                })
                .sorted()
                .forEach(Main::compile);
        } catch (IOException e) {
            System.err.println("Error reading tests directory: " + e.getMessage());
        }
    }

    public static void compile(String filePath) {
        compile(Paths.get(filePath));
    }

    public static void compile(Path filePath) {
        try {
            String fileName = filePath.getFileName().toString();
            String filePathStr = filePath.toString().toLowerCase();

            // Determine file type
            String fileType = filePathStr.endsWith(".py") ? "Python" :
                              filePathStr.endsWith(".html") ? "Jinja2" : "Unknown";

            // Header with colors
            System.out.println("\n" + TEAL + "═".repeat(70) + RESET);
            System.out.println(TEAL + "  " + BOLD + "COMPILING (" + fileType + "): " + LIGHT + fileName + RESET);
            System.out.println(TEAL + "═".repeat(70) + RESET);

            String sourceCode = Files.readString(filePath);

            // عرض الكود المصدري
            System.out.println("\n" + TEAL + "📄 Source Code:" + RESET);
            System.out.println(GRAY + "─".repeat(40) + RESET);
            System.out.println(LIGHT + sourceCode + RESET);
            System.out.println(GRAY + "─".repeat(40) + RESET);

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

            // عرض النتائج
            if (result.success) {
                System.out.println("\n" + TEAL + "✅ Compilation successful!" + RESET);
            } else {
                System.out.println("\n" + RED + "❌ Compilation failed!" + RESET);
                for (String error : result.errors) {
                    System.out.println(RED + "   Error: " + error + RESET);
                }
            }

        } catch (IOException e) {
            System.err.println(RED + "Error reading file: " + filePath + " - " + e.getMessage() + RESET);
        }
    }

    /**
     * ترجمة كود Python
     */
    public static CompilationResult compilePythonSource(String sourceCode) {
        CompilationResult result = new CompilationResult();
        
        try {
            // ==================== Phase 1: Lexical Analysis ====================
            System.out.println("\n" + TEAL + "🔍 Phase 1: " + LIGHT + "Lexical Analysis..." + RESET);
            CharStream input = CharStreams.fromString(sourceCode);
            pythonLexer lexer = new pythonLexer(input);

            // جمع الأخطاء المعجمية
            lexer.removeErrorListeners();
            lexer.addErrorListener(new CompilerErrorListener(result));

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // عرض الـ Tokens
            tokens.fill();
            System.out.println("\n" + TEAL + "   Tokens:" + RESET);
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
                    System.out.printf("   " + LIGHT + "[%-15s]" + RESET + " " + TEAL + "'%s'" + RESET + " " + GRAY + "(Line %d, Col %d)" + RESET + "%n",
                        tokenName,
                        token.getText().replace("\n", "newline"),
                        token.getLine(),
                        token.getCharPositionInLine());
                }
            }

            // ==================== Phase 2: Syntax Analysis ====================
            System.out.println("\n" + TEAL + "🔍 Phase 2: " + LIGHT + "Syntax Analysis (Parsing)..." + RESET);
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
            System.out.println("\n" + TEAL + "🔍 Phase 3: " + LIGHT + "Building AST..." + RESET);
            ASTBuilder builder = new ASTBuilder();
            ASTNode ast = builder.visit(parseTree);
            result.ast = ast;
            result.symbolTable = builder.getSymbolTable();

            // ==================== Phase 4: Print AST ====================
            System.out.println("\n" + TEAL + "🌳 Abstract Syntax Tree:" + RESET);
            ASTPrinter printer = new ASTPrinter();
            printer.print(ast);

            // ==================== Phase 5: Symbol Table ====================
            System.out.println("\n" + TEAL + "📋 Symbol Table:" + RESET);
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
    public static CompilationResult compileJinjaSource(String sourceCode) {
        CompilationResult result = new CompilationResult();

        try {
            // ==================== Phase 1: Lexical Analysis ====================
            System.out.println("\n" + TEAL + "🔍 Phase 1: " + LIGHT + "Lexical Analysis..." + RESET);
            CharStream input = CharStreams.fromString(sourceCode);
            jinja2Lexer lexer = new jinja2Lexer(input);

            // جمع الأخطاء المعجمية
            lexer.removeErrorListeners();
            lexer.addErrorListener(new CompilerErrorListener(result));

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // عرض الـ Tokens
            tokens.fill();
            System.out.println("\n" + TEAL + "   Tokens:" + RESET);
            for (Token token : tokens.getTokens()) {
                if (token.getType() != Token.EOF) {
                    String tokenName = jinja2Lexer.VOCABULARY.getSymbolicName(token.getType());
                    if (tokenName == null) {
                        tokenName = "UNKNOWN_" + token.getType();
                    }
                    System.out.printf("   " + LIGHT + "[%-15s]" + RESET + " " + TEAL + "'%s'" + RESET + " " + GRAY + "(Line %d, Col %d)" + RESET + "%n",
                        tokenName,
                        token.getText().replace("\n", "\\n").replace("\r", "\\r"),
                        token.getLine(),
                        token.getCharPositionInLine());
                }
            }

            // ==================== Phase 2: Syntax Analysis ====================
            System.out.println("\n" + TEAL + "🔍 Phase 2: " + LIGHT + "Syntax Analysis (Parsing)..." + RESET);
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
            System.out.println("\n" + TEAL + "🔍 Phase 3: " + LIGHT + "Building AST..." + RESET);
            JinjaASTBuilder builder = new JinjaASTBuilder();
            ASTNode ast = builder.visit(parseTree);
            result.ast = ast;

            // ==================== Phase 4: Print AST ====================
            System.out.println("\n" + TEAL + "🌳 Abstract Syntax Tree:" + RESET);
            ASTPrinter printer = new ASTPrinter();
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

    /**
     * نتيجة الترجمة
     */
    public static class CompilationResult {
        public boolean success = false;
        public ASTNode ast = null;
        public SymbolTable symbolTable = null;
        public java.util.List<String> errors = new java.util.ArrayList<>();
    }
    
    /**
     * مستمع الأخطاء
     */
    public static class CompilerErrorListener extends BaseErrorListener {
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