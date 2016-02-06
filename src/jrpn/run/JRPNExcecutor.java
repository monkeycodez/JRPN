package jrpn.run;

import static jrpn.run.JRPNVMCodes.*;
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
		int curc[] = curr.code;
		vm: while (ip < curr.code.length) {
			try {
				switch (curr.code[ip]) {
					case NOP:
						ip++;
						continue;
					case EXIT:
						break vm;
					case PUSHC:
						env.push_valc(curr.arg[ip]);
						ip++;
						continue;
					case POP:
						env.pop_val();
						ip++;
						continue;
					case GETV:
						JRPNObj o = env.const_vals[curr.arg[ip]];
						env.push_val(env.get_var(o.toString()).ref);
						ip++;
						continue;
					case PRINT:
						env.out.write(env.pop_val().toString());
						env.out.flush();
						ip++;
						continue;
					case EPRINT:
						env.err.write(env.pop_val().toString());
						env.err.flush();
						ip++;
						continue;
					case CALL:
						ip++;
						continue;

				}
			} catch (Exception ex) {

			}
		}
	}

}
