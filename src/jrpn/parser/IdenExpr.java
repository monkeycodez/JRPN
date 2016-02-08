package jrpn.parser;

import jrpn.run.JRPNVMCodes;
import jrpn.syn.Token;

public class IdenExpr extends Expr {

	public IdenExpr(Token t) {
		super(t);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(from.text);
		chunk.add_instr(JRPNVMCodes.GETV, i, from.lineno);
	}

}
