package jrpn.run;

import jrpn.lang.JRPNObj;

public class JRPNCodeObj implements JRPNObj, JRPNCallable {

	public final int	code[];
	public final int	arg[];
	public int			lineno[];
	public String		source;

	public JRPNCodeObj(int code[], int arg[]) {
		this.code = code;
		this.arg = arg;
	}

	public JRPNCodeObj(int code[], int arg[], int linenos[], String src) {
		this.code = code;
		this.arg = arg;
		source = src;
		lineno = linenos;
	}

	@Override
	public int call(JRPNEnv env, int ip) {
		env.push_call_stack(this, ip);
		return -1;
	}

}
