package jrpn.parser;

import jrpn.run.JRPNVMCodes;
import jrpn.syn.Token;

public class StrExpr extends Expr {

	StrExpr(Token t) {
		super(t);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int constr = comp.register_const(from.text);
		chunk.add_instr(JRPNVMCodes.PUSHC, constr, from.lineno);
	}

}
