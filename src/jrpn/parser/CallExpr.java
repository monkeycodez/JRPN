package jrpn.parser;

import jrpn.syn.Token;

public class CallExpr extends Expr {

	Token	from;

	public CallExpr(Token f) {
		from = f;
	}

}
