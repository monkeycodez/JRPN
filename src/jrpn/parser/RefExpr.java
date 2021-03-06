package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class RefExpr extends Expr {

	Token	ref;

	RefExpr(Token t, Token nx) {
		super(t);
		ref = nx;
	}

	@Override
	public String toString() {
		return "REF: " + ref;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		int i = comp.register_const(ref.text);
		chunk.add_instr(JRPNVMCodes.REF, i, getFrom().lineno);
	}
	
	public static Expr getr_listidx(Token from, Expr val){
		return new Expr(from){

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				val.compile(comp, chunk);
				chunk.add_instr(JRPNVMCodes.LISTGETR, 0, from.lineno);
			}
			
		};
	}

}
