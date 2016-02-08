package jrpn.lang;

public class JRPNRef implements JRPNObj {

	public JRPNObj	ref;

	public JRPNRef(JRPNObj o) {
		ref = o;
	}

	public String toString() {
		return "REF: " + ref;
	}

}
