lexer grammar pythonLexer;

@header{
   package antlr;
   import com.yuvalshavit.antlr4.DenterHelper;
}

@members {
  private final DenterHelper denter = DenterHelper.builder()
    .nl(NL)
    .indent(pythonParser.INDENT)
    .dedent(pythonParser.DEDENT)
    .pullToken(pythonLexer.super::nextToken);

  @Override
  public Token nextToken() {
    return denter.nextToken();
  }
}

// Numbers
NUM: [0-9]+;

// Arithmetic operators (POW before MUL to match ** before *)
POW: '**';
MUL: '*';
DIV: '/';
PLUS: '+';
SUB: '-';

// Comparison operators
EQ: '==';
NEQ: '!=';
LT: '<';
GT: '>';
LTE: '<=';
GTE: '>=';

// Boolean operators
AND: 'and';
OR: 'or';
NOT: 'not';

// Keywords (BEFORE NAME!)
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
PRINT: 'print';
TRUE: 'True';
FALSE: 'False';

// Assignment and punctuation
ASSIGN: '=';
COLON: ':';
LPAREN: '(';
RPAREN: ')';
COMMA:',';
LBRACK: '[';
RBRACK: ']';
LBRACE:'{';
RBRACE:'}';

STRING
    : '"' ( ~["\\] | '\\' . )* '"'   // double-quoted strings
    | '\'' ( ~['\\] | '\\' . )* '\'' // single-quoted strings
    ;


// Identifiers (LAST!)
NAME: [a-zA-Z_][a-zA-Z0-9_]*;

// Newline with trailing spaces (for indentation detection)
NL: ('\r'? '\n' ' '*);

// Skip spaces and tabs within lines
WS: [ \t]+ -> skip;
