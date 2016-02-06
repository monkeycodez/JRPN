package jrpn.parser;

import jrpn.syn.Token;

public class StrExpr extends Expr {

	String	val;

	StrExpr(Token t) {
		val = t.text;
	}

}
