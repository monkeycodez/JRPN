package jrpn.lang;

import java.util.HashMap;

public class JRPNMap extends HashMap<JRPNObj, JRPNRef> implements JRPNObj {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JRPNMap() {
	}

	public JRPNRef get(JRPNObj val) {
		JRPNRef r = super.get(val);
		if (r == null) {
			r = new JRPNRef(null);
			super.put(val, r);
		}
		return r;
	}

	public void set(JRPNObj key, JRPNObj val) {
		super.put(key, new JRPNRef(val));
	}

	//	public boolean equals(Object o) {
	//		return super.equals(o);
	//	}
	//
	//	public int hashCode() {
	//		return super.hashCode();
	//	}
	//
	@Override
	public String toString() {
		return "JRPNMap: " + super.toString();
	}

}
