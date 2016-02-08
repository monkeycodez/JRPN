package jrpn.lang;

public class JRPNRef implements JRPNObj {

	public JRPNObj	ref;

	public JRPNRef(JRPNObj o) {
		ref = o;
	}

	public String toString() {
		return "REF: " + ref;
	}

	public boolean equals(Object o) {
		if (o instanceof JRPNRef) {
			return ((JRPNRef) o).ref == ref;
		}
		return false;
	}

	public int hashCode() {
		return System.identityHashCode(ref);
	}

}
