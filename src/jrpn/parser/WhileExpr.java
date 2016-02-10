package jrpn.parser;

import jrpn.run.JRPNVMCodes;
import jrpn.syn.Token;

public class WhileExpr extends Expr {

	Expr	cond, body;

	public WhileExpr(Token from, Expr c, Expr b) {
		super(from);
		cond = c;
		body = b;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		String st = "start-" + CChunkBuilder.get_uid();
		String end = "end-" + CChunkBuilder.get_uid();
		chunk.set_mark(st);
		cond.compile(comp, chunk);
		chunk.call_instr(cond.from.lineno);
		chunk.add_instr(JRPNVMCodes.JMPIFFN, end, cond.from.lineno);
		body.compile(comp, chunk);
		chunk.call_instr(body.from.lineno);
		chunk.add_instr(JRPNVMCodes.JMP, st, cond.from.lineno);
		chunk.set_mark(end);
	}

}
