package jrpn.run;

import java.io.*;
import java.util.*;

import jrpn.lang.*;

class JRPNEnv {

	public static final int	MAX_STACK_SIZE	= 1 << 16,
			DEFAULT_CONST_SIZE = 1 << 16, MAX_CONST_SIZE = 1 << 20;

	@SuppressWarnings("unchecked")
	Map<String, JRPNRef>[]	var_stack		= new Map[MAX_STACK_SIZE];
	int						var_idx			= 0;

	JRPNObj					val_stack[]		= new JRPNObj[MAX_STACK_SIZE];
	int						val_idx			= 0;

	JRPNCodeObj				call_stack[]	= new JRPNCodeObj[MAX_STACK_SIZE];
	int						insp_stk[]		= new int[MAX_STACK_SIZE];
	int						call_stk_p		= 0;

	JRPNObj					const_vals[]	= new JRPNObj[DEFAULT_CONST_SIZE];
	int						const_end;

	Writer					out, err;
	Reader					in;

	{
		var_stack[0] = new HashMap<>();
		out = new OutputStreamWriter(System.out);
		err = new OutputStreamWriter(System.err);
		in = new InputStreamReader(System.in);
	}

	void push_var_frame() {
		var_stack[++var_idx] = new HashMap<>();
	}

	void pop_var_frame() {
		var_stack[var_idx--] = null;
	}

	JRPNRef get_var(String name) {
		return var_stack[var_idx].get(name);
	}

	JRPNRef get_global_var(String name) {
		return var_stack[0].get(name);
	}

	void set_global_var(String name, JRPNObj o) {
		var_stack[0].put(name, new JRPNRef(o));
	}

	void set_local_var(String name, JRPNObj o) {
		var_stack[var_idx].put(name, new JRPNRef(o));
	}

	void push_val(JRPNObj o) {
		val_stack[val_idx++] = o;
	}

	void push_valc(int i) {
		val_stack[val_idx++] = const_vals[i];
	}

	JRPNObj pop_val() {
		return val_stack[--val_idx];
	}

	int add_const(JRPNObj o) {
		int next = const_end++;
		if (next >= const_vals.length) {
			const_vals = Arrays.copyOf(const_vals, const_vals.length * 3 / 2);
		}
		const_vals[next] = o;
		return next;
	}

	void push_call_stack(JRPNCodeObj c, int ip) {
		call_stack[call_stk_p] = c;
		insp_stk[call_stk_p] = ip;
		call_stk_p++;
	}

	void flush_streams() throws IOException {
		out.flush();
		err.flush();
	}

}
