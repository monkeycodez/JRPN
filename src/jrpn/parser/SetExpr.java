package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class SetExpr extends Expr {

	public SetExpr(Token f) {
		super(f);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		chunk.add_instr(JRPNVMCodes.SET, 0, getFrom().lineno);
	}

}
