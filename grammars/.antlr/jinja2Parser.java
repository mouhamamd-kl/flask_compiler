// Generated from c:/Users/HP/IdeaProjects/antlr_course/grammars/jinja2Parser.g4 by ANTLR 4.13.1

   package antlr.gen.jinja2;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class jinja2Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		STMT_OPEN=1, EXPR_OPEN=2, COMMENT_OPEN=3, HTML_TEXT=4, STMT_CLOSE=5, FOR=6, 
		ENDFOR=7, IN=8, IF=9, ELIF=10, ELSE=11, ENDIF=12, BLOCK=13, ENDBLOCK=14, 
		EXTENDS=15, INCLUDE=16, SET=17, ENDSET=18, MACRO=19, ENDMACRO=20, DOT=21, 
		COMMA=22, COLON=23, LPAREN=24, RPAREN=25, LBRACK=26, RBRACK=27, EQ=28, 
		NEQ=29, LT=30, GT=31, LTE=32, GTE=33, ASSIGN=34, PIPE=35, AND=36, OR=37, 
		NOT=38, STRING=39, NUMBER=40, NAME=41, STMT_WS=42, EXPR_CLOSE=43, EXPR_WS=44, 
		COMMENT_CLOSE=45, COMMENT_TEXT=46;
	public static final int
		RULE_template = 0, RULE_templateContent = 1, RULE_htmlText = 2, RULE_expressionBlock = 3, 
		RULE_expression = 4, RULE_primary = 5, RULE_argList = 6, RULE_filter = 7, 
		RULE_forBlock = 8, RULE_forStart = 9, RULE_forEnd = 10, RULE_ifBlock = 11, 
		RULE_ifStart = 12, RULE_elifBranch = 13, RULE_elseBranch = 14, RULE_ifEnd = 15, 
		RULE_condition = 16, RULE_compOp = 17, RULE_extendsStatement = 18, RULE_includeStatement = 19, 
		RULE_blockDefinition = 20, RULE_setTarget = 21, RULE_setStatement = 22, 
		RULE_setBlockStatement = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"template", "templateContent", "htmlText", "expressionBlock", "expression", 
			"primary", "argList", "filter", "forBlock", "forStart", "forEnd", "ifBlock", 
			"ifStart", "elifBranch", "elseBranch", "ifEnd", "condition", "compOp", 
			"extendsStatement", "includeStatement", "blockDefinition", "setTarget", 
			"setStatement", "setBlockStatement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{%'", "'{{'", "'{#'", null, "'%}'", "'for'", "'endfor'", "'in'", 
			"'if'", "'elif'", "'else'", "'endif'", "'block'", "'endblock'", "'extends'", 
			"'include'", "'set'", "'endset'", "'macro'", "'endmacro'", null, null, 
			"':'", null, null, null, null, "'=='", "'!='", "'<'", "'>'", "'<='", 
			"'>='", "'='", null, "'&&'", "'||'", "'!'", null, null, null, null, "'}}'", 
			null, "'#}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "STMT_OPEN", "EXPR_OPEN", "COMMENT_OPEN", "HTML_TEXT", "STMT_CLOSE", 
			"FOR", "ENDFOR", "IN", "IF", "ELIF", "ELSE", "ENDIF", "BLOCK", "ENDBLOCK", 
			"EXTENDS", "INCLUDE", "SET", "ENDSET", "MACRO", "ENDMACRO", "DOT", "COMMA", 
			"COLON", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "EQ", "NEQ", "LT", "GT", 
			"LTE", "GTE", "ASSIGN", "PIPE", "AND", "OR", "NOT", "STRING", "NUMBER", 
			"NAME", "STMT_WS", "EXPR_CLOSE", "EXPR_WS", "COMMENT_CLOSE", "COMMENT_TEXT"
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
	public String getGrammarFileName() { return "jinja2Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public jinja2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TemplateContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(jinja2Parser.EOF, 0); }
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_template);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 22L) != 0)) {
				{
				{
				setState(48);
				templateContent();
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(54);
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
	public static class TemplateContentContext extends ParserRuleContext {
		public HtmlTextContext htmlText() {
			return getRuleContext(HtmlTextContext.class,0);
		}
		public ExpressionBlockContext expressionBlock() {
			return getRuleContext(ExpressionBlockContext.class,0);
		}
		public ForBlockContext forBlock() {
			return getRuleContext(ForBlockContext.class,0);
		}
		public IfBlockContext ifBlock() {
			return getRuleContext(IfBlockContext.class,0);
		}
		public ExtendsStatementContext extendsStatement() {
			return getRuleContext(ExtendsStatementContext.class,0);
		}
		public IncludeStatementContext includeStatement() {
			return getRuleContext(IncludeStatementContext.class,0);
		}
		public BlockDefinitionContext blockDefinition() {
			return getRuleContext(BlockDefinitionContext.class,0);
		}
		public SetStatementContext setStatement() {
			return getRuleContext(SetStatementContext.class,0);
		}
		public SetBlockStatementContext setBlockStatement() {
			return getRuleContext(SetBlockStatementContext.class,0);
		}
		public TemplateContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateContent; }
	}

	public final TemplateContentContext templateContent() throws RecognitionException {
		TemplateContentContext _localctx = new TemplateContentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_templateContent);
		try {
			setState(65);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				htmlText();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				expressionBlock();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				forBlock();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(59);
				ifBlock();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(60);
				extendsStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(61);
				includeStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(62);
				blockDefinition();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(63);
				setStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(64);
				setBlockStatement();
				}
				break;
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
	public static class HtmlTextContext extends ParserRuleContext {
		public List<TerminalNode> HTML_TEXT() { return getTokens(jinja2Parser.HTML_TEXT); }
		public TerminalNode HTML_TEXT(int i) {
			return getToken(jinja2Parser.HTML_TEXT, i);
		}
		public HtmlTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlText; }
	}

	public final HtmlTextContext htmlText() throws RecognitionException {
		HtmlTextContext _localctx = new HtmlTextContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_htmlText);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(68); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(67);
					match(HTML_TEXT);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(70); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
	public static class ExpressionBlockContext extends ParserRuleContext {
		public ExpressionContext expr;
		public FilterContext filter;
		public List<FilterContext> filters = new ArrayList<FilterContext>();
		public TerminalNode EXPR_OPEN() { return getToken(jinja2Parser.EXPR_OPEN, 0); }
		public TerminalNode EXPR_CLOSE() { return getToken(jinja2Parser.EXPR_CLOSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> PIPE() { return getTokens(jinja2Parser.PIPE); }
		public TerminalNode PIPE(int i) {
			return getToken(jinja2Parser.PIPE, i);
		}
		public List<FilterContext> filter() {
			return getRuleContexts(FilterContext.class);
		}
		public FilterContext filter(int i) {
			return getRuleContext(FilterContext.class,i);
		}
		public ExpressionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionBlock; }
	}

	public final ExpressionBlockContext expressionBlock() throws RecognitionException {
		ExpressionBlockContext _localctx = new ExpressionBlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expressionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(EXPR_OPEN);
			setState(73);
			((ExpressionBlockContext)_localctx).expr = expression();
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PIPE) {
				{
				{
				setState(74);
				match(PIPE);
				setState(75);
				((ExpressionBlockContext)_localctx).filter = filter();
				((ExpressionBlockContext)_localctx).filters.add(((ExpressionBlockContext)_localctx).filter);
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
			match(EXPR_CLOSE);
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
	public static class ExpressionContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(jinja2Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(jinja2Parser.DOT, i);
		}
		public List<TerminalNode> NAME() { return getTokens(jinja2Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(jinja2Parser.NAME, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			primary();
			setState(88);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(84);
					match(DOT);
					setState(85);
					match(NAME);
					}
					} 
				}
				setState(90);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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
	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CALLEEContext extends PrimaryContext {
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(jinja2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(jinja2Parser.RPAREN, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public CALLEEContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VARContext extends PrimaryContext {
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public VARContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NUMContext extends PrimaryContext {
		public TerminalNode NUMBER() { return getToken(jinja2Parser.NUMBER, 0); }
		public NUMContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class STRING_LITERALContext extends PrimaryContext {
		public TerminalNode STRING() { return getToken(jinja2Parser.STRING, 0); }
		public STRING_LITERALContext(PrimaryContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(jinja2Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(jinja2Parser.RPAREN, 0); }
		public ParenExprContext(PrimaryContext ctx) { copyFrom(ctx); }
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_primary);
		int _la;
		try {
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new NUMContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				match(NUMBER);
				}
				break;
			case 2:
				_localctx = new STRING_LITERALContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				match(STRING);
				}
				break;
			case 3:
				_localctx = new CALLEEContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
				match(NAME);
				setState(94);
				match(LPAREN);
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3848307474432L) != 0)) {
					{
					setState(95);
					argList();
					}
				}

				setState(98);
				match(RPAREN);
				}
				break;
			case 4:
				_localctx = new VARContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(99);
				match(NAME);
				}
				break;
			case 5:
				_localctx = new ParenExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(100);
				match(LPAREN);
				setState(101);
				expression();
				setState(102);
				match(RPAREN);
				}
				break;
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
	public static class ArgListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jinja2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jinja2Parser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			expression();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(107);
				match(COMMA);
				setState(108);
				expression();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class FilterContext extends ParserRuleContext {
		public Token name;
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(jinja2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(jinja2Parser.RPAREN, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_filter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			((FilterContext)_localctx).name = match(NAME);
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(115);
				match(LPAREN);
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 3848307474432L) != 0)) {
					{
					setState(116);
					argList();
					}
				}

				setState(119);
				match(RPAREN);
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
	public static class ForBlockContext extends ParserRuleContext {
		public ForStartContext forStart() {
			return getRuleContext(ForStartContext.class,0);
		}
		public ForEndContext forEnd() {
			return getRuleContext(ForEndContext.class,0);
		}
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public ForBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forBlock; }
	}

	public final ForBlockContext forBlock() throws RecognitionException {
		ForBlockContext _localctx = new ForBlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_forBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			forStart();
			setState(126);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(123);
					templateContent();
					}
					} 
				}
				setState(128);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(129);
			forEnd();
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
	public static class ForStartContext extends ParserRuleContext {
		public Token target;
		public ExpressionContext iterable;
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode FOR() { return getToken(jinja2Parser.FOR, 0); }
		public TerminalNode IN() { return getToken(jinja2Parser.IN, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForStartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStart; }
	}

	public final ForStartContext forStart() throws RecognitionException {
		ForStartContext _localctx = new ForStartContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_forStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(STMT_OPEN);
			setState(132);
			match(FOR);
			setState(133);
			((ForStartContext)_localctx).target = match(NAME);
			setState(134);
			match(IN);
			setState(135);
			((ForStartContext)_localctx).iterable = expression();
			setState(136);
			match(STMT_CLOSE);
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
	public static class ForEndContext extends ParserRuleContext {
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode ENDFOR() { return getToken(jinja2Parser.ENDFOR, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public ForEndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forEnd; }
	}

	public final ForEndContext forEnd() throws RecognitionException {
		ForEndContext _localctx = new ForEndContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_forEnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(STMT_OPEN);
			setState(139);
			match(ENDFOR);
			setState(140);
			match(STMT_CLOSE);
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
	public static class IfBlockContext extends ParserRuleContext {
		public IfStartContext ifStart() {
			return getRuleContext(IfStartContext.class,0);
		}
		public IfEndContext ifEnd() {
			return getRuleContext(IfEndContext.class,0);
		}
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public List<ElifBranchContext> elifBranch() {
			return getRuleContexts(ElifBranchContext.class);
		}
		public ElifBranchContext elifBranch(int i) {
			return getRuleContext(ElifBranchContext.class,i);
		}
		public ElseBranchContext elseBranch() {
			return getRuleContext(ElseBranchContext.class,0);
		}
		public IfBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifBlock; }
	}

	public final IfBlockContext ifBlock() throws RecognitionException {
		IfBlockContext _localctx = new IfBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			ifStart();
			setState(146);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(143);
					templateContent();
					}
					} 
				}
				setState(148);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(152);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(149);
					elifBranch();
					}
					} 
				}
				setState(154);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(155);
				elseBranch();
				}
				break;
			}
			setState(158);
			ifEnd();
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
	public static class IfStartContext extends ParserRuleContext {
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode IF() { return getToken(jinja2Parser.IF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public IfStartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStart; }
	}

	public final IfStartContext ifStart() throws RecognitionException {
		IfStartContext _localctx = new IfStartContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ifStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(STMT_OPEN);
			setState(161);
			match(IF);
			setState(162);
			condition(0);
			setState(163);
			match(STMT_CLOSE);
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
	public static class ElifBranchContext extends ParserRuleContext {
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode ELIF() { return getToken(jinja2Parser.ELIF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public ElifBranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elifBranch; }
	}

	public final ElifBranchContext elifBranch() throws RecognitionException {
		ElifBranchContext _localctx = new ElifBranchContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_elifBranch);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(STMT_OPEN);
			setState(166);
			match(ELIF);
			setState(167);
			condition(0);
			setState(168);
			match(STMT_CLOSE);
			setState(172);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(169);
					templateContent();
					}
					} 
				}
				setState(174);
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
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseBranchContext extends ParserRuleContext {
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode ELSE() { return getToken(jinja2Parser.ELSE, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public ElseBranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseBranch; }
	}

	public final ElseBranchContext elseBranch() throws RecognitionException {
		ElseBranchContext _localctx = new ElseBranchContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_elseBranch);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(STMT_OPEN);
			setState(176);
			match(ELSE);
			setState(177);
			match(STMT_CLOSE);
			setState(181);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(178);
					templateContent();
					}
					} 
				}
				setState(183);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
	public static class IfEndContext extends ParserRuleContext {
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode ENDIF() { return getToken(jinja2Parser.ENDIF, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public IfEndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifEnd; }
	}

	public final IfEndContext ifEnd() throws RecognitionException {
		IfEndContext _localctx = new IfEndContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifEnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(STMT_OPEN);
			setState(185);
			match(ENDIF);
			setState(186);
			match(STMT_CLOSE);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public TerminalNode NOT() { return getToken(jinja2Parser.NOT, 0); }
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode AND() { return getToken(jinja2Parser.AND, 0); }
		public TerminalNode OR() { return getToken(jinja2Parser.OR, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	}

	public final ConditionContext condition() throws RecognitionException {
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case STRING:
			case NUMBER:
			case NAME:
				{
				setState(189);
				expression();
				setState(193);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(190);
					compOp();
					setState(191);
					expression();
					}
					break;
				}
				}
				break;
			case NOT:
				{
				setState(195);
				match(NOT);
				setState(196);
				condition(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(207);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(205);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new ConditionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(199);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(200);
						match(AND);
						setState(201);
						condition(4);
						}
						break;
					case 2:
						{
						_localctx = new ConditionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(202);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(203);
						match(OR);
						setState(204);
						condition(3);
						}
						break;
					}
					} 
				}
				setState(209);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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
	public static class CompOpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(jinja2Parser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(jinja2Parser.NEQ, 0); }
		public TerminalNode LT() { return getToken(jinja2Parser.LT, 0); }
		public TerminalNode GT() { return getToken(jinja2Parser.GT, 0); }
		public TerminalNode LTE() { return getToken(jinja2Parser.LTE, 0); }
		public TerminalNode GTE() { return getToken(jinja2Parser.GTE, 0); }
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16911433728L) != 0)) ) {
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
	public static class ExtendsStatementContext extends ParserRuleContext {
		public Token templateName;
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode EXTENDS() { return getToken(jinja2Parser.EXTENDS, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public TerminalNode STRING() { return getToken(jinja2Parser.STRING, 0); }
		public ExtendsStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extendsStatement; }
	}

	public final ExtendsStatementContext extendsStatement() throws RecognitionException {
		ExtendsStatementContext _localctx = new ExtendsStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_extendsStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(STMT_OPEN);
			setState(213);
			match(EXTENDS);
			setState(214);
			((ExtendsStatementContext)_localctx).templateName = match(STRING);
			setState(215);
			match(STMT_CLOSE);
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
	public static class IncludeStatementContext extends ParserRuleContext {
		public Token templateName;
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode INCLUDE() { return getToken(jinja2Parser.INCLUDE, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public TerminalNode STRING() { return getToken(jinja2Parser.STRING, 0); }
		public IncludeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includeStatement; }
	}

	public final IncludeStatementContext includeStatement() throws RecognitionException {
		IncludeStatementContext _localctx = new IncludeStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_includeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(STMT_OPEN);
			setState(218);
			match(INCLUDE);
			setState(219);
			((IncludeStatementContext)_localctx).templateName = match(STRING);
			setState(220);
			match(STMT_CLOSE);
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
	public static class BlockDefinitionContext extends ParserRuleContext {
		public List<TerminalNode> STMT_OPEN() { return getTokens(jinja2Parser.STMT_OPEN); }
		public TerminalNode STMT_OPEN(int i) {
			return getToken(jinja2Parser.STMT_OPEN, i);
		}
		public TerminalNode BLOCK() { return getToken(jinja2Parser.BLOCK, 0); }
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public List<TerminalNode> STMT_CLOSE() { return getTokens(jinja2Parser.STMT_CLOSE); }
		public TerminalNode STMT_CLOSE(int i) {
			return getToken(jinja2Parser.STMT_CLOSE, i);
		}
		public TerminalNode ENDBLOCK() { return getToken(jinja2Parser.ENDBLOCK, 0); }
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public BlockDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockDefinition; }
	}

	public final BlockDefinitionContext blockDefinition() throws RecognitionException {
		BlockDefinitionContext _localctx = new BlockDefinitionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_blockDefinition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(STMT_OPEN);
			setState(223);
			match(BLOCK);
			setState(224);
			match(NAME);
			setState(225);
			match(STMT_CLOSE);
			setState(229);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(226);
					templateContent();
					}
					} 
				}
				setState(231);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			setState(232);
			match(STMT_OPEN);
			setState(233);
			match(ENDBLOCK);
			setState(234);
			match(STMT_CLOSE);
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
	public static class SetTargetContext extends ParserRuleContext {
		public SetTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setTarget; }
	 
		public SetTargetContext() { }
		public void copyFrom(SetTargetContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTargetContext extends SetTargetContext {
		public Token name;
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public SimpleTargetContext(SetTargetContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NamespaceTargetContext extends SetTargetContext {
		public Token namespace;
		public Token NAME;
		public List<Token> attributes = new ArrayList<Token>();
		public List<TerminalNode> NAME() { return getTokens(jinja2Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(jinja2Parser.NAME, i);
		}
		public List<TerminalNode> DOT() { return getTokens(jinja2Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(jinja2Parser.DOT, i);
		}
		public NamespaceTargetContext(SetTargetContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TupleTargetContext extends SetTargetContext {
		public Token first;
		public Token NAME;
		public List<Token> rest = new ArrayList<Token>();
		public List<TerminalNode> NAME() { return getTokens(jinja2Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(jinja2Parser.NAME, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jinja2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jinja2Parser.COMMA, i);
		}
		public TupleTargetContext(SetTargetContext ctx) { copyFrom(ctx); }
	}

	public final SetTargetContext setTarget() throws RecognitionException {
		SetTargetContext _localctx = new SetTargetContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_setTarget);
		int _la;
		try {
			setState(251);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new SimpleTargetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(236);
				((SimpleTargetContext)_localctx).name = match(NAME);
				}
				break;
			case 2:
				_localctx = new TupleTargetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				((TupleTargetContext)_localctx).first = match(NAME);
				setState(240); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(238);
					match(COMMA);
					setState(239);
					((TupleTargetContext)_localctx).NAME = match(NAME);
					((TupleTargetContext)_localctx).rest.add(((TupleTargetContext)_localctx).NAME);
					}
					}
					setState(242); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==COMMA );
				}
				break;
			case 3:
				_localctx = new NamespaceTargetContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(244);
				((NamespaceTargetContext)_localctx).namespace = match(NAME);
				setState(247); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(245);
					match(DOT);
					setState(246);
					((NamespaceTargetContext)_localctx).NAME = match(NAME);
					((NamespaceTargetContext)_localctx).attributes.add(((NamespaceTargetContext)_localctx).NAME);
					}
					}
					setState(249); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DOT );
				}
				break;
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
	public static class SetStatementContext extends ParserRuleContext {
		public ExpressionContext value;
		public TerminalNode STMT_OPEN() { return getToken(jinja2Parser.STMT_OPEN, 0); }
		public TerminalNode SET() { return getToken(jinja2Parser.SET, 0); }
		public SetTargetContext setTarget() {
			return getRuleContext(SetTargetContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(jinja2Parser.ASSIGN, 0); }
		public TerminalNode STMT_CLOSE() { return getToken(jinja2Parser.STMT_CLOSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SetStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setStatement; }
	}

	public final SetStatementContext setStatement() throws RecognitionException {
		SetStatementContext _localctx = new SetStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_setStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(STMT_OPEN);
			setState(254);
			match(SET);
			setState(255);
			setTarget();
			setState(256);
			match(ASSIGN);
			setState(257);
			((SetStatementContext)_localctx).value = expression();
			setState(258);
			match(STMT_CLOSE);
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
	public static class SetBlockStatementContext extends ParserRuleContext {
		public TemplateContentContext value;
		public List<TerminalNode> STMT_OPEN() { return getTokens(jinja2Parser.STMT_OPEN); }
		public TerminalNode STMT_OPEN(int i) {
			return getToken(jinja2Parser.STMT_OPEN, i);
		}
		public TerminalNode SET() { return getToken(jinja2Parser.SET, 0); }
		public TerminalNode NAME() { return getToken(jinja2Parser.NAME, 0); }
		public List<TerminalNode> STMT_CLOSE() { return getTokens(jinja2Parser.STMT_CLOSE); }
		public TerminalNode STMT_CLOSE(int i) {
			return getToken(jinja2Parser.STMT_CLOSE, i);
		}
		public TerminalNode ENDSET() { return getToken(jinja2Parser.ENDSET, 0); }
		public List<TemplateContentContext> templateContent() {
			return getRuleContexts(TemplateContentContext.class);
		}
		public TemplateContentContext templateContent(int i) {
			return getRuleContext(TemplateContentContext.class,i);
		}
		public SetBlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setBlockStatement; }
	}

	public final SetBlockStatementContext setBlockStatement() throws RecognitionException {
		SetBlockStatementContext _localctx = new SetBlockStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_setBlockStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(STMT_OPEN);
			setState(261);
			match(SET);
			setState(262);
			match(NAME);
			setState(263);
			match(STMT_CLOSE);
			setState(267);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(264);
					((SetBlockStatementContext)_localctx).value = templateContent();
					}
					} 
				}
				setState(269);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			setState(270);
			match(STMT_OPEN);
			setState(271);
			match(ENDSET);
			setState(272);
			match(STMT_CLOSE);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return condition_sempred((ConditionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001.\u0113\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0005\u0000"+
		"2\b\u0000\n\u0000\f\u00005\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001B\b\u0001\u0001\u0002\u0004\u0002"+
		"E\b\u0002\u000b\u0002\f\u0002F\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003M\b\u0003\n\u0003\f\u0003P\t\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004W\b\u0004\n\u0004"+
		"\f\u0004Z\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005a\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0003\u0005i\b\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006n\b\u0006\n\u0006\f\u0006q\t\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007v\b\u0007\u0001\u0007\u0003"+
		"\u0007y\b\u0007\u0001\b\u0001\b\u0005\b}\b\b\n\b\f\b\u0080\t\b\u0001\b"+
		"\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0005\u000b\u0091\b"+
		"\u000b\n\u000b\f\u000b\u0094\t\u000b\u0001\u000b\u0005\u000b\u0097\b\u000b"+
		"\n\u000b\f\u000b\u009a\t\u000b\u0001\u000b\u0003\u000b\u009d\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0005\r\u00ab\b\r\n\r\f\r\u00ae\t\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00b4\b\u000e\n\u000e"+
		"\f\u000e\u00b7\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010"+
		"\u00c2\b\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00c6\b\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005"+
		"\u0010\u00ce\b\u0010\n\u0010\f\u0010\u00d1\t\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u00e4\b\u0014\n\u0014"+
		"\f\u0014\u00e7\t\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0004\u0015\u00f1\b\u0015"+
		"\u000b\u0015\f\u0015\u00f2\u0001\u0015\u0001\u0015\u0001\u0015\u0004\u0015"+
		"\u00f8\b\u0015\u000b\u0015\f\u0015\u00f9\u0003\u0015\u00fc\b\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0005"+
		"\u0017\u010a\b\u0017\n\u0017\f\u0017\u010d\t\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0000\u0001 \u0018\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.\u0000\u0001\u0001\u0000\u001c!\u011e\u00003\u0001\u0000\u0000\u0000"+
		"\u0002A\u0001\u0000\u0000\u0000\u0004D\u0001\u0000\u0000\u0000\u0006H"+
		"\u0001\u0000\u0000\u0000\bS\u0001\u0000\u0000\u0000\nh\u0001\u0000\u0000"+
		"\u0000\fj\u0001\u0000\u0000\u0000\u000er\u0001\u0000\u0000\u0000\u0010"+
		"z\u0001\u0000\u0000\u0000\u0012\u0083\u0001\u0000\u0000\u0000\u0014\u008a"+
		"\u0001\u0000\u0000\u0000\u0016\u008e\u0001\u0000\u0000\u0000\u0018\u00a0"+
		"\u0001\u0000\u0000\u0000\u001a\u00a5\u0001\u0000\u0000\u0000\u001c\u00af"+
		"\u0001\u0000\u0000\u0000\u001e\u00b8\u0001\u0000\u0000\u0000 \u00c5\u0001"+
		"\u0000\u0000\u0000\"\u00d2\u0001\u0000\u0000\u0000$\u00d4\u0001\u0000"+
		"\u0000\u0000&\u00d9\u0001\u0000\u0000\u0000(\u00de\u0001\u0000\u0000\u0000"+
		"*\u00fb\u0001\u0000\u0000\u0000,\u00fd\u0001\u0000\u0000\u0000.\u0104"+
		"\u0001\u0000\u0000\u000002\u0003\u0002\u0001\u000010\u0001\u0000\u0000"+
		"\u000025\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000"+
		"\u0000\u000046\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005"+
		"\u0000\u0000\u00017\u0001\u0001\u0000\u0000\u00008B\u0003\u0004\u0002"+
		"\u00009B\u0003\u0006\u0003\u0000:B\u0003\u0010\b\u0000;B\u0003\u0016\u000b"+
		"\u0000<B\u0003$\u0012\u0000=B\u0003&\u0013\u0000>B\u0003(\u0014\u0000"+
		"?B\u0003,\u0016\u0000@B\u0003.\u0017\u0000A8\u0001\u0000\u0000\u0000A"+
		"9\u0001\u0000\u0000\u0000A:\u0001\u0000\u0000\u0000A;\u0001\u0000\u0000"+
		"\u0000A<\u0001\u0000\u0000\u0000A=\u0001\u0000\u0000\u0000A>\u0001\u0000"+
		"\u0000\u0000A?\u0001\u0000\u0000\u0000A@\u0001\u0000\u0000\u0000B\u0003"+
		"\u0001\u0000\u0000\u0000CE\u0005\u0004\u0000\u0000DC\u0001\u0000\u0000"+
		"\u0000EF\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000FG\u0001\u0000"+
		"\u0000\u0000G\u0005\u0001\u0000\u0000\u0000HI\u0005\u0002\u0000\u0000"+
		"IN\u0003\b\u0004\u0000JK\u0005#\u0000\u0000KM\u0003\u000e\u0007\u0000"+
		"LJ\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000"+
		"\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000PN\u0001\u0000"+
		"\u0000\u0000QR\u0005+\u0000\u0000R\u0007\u0001\u0000\u0000\u0000SX\u0003"+
		"\n\u0005\u0000TU\u0005\u0015\u0000\u0000UW\u0005)\u0000\u0000VT\u0001"+
		"\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000"+
		"XY\u0001\u0000\u0000\u0000Y\t\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000"+
		"\u0000[i\u0005(\u0000\u0000\\i\u0005\'\u0000\u0000]^\u0005)\u0000\u0000"+
		"^`\u0005\u0018\u0000\u0000_a\u0003\f\u0006\u0000`_\u0001\u0000\u0000\u0000"+
		"`a\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bi\u0005\u0019\u0000"+
		"\u0000ci\u0005)\u0000\u0000de\u0005\u0018\u0000\u0000ef\u0003\b\u0004"+
		"\u0000fg\u0005\u0019\u0000\u0000gi\u0001\u0000\u0000\u0000h[\u0001\u0000"+
		"\u0000\u0000h\\\u0001\u0000\u0000\u0000h]\u0001\u0000\u0000\u0000hc\u0001"+
		"\u0000\u0000\u0000hd\u0001\u0000\u0000\u0000i\u000b\u0001\u0000\u0000"+
		"\u0000jo\u0003\b\u0004\u0000kl\u0005\u0016\u0000\u0000ln\u0003\b\u0004"+
		"\u0000mk\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000"+
		"\u0000\u0000op\u0001\u0000\u0000\u0000p\r\u0001\u0000\u0000\u0000qo\u0001"+
		"\u0000\u0000\u0000rx\u0005)\u0000\u0000su\u0005\u0018\u0000\u0000tv\u0003"+
		"\f\u0006\u0000ut\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001"+
		"\u0000\u0000\u0000wy\u0005\u0019\u0000\u0000xs\u0001\u0000\u0000\u0000"+
		"xy\u0001\u0000\u0000\u0000y\u000f\u0001\u0000\u0000\u0000z~\u0003\u0012"+
		"\t\u0000{}\u0003\u0002\u0001\u0000|{\u0001\u0000\u0000\u0000}\u0080\u0001"+
		"\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000"+
		"\u0000\u007f\u0081\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000"+
		"\u0081\u0082\u0003\u0014\n\u0000\u0082\u0011\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0005\u0001\u0000\u0000\u0084\u0085\u0005\u0006\u0000\u0000\u0085"+
		"\u0086\u0005)\u0000\u0000\u0086\u0087\u0005\b\u0000\u0000\u0087\u0088"+
		"\u0003\b\u0004\u0000\u0088\u0089\u0005\u0005\u0000\u0000\u0089\u0013\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0005\u0001\u0000\u0000\u008b\u008c\u0005"+
		"\u0007\u0000\u0000\u008c\u008d\u0005\u0005\u0000\u0000\u008d\u0015\u0001"+
		"\u0000\u0000\u0000\u008e\u0092\u0003\u0018\f\u0000\u008f\u0091\u0003\u0002"+
		"\u0001\u0000\u0090\u008f\u0001\u0000\u0000\u0000\u0091\u0094\u0001\u0000"+
		"\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000"+
		"\u0000\u0000\u0093\u0098\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000"+
		"\u0000\u0000\u0095\u0097\u0003\u001a\r\u0000\u0096\u0095\u0001\u0000\u0000"+
		"\u0000\u0097\u009a\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009c\u0001\u0000\u0000"+
		"\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009b\u009d\u0003\u001c\u000e"+
		"\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000\u0000"+
		"\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009f\u0003\u001e\u000f"+
		"\u0000\u009f\u0017\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005\u0001\u0000"+
		"\u0000\u00a1\u00a2\u0005\t\u0000\u0000\u00a2\u00a3\u0003 \u0010\u0000"+
		"\u00a3\u00a4\u0005\u0005\u0000\u0000\u00a4\u0019\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a6\u0005\u0001\u0000\u0000\u00a6\u00a7\u0005\n\u0000\u0000\u00a7"+
		"\u00a8\u0003 \u0010\u0000\u00a8\u00ac\u0005\u0005\u0000\u0000\u00a9\u00ab"+
		"\u0003\u0002\u0001\u0000\u00aa\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ae"+
		"\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ac\u00ad"+
		"\u0001\u0000\u0000\u0000\u00ad\u001b\u0001\u0000\u0000\u0000\u00ae\u00ac"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0005\u0001\u0000\u0000\u00b0\u00b1"+
		"\u0005\u000b\u0000\u0000\u00b1\u00b5\u0005\u0005\u0000\u0000\u00b2\u00b4"+
		"\u0003\u0002\u0001\u0000\u00b3\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b5\u00b6"+
		"\u0001\u0000\u0000\u0000\u00b6\u001d\u0001\u0000\u0000\u0000\u00b7\u00b5"+
		"\u0001\u0000\u0000\u0000\u00b8\u00b9\u0005\u0001\u0000\u0000\u00b9\u00ba"+
		"\u0005\f\u0000\u0000\u00ba\u00bb\u0005\u0005\u0000\u0000\u00bb\u001f\u0001"+
		"\u0000\u0000\u0000\u00bc\u00bd\u0006\u0010\uffff\uffff\u0000\u00bd\u00c1"+
		"\u0003\b\u0004\u0000\u00be\u00bf\u0003\"\u0011\u0000\u00bf\u00c0\u0003"+
		"\b\u0004\u0000\u00c0\u00c2\u0001\u0000\u0000\u0000\u00c1\u00be\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c3\u00c4\u0005&\u0000\u0000\u00c4\u00c6\u0003 \u0010\u0001"+
		"\u00c5\u00bc\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000"+
		"\u00c6\u00cf\u0001\u0000\u0000\u0000\u00c7\u00c8\n\u0003\u0000\u0000\u00c8"+
		"\u00c9\u0005$\u0000\u0000\u00c9\u00ce\u0003 \u0010\u0004\u00ca\u00cb\n"+
		"\u0002\u0000\u0000\u00cb\u00cc\u0005%\u0000\u0000\u00cc\u00ce\u0003 \u0010"+
		"\u0003\u00cd\u00c7\u0001\u0000\u0000\u0000\u00cd\u00ca\u0001\u0000\u0000"+
		"\u0000\u00ce\u00d1\u0001\u0000\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000"+
		"\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0!\u0001\u0000\u0000\u0000"+
		"\u00d1\u00cf\u0001\u0000\u0000\u0000\u00d2\u00d3\u0007\u0000\u0000\u0000"+
		"\u00d3#\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005\u0001\u0000\u0000\u00d5"+
		"\u00d6\u0005\u000f\u0000\u0000\u00d6\u00d7\u0005\'\u0000\u0000\u00d7\u00d8"+
		"\u0005\u0005\u0000\u0000\u00d8%\u0001\u0000\u0000\u0000\u00d9\u00da\u0005"+
		"\u0001\u0000\u0000\u00da\u00db\u0005\u0010\u0000\u0000\u00db\u00dc\u0005"+
		"\'\u0000\u0000\u00dc\u00dd\u0005\u0005\u0000\u0000\u00dd\'\u0001\u0000"+
		"\u0000\u0000\u00de\u00df\u0005\u0001\u0000\u0000\u00df\u00e0\u0005\r\u0000"+
		"\u0000\u00e0\u00e1\u0005)\u0000\u0000\u00e1\u00e5\u0005\u0005\u0000\u0000"+
		"\u00e2\u00e4\u0003\u0002\u0001\u0000\u00e3\u00e2\u0001\u0000\u0000\u0000"+
		"\u00e4\u00e7\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000"+
		"\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6\u00e8\u0001\u0000\u0000\u0000"+
		"\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005\u0001\u0000\u0000"+
		"\u00e9\u00ea\u0005\u000e\u0000\u0000\u00ea\u00eb\u0005\u0005\u0000\u0000"+
		"\u00eb)\u0001\u0000\u0000\u0000\u00ec\u00fc\u0005)\u0000\u0000\u00ed\u00f0"+
		"\u0005)\u0000\u0000\u00ee\u00ef\u0005\u0016\u0000\u0000\u00ef\u00f1\u0005"+
		")\u0000\u0000\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000"+
		"\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f2\u00f3\u0001\u0000"+
		"\u0000\u0000\u00f3\u00fc\u0001\u0000\u0000\u0000\u00f4\u00f7\u0005)\u0000"+
		"\u0000\u00f5\u00f6\u0005\u0015\u0000\u0000\u00f6\u00f8\u0005)\u0000\u0000"+
		"\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000"+
		"\u00f9\u00f7\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001\u0000\u0000\u0000"+
		"\u00fa\u00fc\u0001\u0000\u0000\u0000\u00fb\u00ec\u0001\u0000\u0000\u0000"+
		"\u00fb\u00ed\u0001\u0000\u0000\u0000\u00fb\u00f4\u0001\u0000\u0000\u0000"+
		"\u00fc+\u0001\u0000\u0000\u0000\u00fd\u00fe\u0005\u0001\u0000\u0000\u00fe"+
		"\u00ff\u0005\u0011\u0000\u0000\u00ff\u0100\u0003*\u0015\u0000\u0100\u0101"+
		"\u0005\"\u0000\u0000\u0101\u0102\u0003\b\u0004\u0000\u0102\u0103\u0005"+
		"\u0005\u0000\u0000\u0103-\u0001\u0000\u0000\u0000\u0104\u0105\u0005\u0001"+
		"\u0000\u0000\u0105\u0106\u0005\u0011\u0000\u0000\u0106\u0107\u0005)\u0000"+
		"\u0000\u0107\u010b\u0005\u0005\u0000\u0000\u0108\u010a\u0003\u0002\u0001"+
		"\u0000\u0109\u0108\u0001\u0000\u0000\u0000\u010a\u010d\u0001\u0000\u0000"+
		"\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000"+
		"\u0000\u010c\u010e\u0001\u0000\u0000\u0000\u010d\u010b\u0001\u0000\u0000"+
		"\u0000\u010e\u010f\u0005\u0001\u0000\u0000\u010f\u0110\u0005\u0012\u0000"+
		"\u0000\u0110\u0111\u0005\u0005\u0000\u0000\u0111/\u0001\u0000\u0000\u0000"+
		"\u00193AFNX`houx~\u0092\u0098\u009c\u00ac\u00b5\u00c1\u00c5\u00cd\u00cf"+
		"\u00e5\u00f2\u00f9\u00fb\u010b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}