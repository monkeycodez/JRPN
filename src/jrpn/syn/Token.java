package jrpn.syn;

public class Token {

	public final int lineno;
	public final String text;
	public final TType type;
	public final String source;

	public Token(TType type, int lineno, String source) {
		super();
		this.lineno = lineno;
		this.type = type;
		this.source = source;
		text = null;
	}

	public Token(TType type, String text, int lineno, String source) {
		super();
		this.lineno = lineno;
		this.text = text;
		this.type = type;
		this.source = source;
	}

	public String toString() {
		if (text == null) {
			return type.name() + "@" + source + ":" + lineno;
		}
		return text + " @" + source + ":" + lineno;
	}

}
