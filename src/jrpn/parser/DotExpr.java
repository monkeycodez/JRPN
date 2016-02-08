package jrpn.parser;

import jrpn.syn.Token;

public class DotExpr extends Expr {

	String	val;

	public DotExpr(Token f, String nxt) {
		super(f);
		val = nxt;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		// TODO Auto-generated method stub

	}

}
