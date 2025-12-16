// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/pythonParser.g4 by ANTLR 4.13.2

   package antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class pythonParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, POW=2, MUL=3, DIV=4, PLUS=5, SUB=6, EQ=7, NEQ=8, LT=9, GT=10, LTE=11, 
		GTE=12, AND=13, OR=14, NOT=15, IF=16, ELIF=17, ELSE=18, PRINT=19, TRUE=20, 
		FALSE=21, ASSIGN=22, COLON=23, LPAREN=24, RPAREN=25, COMMA=26, LBRACK=27, 
		RBRACK=28, LBRACE=29, RBRACE=30, STRING=31, NAME=32, NL=33, WS=34, INDENT=35, 
		DEDENT=36;
	public static final int
		RULE_root = 0, RULE_statement = 1, RULE_action = 2, RULE_ifStatement = 3, 
		RULE_block = 4, RULE_condition = 5, RULE_comparisonOp = 6, RULE_list = 7, 
		RULE_dict = 8, RULE_dictEntry = 9, RULE_expr = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "statement", "action", "ifStatement", "block", "condition", "comparisonOp", 
			"list", "dict", "dictEntry", "expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'**'", "'*'", "'/'", "'+'", "'-'", "'=='", "'!='", "'<'", 
			"'>'", "'<='", "'>='", "'and'", "'or'", "'not'", "'if'", "'elif'", "'else'", 
			"'print'", "'True'", "'False'", "'='", "':'", "'('", "')'", "','", "'['", 
			"']'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NUM", "POW", "MUL", "DIV", "PLUS", "SUB", "EQ", "NEQ", "LT", "GT", 
			"LTE", "GTE", "AND", "OR", "NOT", "IF", "ELIF", "ELSE", "PRINT", "TRUE", 
			"FALSE", "ASSIGN", "COLON", "LPAREN", "RPAREN", "COMMA", "LBRACK", "RBRACK", 
			"LBRACE", "RBRACE", "STRING", "NAME", "NL", "WS", "INDENT", "DEDENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "pythonParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public pythonParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RootContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(pythonParser.EOF, 0); }
		public List<TerminalNode> NL() { return getTokens(pythonParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(pythonParser.NL, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitRoot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(22);
				match(NL);
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4295557120L) != 0)) {
				{
				{
				setState(28);
				statement();
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(29);
					match(NL);
					}
					}
					setState(34);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				action();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				ifStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionContext extends ParserRuleContext {
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
	 
		public ActionContext() { }
		public void copyFrom(ActionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignActionContext extends ActionContext {
		public Token var;
		public Token op;
		public ExprContext value;
		public TerminalNode NAME() { return getToken(pythonParser.NAME, 0); }
		public TerminalNode ASSIGN() { return getToken(pythonParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignActionContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterAssignAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitAssignAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitAssignAction(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintActionContext extends ActionContext {
		public TerminalNode PRINT() { return getToken(pythonParser.PRINT, 0); }
		public TerminalNode LPAREN() { return getToken(pythonParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(pythonParser.RPAREN, 0); }
		public PrintActionContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterPrintAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitPrintAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitPrintAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_action);
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				_localctx = new AssignActionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(46);
				((AssignActionContext)_localctx).var = match(NAME);
				setState(47);
				((AssignActionContext)_localctx).op = match(ASSIGN);
				setState(48);
				((AssignActionContext)_localctx).value = expr(0);
				}
				break;
			case PRINT:
				_localctx = new PrintActionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				match(PRINT);
				setState(50);
				match(LPAREN);
				setState(51);
				expr(0);
				setState(52);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(pythonParser.IF, 0); }
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(pythonParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(pythonParser.COLON, i);
		}
		public List<TerminalNode> INDENT() { return getTokens(pythonParser.INDENT); }
		public TerminalNode INDENT(int i) {
			return getToken(pythonParser.INDENT, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> DEDENT() { return getTokens(pythonParser.DEDENT); }
		public TerminalNode DEDENT(int i) {
			return getToken(pythonParser.DEDENT, i);
		}
		public List<TerminalNode> ELIF() { return getTokens(pythonParser.ELIF); }
		public TerminalNode ELIF(int i) {
			return getToken(pythonParser.ELIF, i);
		}
		public TerminalNode ELSE() { return getToken(pythonParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(IF);
			setState(57);
			condition(0);
			setState(58);
			match(COLON);
			setState(59);
			match(INDENT);
			setState(60);
			block();
			setState(61);
			match(DEDENT);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELIF) {
				{
				{
				setState(62);
				match(ELIF);
				setState(63);
				condition(0);
				setState(64);
				match(COLON);
				setState(65);
				match(INDENT);
				setState(66);
				block();
				setState(67);
				match(DEDENT);
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(74);
				match(ELSE);
				setState(75);
				match(COLON);
				setState(76);
				match(INDENT);
				setState(77);
				block();
				setState(78);
				match(DEDENT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(pythonParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(pythonParser.NL, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(82);
				statement();
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(83);
					match(NL);
					}
					}
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(91); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 4295557120L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotConditionContext extends ConditionContext {
		public TerminalNode NOT() { return getToken(pythonParser.NOT, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public NotConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterNotCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitNotCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitNotCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenConditionContext extends ConditionContext {
		public TerminalNode LPAREN() { return getToken(pythonParser.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(pythonParser.RPAREN, 0); }
		public ParenConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterParenCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitParenCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitParenCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseConditionContext extends ConditionContext {
		public TerminalNode FALSE() { return getToken(pythonParser.FALSE, 0); }
		public FalseConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterFalseCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitFalseCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitFalseCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareConditionContext extends ConditionContext {
		public ExprContext left;
		public ComparisonOpContext op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ComparisonOpContext comparisonOp() {
			return getRuleContext(ComparisonOpContext.class,0);
		}
		public CompareConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterCompareCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitCompareCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitCompareCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueConditionContext extends ConditionContext {
		public TerminalNode TRUE() { return getToken(pythonParser.TRUE, 0); }
		public TrueConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterTrueCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitTrueCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitTrueCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrConditionContext extends ConditionContext {
		public ConditionContext left;
		public Token op;
		public ConditionContext right;
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode OR() { return getToken(pythonParser.OR, 0); }
		public OrConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterOrCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitOrCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitOrCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndConditionContext extends ConditionContext {
		public ConditionContext left;
		public Token op;
		public ConditionContext right;
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode AND() { return getToken(pythonParser.AND, 0); }
		public AndConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterAndCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitAndCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitAndCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new NotConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(94);
				match(NOT);
				setState(95);
				condition(5);
				}
				break;
			case 2:
				{
				_localctx = new ParenConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96);
				match(LPAREN);
				setState(97);
				condition(0);
				setState(98);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new CompareConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100);
				((CompareConditionContext)_localctx).left = expr(0);
				setState(101);
				((CompareConditionContext)_localctx).op = comparisonOp();
				setState(102);
				((CompareConditionContext)_localctx).right = expr(0);
				}
				break;
			case 4:
				{
				_localctx = new TrueConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104);
				match(TRUE);
				}
				break;
			case 5:
				{
				_localctx = new FalseConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				match(FALSE);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(116);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(114);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new AndConditionContext(new ConditionContext(_parentctx, _parentState));
						((AndConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(108);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(109);
						((AndConditionContext)_localctx).op = match(AND);
						setState(110);
						((AndConditionContext)_localctx).right = condition(8);
						}
						break;
					case 2:
						{
						_localctx = new OrConditionContext(new ConditionContext(_parentctx, _parentState));
						((OrConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(111);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(112);
						((OrConditionContext)_localctx).op = match(OR);
						setState(113);
						((OrConditionContext)_localctx).right = condition(7);
						}
						break;
					}
					} 
				}
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonOpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(pythonParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(pythonParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(pythonParser.LT, 0); }
		public TerminalNode GT() { return getToken(pythonParser.GT, 0); }
		public TerminalNode LTE() { return getToken(pythonParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(pythonParser.GTE, 0); }
		public ComparisonOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterComparisonOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitComparisonOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitComparisonOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOpContext comparisonOp() throws RecognitionException {
		ComparisonOpContext _localctx = new ComparisonOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comparisonOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8064L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListContext extends ParserRuleContext {
		public Token lbrack;
		public Token trcomma;
		public Token rbrack;
		public TerminalNode LBRACK() { return getToken(pythonParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(pythonParser.RBRACK, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(pythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pythonParser.COMMA, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			((ListContext)_localctx).lbrack = match(LBRACK);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7130316802L) != 0)) {
				{
				setState(122);
				expr(0);
				setState(127);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(123);
						match(COMMA);
						setState(124);
						expr(0);
						}
						} 
					}
					setState(129);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(130);
					((ListContext)_localctx).trcomma = match(COMMA);
					}
				}

				}
			}

			setState(135);
			((ListContext)_localctx).rbrack = match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DictContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(pythonParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(pythonParser.RBRACE, 0); }
		public List<DictEntryContext> dictEntry() {
			return getRuleContexts(DictEntryContext.class);
		}
		public DictEntryContext dictEntry(int i) {
			return getRuleContext(DictEntryContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(pythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(pythonParser.COMMA, i);
		}
		public DictContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterDict(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitDict(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitDict(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictContext dict() throws RecognitionException {
		DictContext _localctx = new DictContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_dict);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(LBRACE);
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7130316802L) != 0)) {
				{
				setState(138);
				dictEntry();
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(139);
						match(COMMA);
						setState(140);
						dictEntry();
						}
						} 
					}
					setState(145);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(146);
					match(COMMA);
					}
				}

				}
			}

			setState(151);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DictEntryContext extends ParserRuleContext {
		public ExprContext key;
		public ExprContext value;
		public TerminalNode COLON() { return getToken(pythonParser.COLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DictEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterDictEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitDictEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitDictEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictEntryContext dictEntry() throws RecognitionException {
		DictEntryContext _localctx = new DictEntryContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_dictEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			((DictEntryContext)_localctx).key = expr(0);
			setState(154);
			match(COLON);
			setState(155);
			((DictEntryContext)_localctx).value = expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MUL_DIV_OPContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(pythonParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(pythonParser.DIV, 0); }
		public MUL_DIV_OPContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterMUL_DIV_OP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitMUL_DIV_OP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitMUL_DIV_OP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VARContext extends ExprContext {
		public TerminalNode NAME() { return getToken(pythonParser.NAME, 0); }
		public VARContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterVAR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitVAR(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitVAR(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SUM_SUB_OPContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(pythonParser.PLUS, 0); }
		public TerminalNode SUB() { return getToken(pythonParser.SUB, 0); }
		public SUM_SUB_OPContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterSUM_SUB_OP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitSUM_SUB_OP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitSUM_SUB_OP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NUMContext extends ExprContext {
		public TerminalNode NUM() { return getToken(pythonParser.NUM, 0); }
		public NUMContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterNUM(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitNUM(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitNUM(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EXPONENT_OPContext extends ExprContext {
		public ExprContext base;
		public Token op;
		public ExprContext exp;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode POW() { return getToken(pythonParser.POW, 0); }
		public EXPONENT_OPContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterEXPONENT_OP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitEXPONENT_OP(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitEXPONENT_OP(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DICTIONARYContext extends ExprContext {
		public DictContext dict() {
			return getRuleContext(DictContext.class,0);
		}
		public DICTIONARYContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterDICTIONARY(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitDICTIONARY(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitDICTIONARY(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STRING_LITERALContext extends ExprContext {
		public TerminalNode STRING() { return getToken(pythonParser.STRING, 0); }
		public STRING_LITERALContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterSTRING_LITERAL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitSTRING_LITERAL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitSTRING_LITERAL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListExprContext extends ExprContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public ListExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterListExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitListExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitListExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(pythonParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(pythonParser.RPAREN, 0); }
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof pythonParserListener ) ((pythonParserListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof pythonParserVisitor ) return ((pythonParserVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(158);
				match(LPAREN);
				setState(159);
				expr(0);
				setState(160);
				match(RPAREN);
				}
				break;
			case LBRACK:
				{
				_localctx = new ListExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(162);
				list();
				}
				break;
			case STRING:
				{
				_localctx = new STRING_LITERALContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(163);
				match(STRING);
				}
				break;
			case LBRACE:
				{
				_localctx = new DICTIONARYContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(164);
				dict();
				}
				break;
			case NUM:
				{
				_localctx = new NUMContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(165);
				match(NUM);
				}
				break;
			case NAME:
				{
				_localctx = new VARContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(166);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(180);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(178);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new EXPONENT_OPContext(new ExprContext(_parentctx, _parentState));
						((EXPONENT_OPContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(169);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(170);
						((EXPONENT_OPContext)_localctx).op = match(POW);
						setState(171);
						((EXPONENT_OPContext)_localctx).exp = expr(8);
						}
						break;
					case 2:
						{
						_localctx = new MUL_DIV_OPContext(new ExprContext(_parentctx, _parentState));
						((MUL_DIV_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(172);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(173);
						((MUL_DIV_OPContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((MUL_DIV_OPContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(174);
						((MUL_DIV_OPContext)_localctx).right = expr(8);
						}
						break;
					case 3:
						{
						_localctx = new SUM_SUB_OPContext(new ExprContext(_parentctx, _parentState));
						((SUM_SUB_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(175);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(176);
						((SUM_SUB_OPContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==SUB) ) {
							((SUM_SUB_OPContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(177);
						((SUM_SUB_OPContext)_localctx).right = expr(7);
						}
						break;
					}
					} 
				}
				setState(182);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return condition_sempred((ConditionContext)_localctx, predIndex);
		case 10:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001$\u00b8\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0005\u0000\u0018"+
		"\b\u0000\n\u0000\f\u0000\u001b\t\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		"\u001f\b\u0000\n\u0000\f\u0000\"\t\u0000\u0005\u0000$\b\u0000\n\u0000"+
		"\f\u0000\'\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003"+
		"\u0001-\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u00027\b\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003F\b\u0003\n\u0003\f\u0003I\t\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003Q\b"+
		"\u0003\u0001\u0004\u0001\u0004\u0005\u0004U\b\u0004\n\u0004\f\u0004X\t"+
		"\u0004\u0004\u0004Z\b\u0004\u000b\u0004\f\u0004[\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"k\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005s\b\u0005\n\u0005\f\u0005v\t\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007"+
		"~\b\u0007\n\u0007\f\u0007\u0081\t\u0007\u0001\u0007\u0003\u0007\u0084"+
		"\b\u0007\u0003\u0007\u0086\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u008e\b\b\n\b\f\b\u0091\t\b\u0001\b\u0003\b"+
		"\u0094\b\b\u0003\b\u0096\b\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u00a8\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0005\n\u00b3\b\n\n\n\f\n\u00b6\t\n\u0001\n"+
		"\u0000\u0002\n\u0014\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0000\u0003\u0001\u0000\u0007\f\u0001\u0000\u0003\u0004\u0001\u0000"+
		"\u0005\u0006\u00c9\u0000\u0019\u0001\u0000\u0000\u0000\u0002,\u0001\u0000"+
		"\u0000\u0000\u00046\u0001\u0000\u0000\u0000\u00068\u0001\u0000\u0000\u0000"+
		"\bY\u0001\u0000\u0000\u0000\nj\u0001\u0000\u0000\u0000\fw\u0001\u0000"+
		"\u0000\u0000\u000ey\u0001\u0000\u0000\u0000\u0010\u0089\u0001\u0000\u0000"+
		"\u0000\u0012\u0099\u0001\u0000\u0000\u0000\u0014\u00a7\u0001\u0000\u0000"+
		"\u0000\u0016\u0018\u0005!\u0000\u0000\u0017\u0016\u0001\u0000\u0000\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0001\u0000\u0000\u0000\u001a%\u0001\u0000\u0000\u0000\u001b"+
		"\u0019\u0001\u0000\u0000\u0000\u001c \u0003\u0002\u0001\u0000\u001d\u001f"+
		"\u0005!\u0000\u0000\u001e\u001d\u0001\u0000\u0000\u0000\u001f\"\u0001"+
		"\u0000\u0000\u0000 \u001e\u0001\u0000\u0000\u0000 !\u0001\u0000\u0000"+
		"\u0000!$\u0001\u0000\u0000\u0000\" \u0001\u0000\u0000\u0000#\u001c\u0001"+
		"\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000\u0000"+
		"%&\u0001\u0000\u0000\u0000&(\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000"+
		"\u0000()\u0005\u0000\u0000\u0001)\u0001\u0001\u0000\u0000\u0000*-\u0003"+
		"\u0004\u0002\u0000+-\u0003\u0006\u0003\u0000,*\u0001\u0000\u0000\u0000"+
		",+\u0001\u0000\u0000\u0000-\u0003\u0001\u0000\u0000\u0000./\u0005 \u0000"+
		"\u0000/0\u0005\u0016\u0000\u000007\u0003\u0014\n\u000012\u0005\u0013\u0000"+
		"\u000023\u0005\u0018\u0000\u000034\u0003\u0014\n\u000045\u0005\u0019\u0000"+
		"\u000057\u0001\u0000\u0000\u00006.\u0001\u0000\u0000\u000061\u0001\u0000"+
		"\u0000\u00007\u0005\u0001\u0000\u0000\u000089\u0005\u0010\u0000\u0000"+
		"9:\u0003\n\u0005\u0000:;\u0005\u0017\u0000\u0000;<\u0005#\u0000\u0000"+
		"<=\u0003\b\u0004\u0000=G\u0005$\u0000\u0000>?\u0005\u0011\u0000\u0000"+
		"?@\u0003\n\u0005\u0000@A\u0005\u0017\u0000\u0000AB\u0005#\u0000\u0000"+
		"BC\u0003\b\u0004\u0000CD\u0005$\u0000\u0000DF\u0001\u0000\u0000\u0000"+
		"E>\u0001\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000"+
		"\u0000GH\u0001\u0000\u0000\u0000HP\u0001\u0000\u0000\u0000IG\u0001\u0000"+
		"\u0000\u0000JK\u0005\u0012\u0000\u0000KL\u0005\u0017\u0000\u0000LM\u0005"+
		"#\u0000\u0000MN\u0003\b\u0004\u0000NO\u0005$\u0000\u0000OQ\u0001\u0000"+
		"\u0000\u0000PJ\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000Q\u0007"+
		"\u0001\u0000\u0000\u0000RV\u0003\u0002\u0001\u0000SU\u0005!\u0000\u0000"+
		"TS\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000"+
		"\u0000VW\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001\u0000"+
		"\u0000\u0000YR\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[Y\u0001"+
		"\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\t\u0001\u0000\u0000\u0000"+
		"]^\u0006\u0005\uffff\uffff\u0000^_\u0005\u000f\u0000\u0000_k\u0003\n\u0005"+
		"\u0005`a\u0005\u0018\u0000\u0000ab\u0003\n\u0005\u0000bc\u0005\u0019\u0000"+
		"\u0000ck\u0001\u0000\u0000\u0000de\u0003\u0014\n\u0000ef\u0003\f\u0006"+
		"\u0000fg\u0003\u0014\n\u0000gk\u0001\u0000\u0000\u0000hk\u0005\u0014\u0000"+
		"\u0000ik\u0005\u0015\u0000\u0000j]\u0001\u0000\u0000\u0000j`\u0001\u0000"+
		"\u0000\u0000jd\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000ji\u0001"+
		"\u0000\u0000\u0000kt\u0001\u0000\u0000\u0000lm\n\u0007\u0000\u0000mn\u0005"+
		"\r\u0000\u0000ns\u0003\n\u0005\bop\n\u0006\u0000\u0000pq\u0005\u000e\u0000"+
		"\u0000qs\u0003\n\u0005\u0007rl\u0001\u0000\u0000\u0000ro\u0001\u0000\u0000"+
		"\u0000sv\u0001\u0000\u0000\u0000tr\u0001\u0000\u0000\u0000tu\u0001\u0000"+
		"\u0000\u0000u\u000b\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000"+
		"wx\u0007\u0000\u0000\u0000x\r\u0001\u0000\u0000\u0000y\u0085\u0005\u001b"+
		"\u0000\u0000z\u007f\u0003\u0014\n\u0000{|\u0005\u001a\u0000\u0000|~\u0003"+
		"\u0014\n\u0000}{\u0001\u0000\u0000\u0000~\u0081\u0001\u0000\u0000\u0000"+
		"\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080"+
		"\u0083\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0082"+
		"\u0084\u0005\u001a\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0001\u0000\u0000\u0000\u0084\u0086\u0001\u0000\u0000\u0000\u0085"+
		"z\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0087"+
		"\u0001\u0000\u0000\u0000\u0087\u0088\u0005\u001c\u0000\u0000\u0088\u000f"+
		"\u0001\u0000\u0000\u0000\u0089\u0095\u0005\u001d\u0000\u0000\u008a\u008f"+
		"\u0003\u0012\t\u0000\u008b\u008c\u0005\u001a\u0000\u0000\u008c\u008e\u0003"+
		"\u0012\t\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u0091\u0001\u0000"+
		"\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000"+
		"\u0000\u0000\u0090\u0093\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000"+
		"\u0000\u0000\u0092\u0094\u0005\u001a\u0000\u0000\u0093\u0092\u0001\u0000"+
		"\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0096\u0001\u0000"+
		"\u0000\u0000\u0095\u008a\u0001\u0000\u0000\u0000\u0095\u0096\u0001\u0000"+
		"\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u001e"+
		"\u0000\u0000\u0098\u0011\u0001\u0000\u0000\u0000\u0099\u009a\u0003\u0014"+
		"\n\u0000\u009a\u009b\u0005\u0017\u0000\u0000\u009b\u009c\u0003\u0014\n"+
		"\u0000\u009c\u0013\u0001\u0000\u0000\u0000\u009d\u009e\u0006\n\uffff\uffff"+
		"\u0000\u009e\u009f\u0005\u0018\u0000\u0000\u009f\u00a0\u0003\u0014\n\u0000"+
		"\u00a0\u00a1\u0005\u0019\u0000\u0000\u00a1\u00a8\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a8\u0003\u000e\u0007\u0000\u00a3\u00a8\u0005\u001f\u0000\u0000"+
		"\u00a4\u00a8\u0003\u0010\b\u0000\u00a5\u00a8\u0005\u0001\u0000\u0000\u00a6"+
		"\u00a8\u0005 \u0000\u0000\u00a7\u009d\u0001\u0000\u0000\u0000\u00a7\u00a2"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a3\u0001\u0000\u0000\u0000\u00a7\u00a4"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a6"+
		"\u0001\u0000\u0000\u0000\u00a8\u00b4\u0001\u0000\u0000\u0000\u00a9\u00aa"+
		"\n\b\u0000\u0000\u00aa\u00ab\u0005\u0002\u0000\u0000\u00ab\u00b3\u0003"+
		"\u0014\n\b\u00ac\u00ad\n\u0007\u0000\u0000\u00ad\u00ae\u0007\u0001\u0000"+
		"\u0000\u00ae\u00b3\u0003\u0014\n\b\u00af\u00b0\n\u0006\u0000\u0000\u00b0"+
		"\u00b1\u0007\u0002\u0000\u0000\u00b1\u00b3\u0003\u0014\n\u0007\u00b2\u00a9"+
		"\u0001\u0000\u0000\u0000\u00b2\u00ac\u0001\u0000\u0000\u0000\u00b2\u00af"+
		"\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000\u0000\u0000\u00b4\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u0015"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u0015\u0019"+
		" %,6GPV[jrt\u007f\u0083\u0085\u008f\u0093\u0095\u00a7\u00b2\u00b4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}