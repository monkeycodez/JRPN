package jrpn.parser;

import java.util.*;

import jrpn.lang.*;
import jrpn.run.*;

public class ExeBuilder {

	JRPNObj			const_vals[]	= new JRPNObj[20000];
	int				last			= 1;

	CChunkBuilder	base;

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
		const_vals[last] = new JRPNNum(val);
		return last++;
	}

	public int register_const(String s) {
		const_vals[last] = new JRPNString(s);
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
		System.out.println(Arrays.toString(cd.code));
		e.const_vals = const_vals;
		return e;
	}

}
