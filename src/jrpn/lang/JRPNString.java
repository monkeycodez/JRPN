package jrpn.lang;

public class JRPNString implements JRPNObj {

	private final String	val;

	public JRPNString(String s) {
		val = s;
	}

	public String toString() {
		return val;
	}

}
