package jrpn.run;

import static jrpn.run.JRPNVMCodes.*;

import java.util.Arrays;

import jrpn.lang.*;

public class JRPNExcecutor {

	JRPNEnv	env;
	int		ip	= 0;

	JRPNExcecutor(JRPNEnv e) {
		env = e;
	}

	public JRPNExcecutor() {
	}

	public void set_startup(JRPNCodeObj code) {
		env.call_stack[0] = code;
	}

	public void set_env(JRPNEnv e) {
		env = e;
	}

	public JRPNEnv get_env() {
		return env;
	}

	public void eval(JRPNCodeObj code) {
		set_startup(code);
		eval();
	}

	public void eval() {
		JRPNCodeObj curr = env.call_stack[env.call_stk_p];
		int arg = 0;
		JRPNObj o = null;
		JRPNCallable call = null;
		JRPNList l = null;
		int idx = 0;
		vm: while (ip < curr.code.length) {
			try {
				switch (curr.code[ip]) {
					case NOP:
						ip++;
						continue;
					case EXIT:
						break vm;
					case DUP:
						o = env.pop_val();
						env.push_val(o);
						env.push_val(o);
						break;
					case PUSHC:
						arg = curr.arg[ip];
						o = env.const_vals[arg];
						env.push_val(o);
						ip++;
						continue;
					case POP:
						env.pop_val();
						ip++;
						continue;
					case GETV:
						String s =
								((JRPNString) env.const_vals[curr.arg[ip]])
										.toString();
						o = env.get_var(s).ref;
						if (o instanceof JRPNCallable) {
							call = (JRPNCallable) o;
							if (call.autoexec()) {
								ip = call.call(env, ip);
								curr = env.call_stack[env.call_stk_p];
							} else {
								env.push_val(o);
							}
						} else {
							env.push_val(o);
						}
						ip++;
						continue;
					case PRINT:
						env.out.write(env.pop_val().toString());
						env.out.write('\n');
						env.out.flush();
						ip++;
						continue;
					case EPRINT:
						env.err.write(env.pop_val().toString());
						env.err.write('\n');
						env.err.flush();
						ip++;
						continue;
					case CALL:
						call = (JRPNCallable) env.pop_val();
						ip = call.call(env, ip);
						curr = env.call_stack[env.call_stk_p];
						ip++;
						continue;
					case SET:
						JRPNRef r = (JRPNRef) env.pop_val();
						r.ref = env.pop_val();
						ip++;
						break;
					case REF:
						String name =
								((JRPNString) env.const_vals[curr.arg[ip]])
										.toString();
						JRPNRef rf = env.get_var(name);
						if (rf == null) {
							rf = new JRPNRef(JRPNBool.FALSE);
							env.set_local_var(name, rf);
						}
						env.push_val(rf);
						ip++;
						break;
					case BREAK:
						ip = env.pop_call_stack();
						curr = env.call_stack[env.call_stk_p];
						break;
					case PUSHFRAME:
						env.push_var_frame();
						if (curr.nt && env.lastm != null) {
							env.push_val(env.lastm);
						}
						env.lastm = null;
						ip++;
						break;
					case POPFRAME:
						env.pop_var_frame();
						ip++;
						break;
					case NEWMAP:
						env.push_val(new JRPNMap());
						ip++;
						break;
					case MAPSETM:
						JRPNObj val = env.pop_val();
						JRPNObj key = env.pop_val();
						JRPNMap map = (JRPNMap) env.pop_val();
						map.set(key, val);
						env.push_val(map);
						ip++;
						break;
					case MAPCGET:
						key = env.const_vals[curr.arg[ip]];
						map = (JRPNMap) env.pop_val();
						val = map.get(key).ref;
						if (val instanceof JRPNCallable) {
							env.lastm = map;
						}
						env.push_val(val);
						ip++;
						break;
					case MAPCGETR:
						key = env.const_vals[curr.arg[ip]];
						map = (JRPNMap) env.pop_val();
						val = map.get(key);
						env.push_val(val);
						ip++;
						break;
					case JMP:
						ip = curr.arg[ip];
						break;
					case JMPIFFN:
						o = env.pop_val();
						if (o == JRPNBool.FALSE) {
							ip = curr.arg[ip];
						} else {
							ip++;
						}
						break;
					case NEWLIST:
						env.push_val(new JRPNList());
						ip++;
						break;
					case LISTADDL:
						o = env.pop_val();
						l = (JRPNList) env.pop_val();
						l.add(o);
						env.push_val(l);
						ip++;
						break;
					case LISTGET:
						idx = (int) ((JRPNNum) env.pop_val()).val;
						l = (JRPNList) env.pop_val();
						env.push_val(l.lst.get(idx).ref);
						ip++;
						break;
					case LISTGETR:
						idx = (int) ((JRPNNum) env.pop_val()).val;
						l = (JRPNList) env.pop_val();
						env.push_val(l.lst.get(idx));
						ip++;
						break;

				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(Arrays.toString(env.call_stack));
				System.out.println(Arrays.toString(env.val_stack));
				System.out.println("ip: " + ip + " instr: " + curr.code[ip]
						+ " arg: " + curr.arg[ip]
						+ " ln: " + curr.lineno[ip]);
				break;
			}
		}
	}

}
