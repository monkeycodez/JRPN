package jrpn.run;

import jrpn.lang.*;

public class Test {

	public static void main(String args[]) {
		System.out.println("Starting tests\n===============================");
		JRPNEnv env = new JRPNEnv();
		JRPNCodeObj code = new JRPNCodeObj(new int[] {
				0, 3, 6, 3, 7, 1
		}, new int[] {
				0, 0, 0, 0, 0, 0
		});
		env.const_vals[0] = new JRPNString("test\n");
		JRPNExcecutor exe = new JRPNExcecutor();
		exe.set_env(env);
		exe.eval(code);
	}

}
