package jrpn.parser;

import java.util.*;

import jrpn.lang.*;
import jrpn.run.*;

public class ExeBuilder {

	JRPNObj					const_vals[]	= new JRPNObj[20000];
	int						last			= 1;

	Map<String, Integer>	const_strs		= new HashMap<>();
	Map<Double, Integer>	const_nums		= new HashMap<>();

	CChunkBuilder			base;

	public ExeBuilder(String src) {
		base = new CChunkBuilder(src);
	}

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
		int i = const_nums.getOrDefault(val, -1);
		if (i != -1) {
			return i;
		}
		const_vals[last] = new JRPNNum(val);
		const_nums.put(val, last);
		return last++;
	}

	public int register_const(String s) {
		Integer i = const_strs.get(s);
		if (i != null) {
			return i;
		}
		const_vals[last] = new JRPNString(s);
		const_strs.put(s, last);
		return last++;
	}

	public int register_const(BlockExpr ex) {
		CChunkBuilder c = new CChunkBuilder(ex.from.source);
		ex.compile_children(this, c);
		const_vals[last] = c.create_code();
		return last++;
	}

	public void write_expr(Expr e) {
		e.compile(this, base);
	}

	public JRPNEnv create_env() {
		JRPNEnv e = new JRPNEnv();
		JRPNCodeObj cd = base.create_code();
		const_vals[0] = cd;
		e.const_vals = const_vals;
		return e;
	}

}
