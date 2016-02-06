package jrpn.parser;

import jrpn.syn.Token;

public class ExitExpr extends Expr {

	Token	fr;

	ExitExpr(Token t) {
		fr = t;
	}

}
