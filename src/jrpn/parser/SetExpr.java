package jrpn.parser;

import jrpn.syn.Token;

public class SetExpr extends Expr {

	Token	from;

	public SetExpr(Token f) {
		from = f;
	}

}
