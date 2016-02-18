package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class IdenExpr extends Expr {

	public IdenExpr(Token t) {
		super(t);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(getFrom().text);
		chunk.add_instr(JRPNVMCodes.GETV, i, getFrom().lineno);
	}

}
