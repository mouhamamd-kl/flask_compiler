// Generated from c:/Users/HP/IdeaProjects/antlr_course/grammars/pythonParser.g4 by ANTLR 4.13.1

   package antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class pythonParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, POW=2, MUL=3, DIV=4, PLUS=5, SUB=6, EQ=7, NEQ=8, LT=9, GT=10, LTE=11, 
		GTE=12, AND=13, OR=14, NOT=15, IF=16, ELIF=17, ELSE=18, PRINT=19, TRUE=20, 
		FALSE=21, ASSIGN=22, COLON=23, LPAREN=24, RPAREN=25, COMMA=26, LBRACK=27, 
		RBRACK=28, LBRACE=29, RBRACE=30, NAME=31, NL=32, WS=33, INDENT=34, DEDENT=35;
	public static final int
		RULE_root = 0, RULE_statement = 1, RULE_action = 2, RULE_ifStatement = 3, 
		RULE_block = 4, RULE_condition = 5, RULE_comparisonOp = 6, RULE_list = 7, 
		RULE_expr = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "statement", "action", "ifStatement", "block", "condition", "comparisonOp", 
			"list", "expr"
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
			"LBRACE", "RBRACE", "NAME", "NL", "WS", "INDENT", "DEDENT"
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
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(18);
				match(NL);
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2148073472L) != 0)) {
				{
				{
				setState(24);
				statement();
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(25);
					match(NL);
					}
					}
					setState(30);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
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
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(40);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				action();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
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
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_action);
		try {
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				_localctx = new AssignActionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				((AssignActionContext)_localctx).var = match(NAME);
				setState(43);
				((AssignActionContext)_localctx).op = match(ASSIGN);
				setState(44);
				((AssignActionContext)_localctx).value = expr(0);
				}
				break;
			case PRINT:
				_localctx = new PrintActionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				match(PRINT);
				setState(46);
				match(LPAREN);
				setState(47);
				expr(0);
				setState(48);
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
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(IF);
			setState(53);
			condition(0);
			setState(54);
			match(COLON);
			setState(55);
			match(INDENT);
			setState(56);
			block();
			setState(57);
			match(DEDENT);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELIF) {
				{
				{
				setState(58);
				match(ELIF);
				setState(59);
				condition(0);
				setState(60);
				match(COLON);
				setState(61);
				match(INDENT);
				setState(62);
				block();
				setState(63);
				match(DEDENT);
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(70);
				match(ELSE);
				setState(71);
				match(COLON);
				setState(72);
				match(INDENT);
				setState(73);
				block();
				setState(74);
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
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(78);
				statement();
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(79);
					match(NL);
					}
					}
					setState(84);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(87); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 2148073472L) != 0) );
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenConditionContext extends ConditionContext {
		public TerminalNode LPAREN() { return getToken(pythonParser.LPAREN, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(pythonParser.RPAREN, 0); }
		public ParenConditionContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseConditionContext extends ConditionContext {
		public TerminalNode FALSE() { return getToken(pythonParser.FALSE, 0); }
		public FalseConditionContext(ConditionContext ctx) { copyFrom(ctx); }
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueConditionContext extends ConditionContext {
		public TerminalNode TRUE() { return getToken(pythonParser.TRUE, 0); }
		public TrueConditionContext(ConditionContext ctx) { copyFrom(ctx); }
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
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new NotConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(90);
				match(NOT);
				setState(91);
				condition(5);
				}
				break;
			case 2:
				{
				_localctx = new ParenConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92);
				match(LPAREN);
				setState(93);
				condition(0);
				setState(94);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new CompareConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96);
				((CompareConditionContext)_localctx).left = expr(0);
				setState(97);
				((CompareConditionContext)_localctx).op = comparisonOp();
				setState(98);
				((CompareConditionContext)_localctx).right = expr(0);
				}
				break;
			case 4:
				{
				_localctx = new TrueConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100);
				match(TRUE);
				}
				break;
			case 5:
				{
				_localctx = new FalseConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101);
				match(FALSE);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(112);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(110);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new AndConditionContext(new ConditionContext(_parentctx, _parentState));
						((AndConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(104);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(105);
						((AndConditionContext)_localctx).op = match(AND);
						setState(106);
						((AndConditionContext)_localctx).right = condition(8);
						}
						break;
					case 2:
						{
						_localctx = new OrConditionContext(new ConditionContext(_parentctx, _parentState));
						((OrConditionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(107);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(108);
						((OrConditionContext)_localctx).op = match(OR);
						setState(109);
						((OrConditionContext)_localctx).right = condition(7);
						}
						break;
					}
					} 
				}
				setState(114);
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
	}

	public final ComparisonOpContext comparisonOp() throws RecognitionException {
		ComparisonOpContext _localctx = new ComparisonOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comparisonOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
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
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			((ListContext)_localctx).lbrack = match(LBRACK);
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2298478594L) != 0)) {
				{
				setState(118);
				expr(0);
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(119);
						match(COMMA);
						setState(120);
						expr(0);
						}
						} 
					}
					setState(125);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(126);
					((ListContext)_localctx).trcomma = match(COMMA);
					}
				}

				}
			}

			setState(131);
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VARContext extends ExprContext {
		public TerminalNode NAME() { return getToken(pythonParser.NAME, 0); }
		public VARContext(ExprContext ctx) { copyFrom(ctx); }
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NUMContext extends ExprContext {
		public TerminalNode NUM() { return getToken(pythonParser.NUM, 0); }
		public NUMContext(ExprContext ctx) { copyFrom(ctx); }
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListExprContext extends ExprContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public ListExprContext(ExprContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(pythonParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(pythonParser.RPAREN, 0); }
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(134);
				match(LPAREN);
				setState(135);
				expr(0);
				setState(136);
				match(RPAREN);
				}
				break;
			case LBRACK:
				{
				_localctx = new ListExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(138);
				list();
				}
				break;
			case NUM:
				{
				_localctx = new NUMContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				match(NUM);
				}
				break;
			case NAME:
				{
				_localctx = new VARContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(140);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(154);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(152);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new EXPONENT_OPContext(new ExprContext(_parentctx, _parentState));
						((EXPONENT_OPContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(143);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(144);
						((EXPONENT_OPContext)_localctx).op = match(POW);
						setState(145);
						((EXPONENT_OPContext)_localctx).exp = expr(6);
						}
						break;
					case 2:
						{
						_localctx = new MUL_DIV_OPContext(new ExprContext(_parentctx, _parentState));
						((MUL_DIV_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(146);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(147);
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
						setState(148);
						((MUL_DIV_OPContext)_localctx).right = expr(6);
						}
						break;
					case 3:
						{
						_localctx = new SUM_SUB_OPContext(new ExprContext(_parentctx, _parentState));
						((SUM_SUB_OPContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(149);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(150);
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
						setState(151);
						((SUM_SUB_OPContext)_localctx).right = expr(5);
						}
						break;
					}
					} 
				}
				setState(156);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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
		case 8:
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
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001#\u009e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0005\u0000\u0014\b\u0000\n\u0000\f\u0000\u0017"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000\f\u0000"+
		"\u001e\t\u0000\u0005\u0000 \b\u0000\n\u0000\f\u0000#\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001)\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u00023\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003B\b\u0003"+
		"\n\u0003\f\u0003E\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003M\b\u0003\u0001\u0004\u0001\u0004"+
		"\u0005\u0004Q\b\u0004\n\u0004\f\u0004T\t\u0004\u0004\u0004V\b\u0004\u000b"+
		"\u0004\f\u0004W\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0003\u0005g\b\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005o\b"+
		"\u0005\n\u0005\f\u0005r\t\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007z\b\u0007\n\u0007\f\u0007}\t"+
		"\u0007\u0001\u0007\u0003\u0007\u0080\b\u0007\u0003\u0007\u0082\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u008e\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u0099\b\b\n\b\f\b\u009c\t\b"+
		"\u0001\b\u0000\u0002\n\u0010\t\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0000\u0003\u0001\u0000\u0007\f\u0001\u0000\u0003\u0004\u0001\u0000\u0005"+
		"\u0006\u00ac\u0000\u0015\u0001\u0000\u0000\u0000\u0002(\u0001\u0000\u0000"+
		"\u0000\u00042\u0001\u0000\u0000\u0000\u00064\u0001\u0000\u0000\u0000\b"+
		"U\u0001\u0000\u0000\u0000\nf\u0001\u0000\u0000\u0000\fs\u0001\u0000\u0000"+
		"\u0000\u000eu\u0001\u0000\u0000\u0000\u0010\u008d\u0001\u0000\u0000\u0000"+
		"\u0012\u0014\u0005 \u0000\u0000\u0013\u0012\u0001\u0000\u0000\u0000\u0014"+
		"\u0017\u0001\u0000\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0015"+
		"\u0016\u0001\u0000\u0000\u0000\u0016!\u0001\u0000\u0000\u0000\u0017\u0015"+
		"\u0001\u0000\u0000\u0000\u0018\u001c\u0003\u0002\u0001\u0000\u0019\u001b"+
		"\u0005 \u0000\u0000\u001a\u0019\u0001\u0000\u0000\u0000\u001b\u001e\u0001"+
		"\u0000\u0000\u0000\u001c\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001"+
		"\u0000\u0000\u0000\u001d \u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000"+
		"\u0000\u0000\u001f\u0018\u0001\u0000\u0000\u0000 #\u0001\u0000\u0000\u0000"+
		"!\u001f\u0001\u0000\u0000\u0000!\"\u0001\u0000\u0000\u0000\"$\u0001\u0000"+
		"\u0000\u0000#!\u0001\u0000\u0000\u0000$%\u0005\u0000\u0000\u0001%\u0001"+
		"\u0001\u0000\u0000\u0000&)\u0003\u0004\u0002\u0000\')\u0003\u0006\u0003"+
		"\u0000(&\u0001\u0000\u0000\u0000(\'\u0001\u0000\u0000\u0000)\u0003\u0001"+
		"\u0000\u0000\u0000*+\u0005\u001f\u0000\u0000+,\u0005\u0016\u0000\u0000"+
		",3\u0003\u0010\b\u0000-.\u0005\u0013\u0000\u0000./\u0005\u0018\u0000\u0000"+
		"/0\u0003\u0010\b\u000001\u0005\u0019\u0000\u000013\u0001\u0000\u0000\u0000"+
		"2*\u0001\u0000\u0000\u00002-\u0001\u0000\u0000\u00003\u0005\u0001\u0000"+
		"\u0000\u000045\u0005\u0010\u0000\u000056\u0003\n\u0005\u000067\u0005\u0017"+
		"\u0000\u000078\u0005\"\u0000\u000089\u0003\b\u0004\u00009C\u0005#\u0000"+
		"\u0000:;\u0005\u0011\u0000\u0000;<\u0003\n\u0005\u0000<=\u0005\u0017\u0000"+
		"\u0000=>\u0005\"\u0000\u0000>?\u0003\b\u0004\u0000?@\u0005#\u0000\u0000"+
		"@B\u0001\u0000\u0000\u0000A:\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000DL\u0001\u0000"+
		"\u0000\u0000EC\u0001\u0000\u0000\u0000FG\u0005\u0012\u0000\u0000GH\u0005"+
		"\u0017\u0000\u0000HI\u0005\"\u0000\u0000IJ\u0003\b\u0004\u0000JK\u0005"+
		"#\u0000\u0000KM\u0001\u0000\u0000\u0000LF\u0001\u0000\u0000\u0000LM\u0001"+
		"\u0000\u0000\u0000M\u0007\u0001\u0000\u0000\u0000NR\u0003\u0002\u0001"+
		"\u0000OQ\u0005 \u0000\u0000PO\u0001\u0000\u0000\u0000QT\u0001\u0000\u0000"+
		"\u0000RP\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000SV\u0001\u0000"+
		"\u0000\u0000TR\u0001\u0000\u0000\u0000UN\u0001\u0000\u0000\u0000VW\u0001"+
		"\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000"+
		"X\t\u0001\u0000\u0000\u0000YZ\u0006\u0005\uffff\uffff\u0000Z[\u0005\u000f"+
		"\u0000\u0000[g\u0003\n\u0005\u0005\\]\u0005\u0018\u0000\u0000]^\u0003"+
		"\n\u0005\u0000^_\u0005\u0019\u0000\u0000_g\u0001\u0000\u0000\u0000`a\u0003"+
		"\u0010\b\u0000ab\u0003\f\u0006\u0000bc\u0003\u0010\b\u0000cg\u0001\u0000"+
		"\u0000\u0000dg\u0005\u0014\u0000\u0000eg\u0005\u0015\u0000\u0000fY\u0001"+
		"\u0000\u0000\u0000f\\\u0001\u0000\u0000\u0000f`\u0001\u0000\u0000\u0000"+
		"fd\u0001\u0000\u0000\u0000fe\u0001\u0000\u0000\u0000gp\u0001\u0000\u0000"+
		"\u0000hi\n\u0007\u0000\u0000ij\u0005\r\u0000\u0000jo\u0003\n\u0005\bk"+
		"l\n\u0006\u0000\u0000lm\u0005\u000e\u0000\u0000mo\u0003\n\u0005\u0007"+
		"nh\u0001\u0000\u0000\u0000nk\u0001\u0000\u0000\u0000or\u0001\u0000\u0000"+
		"\u0000pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000q\u000b\u0001"+
		"\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000st\u0007\u0000\u0000\u0000"+
		"t\r\u0001\u0000\u0000\u0000u\u0081\u0005\u001b\u0000\u0000v{\u0003\u0010"+
		"\b\u0000wx\u0005\u001a\u0000\u0000xz\u0003\u0010\b\u0000yw\u0001\u0000"+
		"\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000{|\u0001"+
		"\u0000\u0000\u0000|\u007f\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000"+
		"\u0000~\u0080\u0005\u001a\u0000\u0000\u007f~\u0001\u0000\u0000\u0000\u007f"+
		"\u0080\u0001\u0000\u0000\u0000\u0080\u0082\u0001\u0000\u0000\u0000\u0081"+
		"v\u0001\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005\u001c\u0000\u0000\u0084\u000f"+
		"\u0001\u0000\u0000\u0000\u0085\u0086\u0006\b\uffff\uffff\u0000\u0086\u0087"+
		"\u0005\u0018\u0000\u0000\u0087\u0088\u0003\u0010\b\u0000\u0088\u0089\u0005"+
		"\u0019\u0000\u0000\u0089\u008e\u0001\u0000\u0000\u0000\u008a\u008e\u0003"+
		"\u000e\u0007\u0000\u008b\u008e\u0005\u0001\u0000\u0000\u008c\u008e\u0005"+
		"\u001f\u0000\u0000\u008d\u0085\u0001\u0000\u0000\u0000\u008d\u008a\u0001"+
		"\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008c\u0001"+
		"\u0000\u0000\u0000\u008e\u009a\u0001\u0000\u0000\u0000\u008f\u0090\n\u0006"+
		"\u0000\u0000\u0090\u0091\u0005\u0002\u0000\u0000\u0091\u0099\u0003\u0010"+
		"\b\u0006\u0092\u0093\n\u0005\u0000\u0000\u0093\u0094\u0007\u0001\u0000"+
		"\u0000\u0094\u0099\u0003\u0010\b\u0006\u0095\u0096\n\u0004\u0000\u0000"+
		"\u0096\u0097\u0007\u0002\u0000\u0000\u0097\u0099\u0003\u0010\b\u0005\u0098"+
		"\u008f\u0001\u0000\u0000\u0000\u0098\u0092\u0001\u0000\u0000\u0000\u0098"+
		"\u0095\u0001\u0000\u0000\u0000\u0099\u009c\u0001\u0000\u0000\u0000\u009a"+
		"\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b"+
		"\u0011\u0001\u0000\u0000\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u0012"+
		"\u0015\u001c!(2CLRWfnp{\u007f\u0081\u008d\u0098\u009a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}