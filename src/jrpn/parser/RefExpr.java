package jrpn.parser;

import jrpn.run.JRPNVMCodes;
import jrpn.syn.Token;

public class RefExpr extends Expr {

	String	ref;

	RefExpr(Token t, Token nx) {
		super(t);
		ref = nx.text;
	}

	@Override
	public String toString() {
		return "REF: " + ref;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(ref);
		chunk.add_instr(JRPNVMCodes.REF, i, from.lineno);
	}

}
