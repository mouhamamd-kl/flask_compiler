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
		FALSE=21, ASSIGN=22, COLON=23, LPAREN=24, RPAREN=25, NAME=26, NL=27, WS=28, 
		INDENT=29, DEDENT=30;
	public static final int
		RULE_root = 0, RULE_statement = 1, RULE_action = 2, RULE_ifStatement = 3, 
		RULE_block = 4, RULE_condition = 5, RULE_comparisonOp = 6, RULE_expr = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "statement", "action", "ifStatement", "block", "condition", "comparisonOp", 
			"expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'**'", "'*'", "'/'", "'+'", "'-'", "'=='", "'!='", "'<'", 
			"'>'", "'<='", "'>='", "'and'", "'or'", "'not'", "'if'", "'elif'", "'else'", 
			"'print'", "'True'", "'False'", "'='", "':'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NUM", "POW", "MUL", "DIV", "PLUS", "SUB", "EQ", "NEQ", "LT", "GT", 
			"LTE", "GTE", "AND", "OR", "NOT", "IF", "ELIF", "ELSE", "PRINT", "TRUE", 
			"FALSE", "ASSIGN", "COLON", "LPAREN", "RPAREN", "NAME", "NL", "WS", "INDENT", 
			"DEDENT"
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
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(16);
				match(NL);
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 67698688L) != 0)) {
				{
				{
				setState(22);
				statement();
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(23);
					match(NL);
					}
					}
					setState(28);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
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
			setState(38);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				action();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(37);
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
			setState(48);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				_localctx = new AssignActionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				((AssignActionContext)_localctx).var = match(NAME);
				setState(41);
				((AssignActionContext)_localctx).op = match(ASSIGN);
				setState(42);
				((AssignActionContext)_localctx).value = expr(0);
				}
				break;
			case PRINT:
				_localctx = new PrintActionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				match(PRINT);
				setState(44);
				match(LPAREN);
				setState(45);
				expr(0);
				setState(46);
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
			setState(50);
			match(IF);
			setState(51);
			condition(0);
			setState(52);
			match(COLON);
			setState(53);
			match(INDENT);
			setState(54);
			block();
			setState(55);
			match(DEDENT);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELIF) {
				{
				{
				setState(56);
				match(ELIF);
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
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(68);
				match(ELSE);
				setState(69);
				match(COLON);
				setState(70);
				match(INDENT);
				setState(71);
				block();
				setState(72);
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
			setState(83); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(76);
				statement();
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(77);
					match(NL);
					}
					}
					setState(82);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(85); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 67698688L) != 0) );
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
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new NotConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(88);
				match(NOT);
				setState(89);
				condition(5);
				}
				break;
			case 2:
				{
				_localctx = new ParenConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				match(LPAREN);
				setState(91);
				condition(0);
				setState(92);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new CompareConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(94);
				((CompareConditionContext)_localctx).left = expr(0);
				setState(95);
				((CompareConditionContext)_localctx).op = comparisonOp();
				setState(96);
				((CompareConditionContext)_localctx).right = expr(0);
				}
				break;
			case 4:
				{
				_localctx = new TrueConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98);
				match(TRUE);
				}
				break;
			case 5:
				{
				_localctx = new FalseConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99);
				match(FALSE);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(110);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(108);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new AndConditionContext(new ConditionContext(_parentctx, _parentState));
						((AndConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(102);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(103);
						((AndConditionContext)_localctx).op = match(AND);
						setState(104);
						((AndConditionContext)_localctx).right = condition(8);
						}
						break;
					case 2:
						{
						_localctx = new OrConditionContext(new ConditionContext(_parentctx, _parentState));
						((OrConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(105);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(106);
						((OrConditionContext)_localctx).op = match(OR);
						setState(107);
						((OrConditionContext)_localctx).right = condition(7);
						}
						break;
					}
					} 
				}
				setState(112);
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
			setState(113);
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
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(116);
				match(LPAREN);
				setState(117);
				expr(0);
				setState(118);
				match(RPAREN);
				}
				break;
			case NUM:
				{
				_localctx = new NUMContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(120);
				match(NUM);
				}
				break;
			case NAME:
				{
				_localctx = new VARContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(135);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(133);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new EXPONENT_OPContext(new ExprContext(_parentctx, _parentState));
						((EXPONENT_OPContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(124);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(125);
						((EXPONENT_OPContext)_localctx).op = match(POW);
						setState(126);
						((EXPONENT_OPContext)_localctx).exp = expr(5);
						}
						break;
					case 2:
						{
						_localctx = new MUL_DIV_OPContext(new ExprContext(_parentctx, _parentState));
						((MUL_DIV_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(127);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(128);
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
						setState(129);
						((MUL_DIV_OPContext)_localctx).right = expr(5);
						}
						break;
					case 3:
						{
						_localctx = new SUM_SUB_OPContext(new ExprContext(_parentctx, _parentState));
						((SUM_SUB_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(130);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(131);
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
						setState(132);
						((SUM_SUB_OPContext)_localctx).right = expr(4);
						}
						break;
					}
					} 
				}
				setState(137);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
		case 7:
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
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001e\u008b\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0001\u0000\u0005\u0000\u0012\b\u0000\n\u0000\f\u0000\u0015\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u0019\b\u0000\n\u0000\f\u0000\u001c\t\u0000"+
		"\u0005\u0000\u001e\b\u0000\n\u0000\f\u0000!\t\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0003\u0001\'\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u00021\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003@\b\u0003\n\u0003"+
		"\f\u0003C\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003K\b\u0003\u0001\u0004\u0001\u0004\u0005"+
		"\u0004O\b\u0004\n\u0004\f\u0004R\t\u0004\u0004\u0004T\b\u0004\u000b\u0004"+
		"\f\u0004U\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005e\b\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005m\b\u0005"+
		"\n\u0005\f\u0005p\t\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007"+
		"{\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0086\b\u0007"+
		"\n\u0007\f\u0007\u0089\t\u0007\u0001\u0007\u0000\u0002\n\u000e\b\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0000\u0003\u0001\u0000\u0007\f\u0001\u0000"+
		"\u0003\u0004\u0001\u0000\u0005\u0006\u0096\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0002&\u0001\u0000\u0000\u0000\u00040\u0001\u0000\u0000\u0000\u0006"+
		"2\u0001\u0000\u0000\u0000\bS\u0001\u0000\u0000\u0000\nd\u0001\u0000\u0000"+
		"\u0000\fq\u0001\u0000\u0000\u0000\u000ez\u0001\u0000\u0000\u0000\u0010"+
		"\u0012\u0005\u001b\u0000\u0000\u0011\u0010\u0001\u0000\u0000\u0000\u0012"+
		"\u0015\u0001\u0000\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0013"+
		"\u0014\u0001\u0000\u0000\u0000\u0014\u001f\u0001\u0000\u0000\u0000\u0015"+
		"\u0013\u0001\u0000\u0000\u0000\u0016\u001a\u0003\u0002\u0001\u0000\u0017"+
		"\u0019\u0005\u001b\u0000\u0000\u0018\u0017\u0001\u0000\u0000\u0000\u0019"+
		"\u001c\u0001\u0000\u0000\u0000\u001a\u0018\u0001\u0000\u0000\u0000\u001a"+
		"\u001b\u0001\u0000\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000\u001c"+
		"\u001a\u0001\u0000\u0000\u0000\u001d\u0016\u0001\u0000\u0000\u0000\u001e"+
		"!\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001"+
		"\u0000\u0000\u0000 \"\u0001\u0000\u0000\u0000!\u001f\u0001\u0000\u0000"+
		"\u0000\"#\u0005\u0000\u0000\u0001#\u0001\u0001\u0000\u0000\u0000$\'\u0003"+
		"\u0004\u0002\u0000%\'\u0003\u0006\u0003\u0000&$\u0001\u0000\u0000\u0000"+
		"&%\u0001\u0000\u0000\u0000\'\u0003\u0001\u0000\u0000\u0000()\u0005\u001a"+
		"\u0000\u0000)*\u0005\u0016\u0000\u0000*1\u0003\u000e\u0007\u0000+,\u0005"+
		"\u0013\u0000\u0000,-\u0005\u0018\u0000\u0000-.\u0003\u000e\u0007\u0000"+
		"./\u0005\u0019\u0000\u0000/1\u0001\u0000\u0000\u00000(\u0001\u0000\u0000"+
		"\u00000+\u0001\u0000\u0000\u00001\u0005\u0001\u0000\u0000\u000023\u0005"+
		"\u0010\u0000\u000034\u0003\n\u0005\u000045\u0005\u0017\u0000\u000056\u0005"+
		"\u001d\u0000\u000067\u0003\b\u0004\u00007A\u0005\u001e\u0000\u000089\u0005"+
		"\u0011\u0000\u00009:\u0003\n\u0005\u0000:;\u0005\u0017\u0000\u0000;<\u0005"+
		"\u001d\u0000\u0000<=\u0003\b\u0004\u0000=>\u0005\u001e\u0000\u0000>@\u0001"+
		"\u0000\u0000\u0000?8\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000\u0000"+
		"A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BJ\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000DE\u0005\u0012\u0000\u0000EF\u0005\u0017"+
		"\u0000\u0000FG\u0005\u001d\u0000\u0000GH\u0003\b\u0004\u0000HI\u0005\u001e"+
		"\u0000\u0000IK\u0001\u0000\u0000\u0000JD\u0001\u0000\u0000\u0000JK\u0001"+
		"\u0000\u0000\u0000K\u0007\u0001\u0000\u0000\u0000LP\u0003\u0002\u0001"+
		"\u0000MO\u0005\u001b\u0000\u0000NM\u0001\u0000\u0000\u0000OR\u0001\u0000"+
		"\u0000\u0000PN\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QT\u0001"+
		"\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000SL\u0001\u0000\u0000\u0000"+
		"TU\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000"+
		"\u0000V\t\u0001\u0000\u0000\u0000WX\u0006\u0005\uffff\uffff\u0000XY\u0005"+
		"\u000f\u0000\u0000Ye\u0003\n\u0005\u0005Z[\u0005\u0018\u0000\u0000[\\"+
		"\u0003\n\u0005\u0000\\]\u0005\u0019\u0000\u0000]e\u0001\u0000\u0000\u0000"+
		"^_\u0003\u000e\u0007\u0000_`\u0003\f\u0006\u0000`a\u0003\u000e\u0007\u0000"+
		"ae\u0001\u0000\u0000\u0000be\u0005\u0014\u0000\u0000ce\u0005\u0015\u0000"+
		"\u0000dW\u0001\u0000\u0000\u0000dZ\u0001\u0000\u0000\u0000d^\u0001\u0000"+
		"\u0000\u0000db\u0001\u0000\u0000\u0000dc\u0001\u0000\u0000\u0000en\u0001"+
		"\u0000\u0000\u0000fg\n\u0007\u0000\u0000gh\u0005\r\u0000\u0000hm\u0003"+
		"\n\u0005\bij\n\u0006\u0000\u0000jk\u0005\u000e\u0000\u0000km\u0003\n\u0005"+
		"\u0007lf\u0001\u0000\u0000\u0000li\u0001\u0000\u0000\u0000mp\u0001\u0000"+
		"\u0000\u0000nl\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000o\u000b"+
		"\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000qr\u0007\u0000\u0000"+
		"\u0000r\r\u0001\u0000\u0000\u0000st\u0006\u0007\uffff\uffff\u0000tu\u0005"+
		"\u0018\u0000\u0000uv\u0003\u000e\u0007\u0000vw\u0005\u0019\u0000\u0000"+
		"w{\u0001\u0000\u0000\u0000x{\u0005\u0001\u0000\u0000y{\u0005\u001a\u0000"+
		"\u0000zs\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000zy\u0001\u0000"+
		"\u0000\u0000{\u0087\u0001\u0000\u0000\u0000|}\n\u0005\u0000\u0000}~\u0005"+
		"\u0002\u0000\u0000~\u0086\u0003\u000e\u0007\u0005\u007f\u0080\n\u0004"+
		"\u0000\u0000\u0080\u0081\u0007\u0001\u0000\u0000\u0081\u0086\u0003\u000e"+
		"\u0007\u0005\u0082\u0083\n\u0003\u0000\u0000\u0083\u0084\u0007\u0002\u0000"+
		"\u0000\u0084\u0086\u0003\u000e\u0007\u0004\u0085|\u0001\u0000\u0000\u0000"+
		"\u0085\u007f\u0001\u0000\u0000\u0000\u0085\u0082\u0001\u0000\u0000\u0000"+
		"\u0086\u0089\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000"+
		"\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u000f\u0001\u0000\u0000\u0000"+
		"\u0089\u0087\u0001\u0000\u0000\u0000\u000f\u0013\u001a\u001f&0AJPUdln"+
		"z\u0085\u0087";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}