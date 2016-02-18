package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class IfExpr extends Expr {

	Expr	cond, bod;

	public IfExpr(Token from, Expr c, Expr b) {
		super(from);
		cond = c;
		bod = b;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		cond.compile(comp, chunk);
		chunk.add_instr(JRPNVMCodes.CALL, 0, getFrom().lineno);
		String end = "end-" + CChunkBuilder.get_uid();
		chunk.add_instr(JRPNVMCodes.JMPIFFN, end, getFrom().lineno);
		bod.compile(comp, chunk);
		chunk.add_instr(JRPNVMCodes.CALL, 0, bod.getFrom().lineno);
		chunk.set_mark(end);
	}

}
