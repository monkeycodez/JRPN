package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class RefDotExpr extends RefExpr {

	public RefDotExpr(Token from, Token to) {
		super(from, to);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(ref.text);
		chunk.add_instr(JRPNVMCodes.MAPCGETR, i, getFrom().lineno);
	}

}
