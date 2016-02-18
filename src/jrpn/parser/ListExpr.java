package jrpn.parser;

import java.util.*;

import jrpn.run.*;
import jrpn.syn.Token;

public class ListExpr extends Expr {

	public ListExpr(Token from) {
		super(from);

	}

	List<Expr>	items	= new LinkedList<Expr>();

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		chunk.add_instr(JRPNVMCodes.NEWLIST, -1, getFrom().lineno);
		for(Expr el: items){
			el.compile(comp, chunk);
			chunk.add_instr(JRPNVMCodes.LISTADDL, -1, getFrom().lineno);
		}
	}

}
