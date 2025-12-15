package antlr.symbol;

import java.util.*;

/**
 * جدول الرموز (Symbol Table)
 * يدعم النطاقات المتداخلة (Nested Scopes)
 *
 * المتطلبات المحققة:
 * - بنية مناسبة لتخزين الرموز
 * - توابع مساعدة للتحكم بالعمليات على الجدول
 * - دعم النطاقات المتداخلة
 */
public class SymbolTable {

    // اسم النطاق الحالي
    private final String scopeName;

    // مستوى التداخل
    private final int scopeLevel;

    // النطاق الأب (null للنطاق العام)
    private final SymbolTable parent;

    // الرموز في هذا النطاق
    private final Map<String, Symbol> symbols;

    // النطاقات الفرعية
    private final List<SymbolTable> children;

    /**
     * إنشاء جدول رموز جديد (النطاق العام)
     */
    public SymbolTable() {
        this("global", 0, null);
    }

    /**
     * إنشاء جدول رموز مع نطاق أب
     */
    public SymbolTable(String scopeName, int scopeLevel, SymbolTable parent) {
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.parent = parent;
        this.symbols = new LinkedHashMap<>();  // للحفاظ على الترتيب
        this.children = new ArrayList<>();
    }

    // ==================== عمليات إدارة الرموز ====================

    /**
     * تعريف رمز جديد في النطاق الحالي
     * @return true إذا تم التعريف بنجاح، false إذا كان موجوداً
     */
    public boolean define(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            return false;  // الرمز موجود مسبقاً
        }
        symbols.put(symbol.getName(), symbol);
        return true;
    }

    /**
     * تعريف رمز جديد (طريقة مختصرة)
     */
    public boolean define(String name, Symbol.SymbolType type, int line, int col) {
        return define(new Symbol(name, type, line, col));
    }

    /**
     * البحث عن رمز في النطاق الحالي فقط
     */
    public Symbol lookupLocal(String name) {
        return symbols.get(name);
    }

    /**
     * البحث عن رمز في النطاق الحالي والنطاقات الأعلى
     */
    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        }
        // البحث في النطاق الأب
        if (parent != null) {
            return parent.resolve(name);
        }
        return null;
    }

    /**
     * التحقق من وجود رمز في النطاق الحالي
     */
    public boolean isDefinedLocally(String name) {
        return symbols.containsKey(name);
    }

    /**
     * التحقق من وجود رمز في أي نطاق
     */
    public boolean isDefined(String name) {
        return resolve(name) != null;
    }

    /**
     * تحديث قيمة رمز
     */
    public boolean update(String name, Object value) {
        Symbol symbol = resolve(name);
        if (symbol != null) {
            symbol.setValue(value);
            return true;
        }
        return false;
    }

    /**
     * حذف رمز من النطاق الحالي
     */
    public boolean remove(String name) {
        return symbols.remove(name) != null;
    }

    // ==================== إدارة النطاقات ====================

    /**
     * الدخول إلى نطاق جديد
     */
    public SymbolTable enterScope(String name) {
        SymbolTable child = new SymbolTable(name, scopeLevel + 1, this);
        children.add(child);
        return child;
    }

    /**
     * الخروج من النطاق الحالي
     */
    public SymbolTable exitScope() {
        return parent;
    }

    /**
     * الحصول على النطاق الأب
     */
    public SymbolTable getParent() {
        return parent;
    }

    /**
     * الحصول على النطاق العام (الجذر)
     */
    public SymbolTable getGlobalScope() {
        SymbolTable current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    // ==================== Getters ====================

    public String getScopeName() {
        return scopeName;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public Collection<Symbol> getSymbols() {
        return Collections.unmodifiableCollection(symbols.values());
    }

    public List<SymbolTable> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public int getSymbolCount() {
        return symbols.size();
    }

    // ==================== طباعة الجدول ====================

    /**
     * طباعة جدول الرموز
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * طباعة جدول الرموز مع جميع النطاقات الفرعية
     */
    public void printAll() {
        print();
        for (SymbolTable child : children) {
            child.printAll();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(scopeLevel);

        // عنوان الجدول
        sb.append("\n").append(indent);
        sb.append("╔══════════════════════════════════════════════════════════════╗\n");
        sb.append(indent);
        sb.append(String.format("║  Symbol Table: %-45s ║\n", scopeName + " (Level " + scopeLevel + ")"));
        sb.append(indent);
        sb.append("╠══════════════════════════════════════════════════════════════╣\n");

        if (symbols.isEmpty()) {
            sb.append(indent);
            sb.append("║  (empty)                                                     ║\n");
        } else {
            // عناوين الأعمدة
            sb.append(indent);
            sb.append(String.format("║  %-12s %-15s %-8s %-8s %-10s ║\n",
                "Name", "Type", "Line", "Col", "Value"));
            sb.append(indent);
            sb.append("║──────────────────────────────────────────────────────────────║\n");

            // الرموز
            for (Symbol symbol : symbols.values()) {
                sb.append(indent);
                String valueStr = symbol.getValue() != null ?
                    symbol.getValue().toString() : "-";
                if (valueStr.length() > 10) {
                    valueStr = valueStr.substring(0, 7) + "...";
                }
                sb.append(String.format("║  %-12s %-15s %-8d %-8d %-10s ║\n",
                    truncate(symbol.getName(), 12),
                    symbol.getType(),
                    symbol.getLineNumber(),
                    symbol.getColumnNumber(),
                    valueStr));
            }
        }

        sb.append(indent);
        sb.append("╚══════════════════════════════════════════════════════════════╝\n");

        return sb.toString();
    }

    private String truncate(String str, int maxLen) {
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }

    // ==================== إحصائيات ====================

    /**
     * الحصول على جميع الرموز من جميع النطاقات
     */
    public List<Symbol> getAllSymbols() {
        List<Symbol> all = new ArrayList<>(symbols.values());
        for (SymbolTable child : children) {
            all.addAll(child.getAllSymbols());
        }
        return all;
    }

    /**
     * الحصول على عدد الرموز الكلي
     */
    public int getTotalSymbolCount() {
        int count = symbols.size();
        for (SymbolTable child : children) {
            count += child.getTotalSymbolCount();
        }
        return count;
    }
}
