package jrpn.parser;

import java.util.LinkedList;

import jrpn.run.*;
import jrpn.syn.Token;

public class BlockExpr extends Expr {

	LinkedList<Expr>	stmts	= new LinkedList<Expr>();

	public BlockExpr(Token t) {
		super(t);
	}

	public JRPNCodeObj compile() {

		return null;
	}

	@Override
	public String toString() {
		return stmts.toString();
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int id = comp.register_const(this);
		chunk.add_instr(JRPNVMCodes.PUSHC, id, from.lineno);
	}

	public void compile_children(ExeBuilder comp, CChunkBuilder chunk) {
		for (Expr e : stmts) {
			e.compile(comp, chunk);
		}
		chunk.add_instr(JRPNVMCodes.BREAK, 0, from.lineno);
	}

}
