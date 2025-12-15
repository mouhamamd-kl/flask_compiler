import antlr.ProgLangEvaluator;
import antlr.ast.node.ASTNode;
import antlr.pythonLexer;
import antlr.pythonParser;
import antlr.symbol.SymbolTable;
import antlr.visitor.ASTBuilder;
import antlr.visitor.ASTPrinter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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


import antlr.pythonLexer;
import antlr.pythonParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

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

    public static void main(String[] args) {
        String testsDir = "tests";

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
            Files.list(Paths.get(testsDir))
                .filter(path -> path.toString().endsWith(".py"))
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
            System.out.println("\n" + "═".repeat(70));
            System.out.println("  COMPILING: " + fileName);
            System.out.println("═".repeat(70));

            String sourceCode = Files.readString(filePath);
            
            // عرض الكود المصدري
            System.out.println("\n📄 Source Code:");
            System.out.println("─".repeat(40));
            System.out.println(sourceCode);
            System.out.println("─".repeat(40));
            
            // بدء الترجمة
            CompilationResult result = compileSource(sourceCode);
            
            // عرض النتائج
            if (result.success) {
                System.out.println("\n✅ Compilation successful!");
            } else {
                System.out.println("\n❌ Compilation failed!");
                for (String error : result.errors) {
                    System.out.println("   Error: " + error);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * ترجمة الكود المصدري
     */
    public static CompilationResult compileSource(String sourceCode) {
        CompilationResult result = new CompilationResult();
        
        try {
            // ==================== Phase 1: Lexical Analysis ====================
            System.out.println("\n🔍 Phase 1: Lexical Analysis...");
            CharStream input = CharStreams.fromString(sourceCode);
            pythonLexer lexer = new pythonLexer(input);
            
            // جمع الأخطاء المعجمية
            lexer.removeErrorListeners();
            lexer.addErrorListener(new CompilerErrorListener(result));
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // عرض الـ Tokens
            tokens.fill();
            System.out.println("\n   Tokens:");
            for (Token token : tokens.getTokens()) {
                if (token.getType() != Token.EOF) {
                    String tokenName = pythonLexer.VOCABULARY.getSymbolicName(token.getType());
                    System.out.printf("   [%-15s] '%s' (Line %d, Col %d)%n",
                        tokenName, 
                        token.getText().replace("\n", "\\n"),
                        token.getLine(),
                        token.getCharPositionInLine());
                }
            }
            
            // ==================== Phase 2: Syntax Analysis ====================
            System.out.println("\n🔍 Phase 2: Syntax Analysis (Parsing)...");
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
            System.out.println("\n🔍 Phase 3: Building AST...");
            ASTBuilder builder = new ASTBuilder();
            ASTNode ast = builder.visit(parseTree);
            result.ast = ast;
            result.symbolTable = builder.getSymbolTable();
            
            // ==================== Phase 4: Print AST ====================
            System.out.println("\n🌳 Abstract Syntax Tree:");
            ASTPrinter printer = new ASTPrinter();
            printer.print(ast);
            
            // ==================== Phase 5: Symbol Table ====================
            System.out.println("\n📋 Symbol Table:");
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