package jrpn.parser;

import jrpn.syn.Token;

public class NumberExpr extends Expr {

	double	val;

	public NumberExpr(Token t) {
		super(t);
		val = Double.parseDouble(t.text);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		// TODO Auto-generated method stub

	}

}
