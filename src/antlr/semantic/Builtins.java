package antlr.semantic;

import antlr.symbol.Symbol;
import antlr.symbol.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * الدوال المدمجة في Python والتي لا تحتاج إلى تعريف أو استيراد
 * تُعرّف كرموز FUNCTION في النطاق العام قبل بناء الشجرة
 */
public final class Builtins {

    // التحويل بين الأنواع والعمليات الحسابية
    private static final List<String> CONVERSIONS = List.of(
            "int", "float", "str", "bool", "abs", "round", "isinstance", "type");

    // المجموعات
    private static final List<String> COLLECTIONS = List.of(
            "list", "dict", "set", "tuple", "len", "max", "min",
            "sum", "sorted", "reversed");

    // أدوات التكرار
    private static final List<String> ITERATION = List.of(
            "range", "enumerate", "zip", "map", "filter");

    // الإدخال والإخراج ومتفرقات
    private static final List<String> MISC = List.of(
            "print", "input", "id", "hash", "repr");

    private static final List<String> ALL;

    static {
        List<String> all = new ArrayList<>();
        all.addAll(CONVERSIONS);
        all.addAll(COLLECTIONS);
        all.addAll(ITERATION);
        all.addAll(MISC);
        ALL = List.copyOf(all);
    }

    private Builtins() {
    }

    /**
     * تعريف جميع الدوال المدمجة كرموز في النطاق المحدد (العام)
     */
    public static void defineIn(SymbolTable scope) {
        for (String name : ALL) {
            scope.define(name, Symbol.SymbolType.FUNCTION, 0, 0);
        }
    }

    /**
     * التحقق مما إذا كان الاسم دالة مدمجة
     */
    public static boolean contains(String name) {
        return ALL.contains(name);
    }
}
