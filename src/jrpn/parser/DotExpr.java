package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class DotExpr extends Expr {

	String	val;

	public DotExpr(Token f, String nxt) {
		super(f);
		val = nxt;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(val);
		chunk.add_instr(JRPNVMCodes.MAPCGET, i, getFrom().lineno);
	}

}
