package jrpn.lang;

public class JRPNString implements JRPNObj {

	private final String	val;

	public JRPNString(String s) {
		val = s;
	}

	public String toString() {
		return val;
	}

	public boolean equals(Object obj) {
		if (obj instanceof JRPNString) {
			return ((JRPNString) obj).val.equals(val);
		}
		return val.equals(obj);
	}

	public int hashCode() {
		return val.hashCode();
	}

}
