package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public class MapExpr extends Expr {

	private Expr[]	keys, vals;

	public MapExpr(Token from, Expr k[], Expr v[]) {
		super(from);
		keys = k;
		vals = v;
	}

	@Override
	public void compile(ExeBuilder comp, CChunkBuilder chunk) {
		chunk.add_instr(JRPNVMCodes.NEWMAP, 0, getFrom().lineno);
		for (int i = 0; i < keys.length; i++) {
			keys[i].compile(comp, chunk);
			vals[i].compile(comp, chunk);
			chunk.add_instr(JRPNVMCodes.MAPSETM, 0, keys[0].getFrom().lineno);
		}

	}

}
