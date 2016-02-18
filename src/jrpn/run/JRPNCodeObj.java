package jrpn.run;

import jrpn.lang.JRPNObj;

public class JRPNCodeObj implements JRPNObj, JRPNCallable {

	int		code[];
	int		arg[];
	int		lineno[];
	String	source;
	boolean	nt;

	public JRPNCodeObj(int code[], int arg[]) {
		this.code = code;
		this.arg = arg;
		nt = false;
	}

	public JRPNCodeObj(int code[], int arg[], int linenos[], String src) {
		this.code = code;
		this.arg = arg;
		source = src;
		lineno = linenos;
		nt = false;
	}

	public JRPNCodeObj(int code[], int arg[], int linenos[], String src,
			boolean nthis) {
		this.code = code;
		this.arg = arg;
		source = src;
		lineno = linenos;
		nt = nthis;
	}

	JRPNCodeObj(String src) {
		source = src;
	}

	@Override
	public int call(JRPNEnv env, int ip) {
		env.push_call_stack(this, ip);
		return -1;
	}

	@Override
	public String toString() {
		return source + "@" + lineno[0];
	}

	public boolean needs_this() {
		return nt;
	}

}
