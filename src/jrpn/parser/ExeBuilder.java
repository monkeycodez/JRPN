package jrpn.parser;

import java.util.*;

import jrpn.lang.*;

public class ExeBuilder {

	JRPNObj	const_vals[]	= new JRPNObj[20000];
	int		last			= 0;

	static class to_write {
		int			constptr	= 0;
		BlockExpr	ex;

		to_write(BlockExpr e, int p) {
			constptr = p;
			ex = e;
		}
	}

	List<to_write>	out	= new LinkedList<>();

	public int register_const(double val) {
		const_vals[last] = new JRPNNum(val);
		return last++;
	}

	public int register_const(String s) {
		const_vals[last] = new JRPNString(s);
		return last++;
	}

	public int register_const(BlockExpr ex) {
		out.add(new to_write(ex, last));
		return last++;
	}

}
