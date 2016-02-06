package jrpn.parser;

import jrpn.syn.Token;

public class DotExpr extends Expr {

	Token	from;
	String	val;

	public DotExpr(Token f, String nxt) {
		from = f;
		val = nxt;
	}

}
