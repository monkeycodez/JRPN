package jrpn.parser;

import java.util.*;

import jrpn.run.*;
import jrpn.syn.Token;

class IfElIfExpr extends Expr {

	Expr	cond, body;
	List<Expr>	elif, elifc;

	public IfElIfExpr(Token from, Expr c, Expr b, List<Expr> elc, List<Expr> el) {
		super(from);
		cond = c;
		body = b;
		elif = el;
		elifc = elc;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		cond.compile(comp, chunk);
		chunk.call_instr(getFrom().lineno);
		String end = "end-" + CChunkBuilder.get_uid();
		List<String> elifs = new LinkedList<>();
		for (int i = 0; i < elif.size(); i++) {
			elifs.add("elif-" + CChunkBuilder.get_uid());
		}
		elifs.add(end);
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
			//	if (idx + 2 != elif.size())
			chunk.add_instr(JRPNVMCodes.JMP, end, elif.get(idx).getFrom().lineno);
		}
		chunk.set_mark(end);
	}

}
