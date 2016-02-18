package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class NumberExpr extends Expr {

	double	val;

	public NumberExpr(Token t) {
		super(t);
		val = Double.parseDouble(t.text);
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(val);
		chunk.add_instr(JRPNVMCodes.PUSHC, i, getFrom().lineno);
	}

}
