package jrpn.parser;

import jrpn.run.JRPNVMCodes;
import jrpn.syn.Token;

public class PopExpr extends Expr {

	PopExpr(Token t) {
		super(t);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		chunk.add_instr(JRPNVMCodes.POP, 0, from.lineno);
	}

}
