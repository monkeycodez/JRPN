package jrpn.run;

import java.util.*;

import jrpn.lang.*;
import jrpn.parser.*;

public class ExeBuilder {

	JRPNEnv					env			= new JRPNEnv();

	Map<String, Integer>	const_strs	= new HashMap<>();
	Map<Double, Integer>	const_nums	= new HashMap<>();

	CChunkBuilder			base;

	public ExeBuilder(String src) {
		base = new CChunkBuilder(src);
		env.call_stack[0] = base.cobj;
		env.add_const(base.cobj);
		env.add_const(JRPNBool.TRUE);
		env.add_const(JRPNBool.FALSE);
	}

	public int register_const(double val) {
		Integer d = const_nums.get(val);
		if (d != null) {
			return d;
		}
		int i = env.add_const(new JRPNNum(val));
		const_nums.put(val, i);
		return i;
	}

	public int register_const(String s) {
		Integer i = const_strs.get(s);
		if (i != null) {
			return i;
		}
		int i1 = env.add_const(new JRPNString(s));
		const_strs.put(s, i1);
		return i1;
	}

	public int register_const(BlockExpr ex) {
		CChunkBuilder c = new CChunkBuilder(ex.getFrom().source);
		ex.compile_children(this, c);
		return env.add_const(c.cobj);
	}

	public void write_expr(Expr e) {
		e.compile(this, base);
	}

	JRPNEnv get_env() {
		return env;
	}

	public JRPNEnv create_env() {
		return env;
	}

}
