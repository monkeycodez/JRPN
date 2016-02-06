package jrpn.parser;

import jrpn.syn.Token;

public class IdenExpr extends Expr {

	String	name;

	public IdenExpr(Token t) {
		name = t.text;
	}

}
