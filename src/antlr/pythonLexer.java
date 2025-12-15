// Generated from C:/Users/HP/IdeaProjects/antlr_course/grammars/pythonLexer.g4 by ANTLR 4.13.2

   package antlr;
   import com.yuvalshavit.antlr4.DenterHelper;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class pythonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, POW=2, MUL=3, DIV=4, PLUS=5, SUB=6, EQ=7, NEQ=8, LT=9, GT=10, LTE=11, 
		GTE=12, AND=13, OR=14, NOT=15, IF=16, ELIF=17, ELSE=18, PRINT=19, TRUE=20, 
		FALSE=21, ASSIGN=22, COLON=23, LPAREN=24, RPAREN=25, NAME=26, NL=27, WS=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"NUM", "POW", "MUL", "DIV", "PLUS", "SUB", "EQ", "NEQ", "LT", "GT", "LTE", 
			"GTE", "AND", "OR", "NOT", "IF", "ELIF", "ELSE", "PRINT", "TRUE", "FALSE", 
			"ASSIGN", "COLON", "LPAREN", "RPAREN", "NAME", "NL", "WS"
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
			"FALSE", "ASSIGN", "COLON", "LPAREN", "RPAREN", "NAME", "NL", "WS"
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


	  private final DenterHelper denter = DenterHelper.builder()
	    .nl(NL)
	    .indent(pythonParser.INDENT)
	    .dedent(pythonParser.DEDENT)
	    .pullToken(pythonLexer.super::nextToken);

	  @Override
	  public Token nextToken() {
	    return denter.nextToken();
	  }


	public pythonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "pythonLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001c\u00a2\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0001\u0000\u0004\u0000;\b\u0000\u000b\u0000"+
		"\f\u0000<\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0001\u0019\u0005\u0019\u008d\b\u0019\n\u0019\f\u0019\u0090"+
		"\t\u0019\u0001\u001a\u0003\u001a\u0093\b\u001a\u0001\u001a\u0001\u001a"+
		"\u0005\u001a\u0097\b\u001a\n\u001a\f\u001a\u009a\t\u001a\u0001\u001b\u0004"+
		"\u001b\u009d\b\u001b\u000b\u001b\f\u001b\u009e\u0001\u001b\u0001\u001b"+
		"\u0000\u0000\u001c\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005"+
		"\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019"+
		"\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015"+
		"+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c\u0001\u0000\u0004\u0001"+
		"\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u0002\u0000\t\t  \u00a6"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000"+
		"\u0001:\u0001\u0000\u0000\u0000\u0003>\u0001\u0000\u0000\u0000\u0005A"+
		"\u0001\u0000\u0000\u0000\u0007C\u0001\u0000\u0000\u0000\tE\u0001\u0000"+
		"\u0000\u0000\u000bG\u0001\u0000\u0000\u0000\rI\u0001\u0000\u0000\u0000"+
		"\u000fL\u0001\u0000\u0000\u0000\u0011O\u0001\u0000\u0000\u0000\u0013Q"+
		"\u0001\u0000\u0000\u0000\u0015S\u0001\u0000\u0000\u0000\u0017V\u0001\u0000"+
		"\u0000\u0000\u0019Y\u0001\u0000\u0000\u0000\u001b]\u0001\u0000\u0000\u0000"+
		"\u001d`\u0001\u0000\u0000\u0000\u001fd\u0001\u0000\u0000\u0000!g\u0001"+
		"\u0000\u0000\u0000#l\u0001\u0000\u0000\u0000%q\u0001\u0000\u0000\u0000"+
		"\'w\u0001\u0000\u0000\u0000)|\u0001\u0000\u0000\u0000+\u0082\u0001\u0000"+
		"\u0000\u0000-\u0084\u0001\u0000\u0000\u0000/\u0086\u0001\u0000\u0000\u0000"+
		"1\u0088\u0001\u0000\u0000\u00003\u008a\u0001\u0000\u0000\u00005\u0092"+
		"\u0001\u0000\u0000\u00007\u009c\u0001\u0000\u0000\u00009;\u0007\u0000"+
		"\u0000\u0000:9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<:\u0001"+
		"\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u0002\u0001\u0000\u0000"+
		"\u0000>?\u0005*\u0000\u0000?@\u0005*\u0000\u0000@\u0004\u0001\u0000\u0000"+
		"\u0000AB\u0005*\u0000\u0000B\u0006\u0001\u0000\u0000\u0000CD\u0005/\u0000"+
		"\u0000D\b\u0001\u0000\u0000\u0000EF\u0005+\u0000\u0000F\n\u0001\u0000"+
		"\u0000\u0000GH\u0005-\u0000\u0000H\f\u0001\u0000\u0000\u0000IJ\u0005="+
		"\u0000\u0000JK\u0005=\u0000\u0000K\u000e\u0001\u0000\u0000\u0000LM\u0005"+
		"!\u0000\u0000MN\u0005=\u0000\u0000N\u0010\u0001\u0000\u0000\u0000OP\u0005"+
		"<\u0000\u0000P\u0012\u0001\u0000\u0000\u0000QR\u0005>\u0000\u0000R\u0014"+
		"\u0001\u0000\u0000\u0000ST\u0005<\u0000\u0000TU\u0005=\u0000\u0000U\u0016"+
		"\u0001\u0000\u0000\u0000VW\u0005>\u0000\u0000WX\u0005=\u0000\u0000X\u0018"+
		"\u0001\u0000\u0000\u0000YZ\u0005a\u0000\u0000Z[\u0005n\u0000\u0000[\\"+
		"\u0005d\u0000\u0000\\\u001a\u0001\u0000\u0000\u0000]^\u0005o\u0000\u0000"+
		"^_\u0005r\u0000\u0000_\u001c\u0001\u0000\u0000\u0000`a\u0005n\u0000\u0000"+
		"ab\u0005o\u0000\u0000bc\u0005t\u0000\u0000c\u001e\u0001\u0000\u0000\u0000"+
		"de\u0005i\u0000\u0000ef\u0005f\u0000\u0000f \u0001\u0000\u0000\u0000g"+
		"h\u0005e\u0000\u0000hi\u0005l\u0000\u0000ij\u0005i\u0000\u0000jk\u0005"+
		"f\u0000\u0000k\"\u0001\u0000\u0000\u0000lm\u0005e\u0000\u0000mn\u0005"+
		"l\u0000\u0000no\u0005s\u0000\u0000op\u0005e\u0000\u0000p$\u0001\u0000"+
		"\u0000\u0000qr\u0005p\u0000\u0000rs\u0005r\u0000\u0000st\u0005i\u0000"+
		"\u0000tu\u0005n\u0000\u0000uv\u0005t\u0000\u0000v&\u0001\u0000\u0000\u0000"+
		"wx\u0005T\u0000\u0000xy\u0005r\u0000\u0000yz\u0005u\u0000\u0000z{\u0005"+
		"e\u0000\u0000{(\u0001\u0000\u0000\u0000|}\u0005F\u0000\u0000}~\u0005a"+
		"\u0000\u0000~\u007f\u0005l\u0000\u0000\u007f\u0080\u0005s\u0000\u0000"+
		"\u0080\u0081\u0005e\u0000\u0000\u0081*\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0005=\u0000\u0000\u0083,\u0001\u0000\u0000\u0000\u0084\u0085\u0005:"+
		"\u0000\u0000\u0085.\u0001\u0000\u0000\u0000\u0086\u0087\u0005(\u0000\u0000"+
		"\u00870\u0001\u0000\u0000\u0000\u0088\u0089\u0005)\u0000\u0000\u00892"+
		"\u0001\u0000\u0000\u0000\u008a\u008e\u0007\u0001\u0000\u0000\u008b\u008d"+
		"\u0007\u0002\u0000\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008d\u0090"+
		"\u0001\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000\u008e\u008f"+
		"\u0001\u0000\u0000\u0000\u008f4\u0001\u0000\u0000\u0000\u0090\u008e\u0001"+
		"\u0000\u0000\u0000\u0091\u0093\u0005\r\u0000\u0000\u0092\u0091\u0001\u0000"+
		"\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000"+
		"\u0000\u0000\u0094\u0098\u0005\n\u0000\u0000\u0095\u0097\u0005 \u0000"+
		"\u0000\u0096\u0095\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000\u0000"+
		"\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000"+
		"\u0000\u00996\u0001\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000"+
		"\u009b\u009d\u0007\u0003\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000"+
		"\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000"+
		"\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a0\u00a1\u0006\u001b\u0000\u0000\u00a18\u0001\u0000\u0000\u0000\u0006"+
		"\u0000<\u008e\u0092\u0098\u009e\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}