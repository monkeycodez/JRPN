package jrpn.parser;

import jrpn.syn.Token;

public class NumberExpr extends Expr {

	double	val;

	public NumberExpr(Token t) {
		val = Double.parseDouble(t.text);
	}

}
