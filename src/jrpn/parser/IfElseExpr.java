package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

class IfElseExpr extends Expr {

	Expr	cond, body, els;

	public IfElseExpr(Token from, Expr c, Expr b, Expr e) {
		super(from);
		cond = c;
		body = b;
		els = e;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		cond.compile(comp, chunk);
		chunk.add_instr(JRPNVMCodes.CALL, 0, getFrom().lineno);
		String end = "end-" + CChunkBuilder.get_uid();
		String elss = "else-" + CChunkBuilder.get_uid();
		chunk.add_instr(JRPNVMCodes.JMPIFFN, elss, getFrom().lineno);
		body.compile(comp, chunk);
		chunk.call_instr(body.getFrom().lineno);
		chunk.add_instr(JRPNVMCodes.JMP, end, getFrom().lineno);
		chunk.set_mark(elss);
		els.compile(comp, chunk);
		chunk.call_instr(els.getFrom().lineno);
		chunk.set_mark(end);
	}

}
