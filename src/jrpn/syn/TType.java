package jrpn.syn;

import java.util.*;

public enum TType {

	LPAREN('('),
	RPAREN(')'),
	COMMA(','),
	DOT('.'),
	LBRACE('{'),
	RBRACE('}'),
	LBRACK('['),
	RBRACK(']'),
	COLON(':'),
	EXCL('!'),
	BSLASH('\\'),
	SET("set"),
	FUNC("func"),
	POP("pop"),
	CALL("call"),
	PRINTLN("println"),
	EPRINTLN("eprintln"),
	IDEN(),
	STRING(),
	NUM(),
	EOF();

	public static final Map<String, TType>		keywds	= new HashMap<>();
	public static final Map<Character, TType>	punct	= new HashMap<>();

	static {
		for (TType t : TType.values()) {
			if (t.kwrd != null) {
				keywds.put(t.kwrd, t);
			} else if (t.pct != null) {
				punct.put(t.pct, t);
			}
		}
	}

	final Character								pct;
	final String								kwrd;

	TType(Character c) {
		kwrd = null;
		pct = c;
	}

	TType(String s) {
		pct = null;
		kwrd = s;
	}

	TType() {
		pct = null;
		kwrd = null;
	}

}
