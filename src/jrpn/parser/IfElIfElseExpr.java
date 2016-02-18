package jrpn.parser;

import java.util.*;

import jrpn.run.*;
import jrpn.syn.Token;

class IfElIfElseExpr extends Expr {

	Expr	cond, body, els;
	List<Expr>	elif, elifc;

	public IfElIfElseExpr(Token from, Expr c, Expr b, List<Expr> elc,
			List<Expr> el, Expr e) {
		super(from);
		cond = c;
		body = b;
		elif = el;
		elifc = elc;
		els = e;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		cond.compile(comp, chunk);
		chunk.call_instr(getFrom().lineno);
		String end = "end-" + CChunkBuilder.get_uid();
		String elss = "else-" + CChunkBuilder.get_uid();
		List<String> elifs = new LinkedList<>();
		for (int i = 0; i < elif.size(); i++) {
			elifs.add("elif-" + CChunkBuilder.get_uid());
		}
		elifs.add(elss);
		chunk.add_instr(JRPNVMCodes.JMPIFFN, elifs.get(0), getFrom().lineno);
		body.compile(comp, chunk);
		chunk.call_instr(body.getFrom().lineno);
		chunk.add_instr(JRPNVMCodes.JMP, end, body.getFrom().lineno);
		int idx = 0;
		for (; idx < elif.size(); idx++) {
			chunk.set_mark(elifs.get(idx));
			elifc.get(idx).compile(comp, chunk);
			chunk.call_instr(elifc.get(idx).getFrom().lineno);
			chunk.add_instr(JRPNVMCodes.JMPIFFN, elifs.get(idx + 1),
					elifc.get(idx).getFrom().lineno);
			elif.get(idx).compile(comp, chunk);
			chunk.call_instr(elif.get(idx).getFrom().lineno);
			chunk.add_instr(JRPNVMCodes.JMP, end, elif.get(idx).getFrom().lineno);
		}
		chunk.set_mark(elss);
		els.compile(comp, chunk);
		chunk.call_instr(els.getFrom().lineno);
		chunk.set_mark(end);
	}

}
