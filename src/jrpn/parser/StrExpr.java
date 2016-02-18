package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class StrExpr extends Expr {

	StrExpr(Token t) {
		super(t);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int constr = comp.register_const(getFrom().text);
		chunk.add_instr(JRPNVMCodes.PUSHC, constr, getFrom().lineno);
	}

}
