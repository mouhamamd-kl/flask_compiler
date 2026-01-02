parser grammar jinja2Parser;

options {
    tokenVocab = jinja2Lexer;
}

@header {
   package antlr.gen.jinja2;
}

// ==================== ROOT ====================
template        : templateContent* EOF;

templateContent : htmlText                    # HtmlTextPart
                | expressionBlock             # ExpressionOutput
                | forBlock                    # ForLoopBlock
                | ifBlock                     # IfConditionBlock
                | extendsStatement            # ExtendsStmt
                | includeStatement            # IncludeStmt
                | blockDefinition             # BlockDefStmt
                | setStatement                # SetStmt
                | setBlockStatement           # SetBlockStmt
                | macroStatement              # MacroDefStmt
                | importStatement             # ImportStmt
                | fromImportStatement         # FromImportStmt
                | withBlock                   # WithScopeBlock
                | filterBlock                 # FilterApplyBlock
                | doStatement                 # DoStmt
                | callBlock                   # CallMacroBlock
                | autoescapeBlock             # AutoescapeStmt
                ;

// ==================== HTML TEXT ====================
htmlText        : HTML_TEXT+;

// ==================== EXPRESSION OUTPUT {{ ... }} ====================
expressionBlock : (EXPR_OPEN | EXPR_OPEN_TRIM) expr=expression (PIPE filter)* (EXPR_CLOSE | EXPR_CLOSE_TRIM);

filter          : name=NAME (LPAREN argList? RPAREN)?;

// ==================== EXPRESSIONS (Precedence: lowest → highest) ====================

// Entry point for expressions
expression      : ternaryExpr;

// Ternary: value if condition else other  (lowest precedence)
ternaryExpr     : value=orExpr (IF condition=orExpr ELSE elseValue=orExpr)?;

// Logical OR
orExpr          : left=andExpr (OR right=andExpr)*;

// Logical AND
andExpr         : left=notExpr (AND right=notExpr)*;

// Logical NOT
notExpr         : NOT operand=notExpr                           # NotExpression
                | comparisonExpr                                # NotPassThrough
                ;

// Comparison: ==, !=, <, >, <=, >=, in, not in, is, is not
comparisonExpr  : left=additiveExpr (compOp right=additiveExpr)?           # CompareExpression
                | additiveExpr testOp                           # TestExpression
                ;

compOp          : EQ                                            # EqOp
                | NEQ                                           # NeqOp
                | LT                                            # LtOp
                | GT                                            # GtOp
                | LTE                                           # LteOp
                | GTE                                           # GteOp
                | IN                                            # InOp
                | NOT IN                                        # NotInOp
                ;

// Test operators: is defined, is not none, etc.
testOp          : IS NOT? testName (LPAREN argList? RPAREN)?;
testName        : NAME;  // defined, none, iterable, number, string, etc.

// Addition, subtraction, string concatenation (~)
additiveExpr    : multiplicativeExpr ((op+=(PLUS | MINUS | TILDE)) multiplicativeExpr)*;

// Multiplication, division, floor division, modulo
multiplicativeExpr : powerExpr ((op+=(STAR | SLASH | FLOORDIV | MOD)) powerExpr)*;

// Power (right-associative)
powerExpr       : base=unaryExpr (POW exponent=powerExpr)?;

// Unary: -x, +x
unaryExpr       : op=(MINUS | PLUS) operand=unaryExpr           # UnaryExpression
                | postfixExpr                                   # UnaryPassThrough
                ;

// ==================== POSTFIX EXPRESSIONS (Highest Precedence) ====================
// Handles: a.b, a[i], a[1:3], a()
postfixExpr     : object=primaryExpr ops+=postfixOp*;

postfixOp       : DOT name=NAME                                 # DotAccessOp
                | LBRACK index=expression RBRACK                # IndexAccessOp
                | LBRACK start=expression? COLON stop=expression? (COLON step=expression?)? RBRACK  # SliceAccessOp
                | LPAREN args=argList? RPAREN                   # CallOp
                ;

// ==================== PRIMARY EXPRESSIONS (Atoms) ====================
primaryExpr     : NUMBER                                        # NumberLiteral
                | STRING                                        # StringLiteral
                | TRUE                                          # TrueLiteral
                | FALSE                                         # FalseLiteral
                | NONE                                          # NoneLiteral
                | NAME                                          # VariableExpr
                | list                                          # ListExpr
                | dict                                          # DictExpr
                | LPAREN expression RPAREN                      # ParenExpr
                ;

// ==================== DATA STRUCTURES ====================

// List literal: [1, 2, 3] or [1, 2, 3,] (trailing comma allowed)
list            : LBRACK (elements+=expression (COMMA elements+=expression)* COMMA?)? RBRACK;

// Dictionary literal: {"key": value} or {key: value}
dict            : LBRACE (dictEntry (COMMA dictEntry)* COMMA?)? RBRACE;
dictEntry       : key=expression COLON value=expression;

// Argument list (positional and keyword)
argList         : argument (COMMA argument)*;
argument        : (name=NAME ASSIGN)? value=expression;

// ==================== FOR LOOP {% for ... %} ====================
forBlock        : forStart body+=templateContent* elseForBranch? forEnd;

forStart        : (STMT_OPEN | STMT_OPEN_TRIM) FOR target=forTarget IN iterable=expression
                  (IF condition=expression)? (STMT_CLOSE | STMT_CLOSE_TRIM);

// For target can be single name or tuple unpacking
forTarget       : NAME                                          # SingleTarget
                | NAME (COMMA NAME)+                            # TupleTarget
                ;

elseForBranch   : (STMT_OPEN | STMT_OPEN_TRIM) ELSE (STMT_CLOSE | STMT_CLOSE_TRIM) body+=templateContent*;

forEnd          : (STMT_OPEN | STMT_OPEN_TRIM) ENDFOR (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== IF STATEMENT {% if ... %} ====================
ifBlock         : ifStart body+=templateContent* elifBranch* elseBranch? ifEnd;

ifStart         : (STMT_OPEN | STMT_OPEN_TRIM) IF condition=expression (STMT_CLOSE | STMT_CLOSE_TRIM);

elifBranch      : (STMT_OPEN | STMT_OPEN_TRIM) ELIF condition=expression (STMT_CLOSE | STMT_CLOSE_TRIM) body+=templateContent*;

elseBranch      : (STMT_OPEN | STMT_OPEN_TRIM) ELSE (STMT_CLOSE | STMT_CLOSE_TRIM) body+=templateContent*;

ifEnd           : (STMT_OPEN | STMT_OPEN_TRIM) ENDIF (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== TEMPLATE INHERITANCE ====================
extendsStatement: (STMT_OPEN | STMT_OPEN_TRIM) EXTENDS templateName=STRING (STMT_CLOSE | STMT_CLOSE_TRIM);

includeStatement: (STMT_OPEN | STMT_OPEN_TRIM) INCLUDE templateName=STRING
                  (ignoreMissing=IGNORE MISSING)?
                  (contextMode=(WITH | WITHOUT) CONTEXT)?
                  (STMT_CLOSE | STMT_CLOSE_TRIM);

blockDefinition : (STMT_OPEN | STMT_OPEN_TRIM) BLOCK name=NAME scoped=SCOPED? (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDBLOCK NAME? (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== SET STATEMENT ====================
setTarget       : name=NAME                                     # SimpleTarget
                | first=NAME (COMMA rest+=NAME)+                # TupleSetTarget
                | namespace=NAME (DOT attributes+=NAME)+        # NamespaceTarget
                ;

setStatement    : (STMT_OPEN | STMT_OPEN_TRIM) SET setTarget ASSIGN value=expression
                  (PIPE filter)* (STMT_CLOSE | STMT_CLOSE_TRIM);

setBlockStatement: (STMT_OPEN | STMT_OPEN_TRIM) SET name=NAME (STMT_CLOSE | STMT_CLOSE_TRIM)
                   body+=templateContent*
                   (STMT_OPEN | STMT_OPEN_TRIM) ENDSET (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== MACRO STATEMENT ====================
macroStatement  : (STMT_OPEN | STMT_OPEN_TRIM) MACRO name=NAME LPAREN params=macroParams? RPAREN (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDMACRO (STMT_CLOSE | STMT_CLOSE_TRIM);

macroParams     : macroParam (COMMA macroParam)*;
macroParam      : name=NAME (ASSIGN defaultValue=expression)?;

// ==================== CALL BLOCK ====================
callBlock       : (STMT_OPEN | STMT_OPEN_TRIM) CALL (LPAREN callArgs=argList? RPAREN)? callee=expression (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDCALL (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== IMPORT STATEMENTS ====================
importStatement : (STMT_OPEN | STMT_OPEN_TRIM) IMPORT templateName=STRING AS alias=NAME
                  (contextMode=(WITH | WITHOUT) CONTEXT)?
                  (STMT_CLOSE | STMT_CLOSE_TRIM);

fromImportStatement: (STMT_OPEN | STMT_OPEN_TRIM) FROM templateName=STRING IMPORT imports=importList
                     (contextMode=(WITH | WITHOUT) CONTEXT)?
                     (STMT_CLOSE | STMT_CLOSE_TRIM);

importList      : importItem (COMMA importItem)*;
importItem      : name=NAME (AS alias=NAME)?;

// ==================== WITH BLOCK ====================
withBlock       : (STMT_OPEN | STMT_OPEN_TRIM) WITH assignments (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDWITH (STMT_CLOSE | STMT_CLOSE_TRIM);

assignments     : withAssignment (COMMA withAssignment)*;
withAssignment  : name=NAME ASSIGN value=expression;

// ==================== FILTER BLOCK ====================
filterBlock     : (STMT_OPEN | STMT_OPEN_TRIM) FILTER filterName=NAME (LPAREN args=argList? RPAREN)? (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDFILTER (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== DO STATEMENT ====================
doStatement     : (STMT_OPEN | STMT_OPEN_TRIM) DO expr=expression (STMT_CLOSE | STMT_CLOSE_TRIM);

// ==================== AUTOESCAPE BLOCK ====================
autoescapeBlock : (STMT_OPEN | STMT_OPEN_TRIM) AUTOESCAPE escapeMode=(TRUE | FALSE | STRING) (STMT_CLOSE | STMT_CLOSE_TRIM)
                  body+=templateContent*
                  (STMT_OPEN | STMT_OPEN_TRIM) ENDAUTOESCAPE (STMT_CLOSE | STMT_CLOSE_TRIM);
