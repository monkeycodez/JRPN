package jrpn.run;

import static jrpn.run.JRPNVMCodes.*;

import java.util.Arrays;

import jrpn.lang.*;

public class JRPNExcecutor {

	private JRPNEnv	env;

	public void set_env(JRPNEnv e) {
		env = e;
	}

	public JRPNEnv get_env() {
		return env;
	}

	public void eval(JRPNCodeObj code) {
		JRPNCodeObj curr = code;
		env.call_stack[0] = code;
		int ip = 0;
		vm: while (ip < curr.code.length) {
			try {
				switch (curr.code[ip]) {
					case NOP:
						ip++;
						continue;
					case EXIT:
						break vm;
					case DUP:
						JRPNObj o = env.pop_val();
						env.push_val(o);
						env.push_val(o);
						break;
					case PUSHC:
						env.push_valc(curr.arg[ip]);
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
						JRPNObj n = env.get_var(s).ref;
						env.push_val(n);
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
						JRPNCallable call = (JRPNCallable) env.pop_val();
						//						System.out.println(call);
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
							rf = new JRPNRef(null);
							env.set_local_var(name, rf);
						}
						env.push_val(rf);
						ip++;
						break;
					case BREAK:
						ip = env.pop_call_stack();
						//						System.out.println(ip);
						curr = env.call_stack[env.call_stk_p];
						break;
					case PUSHFRAME:
						env.push_var_frame();
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
						//						System.out.println(ip);
						ip = curr.arg[ip];
						//						System.out.println("JMP TO: " + ip);
						break;
					case JMPIFFN:
						o = env.pop_val();
						if (o == JRPNBool.FALSE) {
							ip = curr.arg[ip];
						} else {
							ip++;
						}
						break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println(Arrays.toString(env.call_stack));
				System.out.println(curr.arg[ip] + " ln: " + curr.lineno[ip]);
				break;
			}
		}
	}

}
