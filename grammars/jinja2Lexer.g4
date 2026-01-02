lexer grammar jinja2Lexer;

@header {
   package antlr.gen.jinja2;
}

// ==================== MODES ====================
// Default mode: HTML content
// JINJA_STMT mode: Inside {% ... %}
// JINJA_EXPR mode: Inside {{ ... }}

// ==================== DEFAULT MODE (HTML) ====================

// Whitespace control versions (must come before regular)
STMT_OPEN_TRIM  : '{%-' -> pushMode(JINJA_STMT);
EXPR_OPEN_TRIM  : '{{-' -> pushMode(JINJA_EXPR);

// Regular openers
STMT_OPEN       : '{%' -> pushMode(JINJA_STMT);
EXPR_OPEN       : '{{' -> pushMode(JINJA_EXPR);
COMMENT_OPEN    : '{#' -> pushMode(JINJA_COMMENT);

// Everything else is raw HTML text
HTML_TEXT       : ~[{]+ | '{' ~[%#{];

// ==================== JINJA STATEMENT MODE ====================
mode JINJA_STMT;

// Close tags (trim version must come first)
STMT_CLOSE_TRIM : '-%}' -> popMode;
STMT_CLOSE      : '%}' -> popMode;

// ==================== Keywords ====================
// Control flow
FOR             : 'for';
ENDFOR          : 'endfor';
IF              : 'if';
ELIF            : 'elif';
ELSE            : 'else';
ENDIF           : 'endif';

// Template inheritance
BLOCK           : 'block';
ENDBLOCK        : 'endblock';
EXTENDS         : 'extends';
INCLUDE         : 'include';

// Variables
SET             : 'set';
ENDSET          : 'endset';
WITH            : 'with';
ENDWITH         : 'endwith';

// Macros
MACRO           : 'macro';
ENDMACRO        : 'endmacro';
CALL            : 'call';
ENDCALL         : 'endcall';

// Imports
IMPORT          : 'import';
FROM            : 'from';
AS              : 'as';

// Filter/Raw blocks
FILTER          : 'filter';
ENDFILTER       : 'endfilter';
RAW             : 'raw';
ENDRAW          : 'endraw';

// Other statements
DO              : 'do';
AUTOESCAPE      : 'autoescape';
ENDAUTOESCAPE   : 'endautoescape';

// Include/Import modifiers
IGNORE          : 'ignore';
MISSING         : 'missing';
CONTEXT         : 'context';
WITHOUT         : 'without';
SCOPED          : 'scoped';

// ==================== Operators ====================
// Logical (Python-style)
AND             : 'and';
OR              : 'or';
NOT             : 'not';
IN              : 'in';
IS              : 'is';

// Comparison
EQ              : '==';
NEQ             : '!=';
LTE             : '<=';
GTE             : '>=';
LT              : '<';
GT              : '>';

// Arithmetic (POW must come before STAR)
POW             : '**';
PLUS            : '+';
MINUS           : '-';
STAR            : '*';
SLASH           : '/';
FLOORDIV        : '//';
MOD             : '%';
TILDE           : '~';

// Punctuation
DOT             : '.';
COMMA           : ',';
COLON           : ':';
PIPE            : '|';
ASSIGN          : '=';
LPAREN          : '(';
RPAREN          : ')';
LBRACK          : '[';
RBRACK          : ']';
LBRACE          : '{';
RBRACE          : '}';

// ==================== Literals ====================
TRUE            : 'true' | 'True';
FALSE           : 'false' | 'False';
NONE            : 'none' | 'None';

// String literals (double or single quoted)
STRING          : '"' (~["\r\n\\] | '\\' .)* '"'
                | '\'' (~['\r\n\\] | '\\' .)* '\'';

// Number literals (integer or float)
NUMBER          : [0-9]+ ('.' [0-9]+)?;

// Identifiers
NAME            : [a-zA-Z_][a-zA-Z0-9_]*;

// Whitespace (skip in statement mode)
STMT_WS         : [ \t\r\n]+ -> skip;

// ==================== JINJA EXPRESSION MODE ====================
mode JINJA_EXPR;

// Close tags (trim version must come first)
EXPR_CLOSE_TRIM : '-}}' -> popMode;
EXPR_CLOSE      : '}}' -> popMode;

// All tokens mirrored with type() to share with STMT mode
// Comparison (longer patterns first)
EXPR_EQ         : '==' -> type(EQ);
EXPR_NEQ        : '!=' -> type(NEQ);
EXPR_LTE        : '<=' -> type(LTE);
EXPR_GTE        : '>=' -> type(GTE);
EXPR_LT         : '<' -> type(LT);
EXPR_GT         : '>' -> type(GT);

// Arithmetic (POW must come before STAR)
EXPR_POW        : '**' -> type(POW);
EXPR_FLOORDIV   : '//' -> type(FLOORDIV);
EXPR_PLUS       : '+' -> type(PLUS);
EXPR_MINUS      : '-' -> type(MINUS);
EXPR_STAR       : '*' -> type(STAR);
EXPR_SLASH      : '/' -> type(SLASH);
EXPR_MOD        : '%' -> type(MOD);
EXPR_TILDE      : '~' -> type(TILDE);

// Punctuation
EXPR_DOT        : '.' -> type(DOT);
EXPR_COMMA      : ',' -> type(COMMA);
EXPR_COLON      : ':' -> type(COLON);
EXPR_PIPE       : '|' -> type(PIPE);
EXPR_ASSIGN     : '=' -> type(ASSIGN);
EXPR_LPAREN     : '(' -> type(LPAREN);
EXPR_RPAREN     : ')' -> type(RPAREN);
EXPR_LBRACK     : '[' -> type(LBRACK);
EXPR_RBRACK     : ']' -> type(RBRACK);
EXPR_LBRACE     : '{' -> type(LBRACE);
EXPR_RBRACE     : '}' -> type(RBRACE);

// Keywords (must check before NAME)
EXPR_AND        : 'and' -> type(AND);
EXPR_OR         : 'or' -> type(OR);
EXPR_NOT        : 'not' -> type(NOT);
EXPR_IN         : 'in' -> type(IN);
EXPR_IS         : 'is' -> type(IS);
EXPR_IF         : 'if' -> type(IF);
EXPR_ELSE       : 'else' -> type(ELSE);
EXPR_TRUE       : ('true' | 'True') -> type(TRUE);
EXPR_FALSE      : ('false' | 'False') -> type(FALSE);
EXPR_NONE       : ('none' | 'None') -> type(NONE);

// Literals
EXPR_STRING_DQ  : '"' (~["\r\n\\] | '\\' .)* '"' -> type(STRING);
EXPR_STRING_SQ  : '\'' (~['\r\n\\] | '\\' .)* '\'' -> type(STRING);
EXPR_NUMBER     : [0-9]+ ('.' [0-9]+)? -> type(NUMBER);

// Identifiers
EXPR_NAME       : [a-zA-Z_][a-zA-Z0-9_]* -> type(NAME);

// Whitespace
EXPR_WS         : [ \t\r\n]+ -> skip;

// ==================== JINJA COMMENT MODE ====================
mode JINJA_COMMENT;

COMMENT_CLOSE   : '#}' -> popMode;
COMMENT_TEXT    : . -> skip;
