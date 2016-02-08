package jrpn.lang;

import java.util.*;

public class JRPNMap implements JRPNObj {

	private Map<JRPNObj, JRPNRef>	map;

	public JRPNMap() {
		map = new HashMap<JRPNObj, JRPNRef>();
	}

	public JRPNRef get(JRPNObj val) {
		JRPNRef r = map.get(val);
		if (r == null) {
			r = new JRPNRef(null);
			map.put(val, r);
		}
		return r;
	}

	public void set(JRPNObj key, JRPNObj val) {
		map.put(key, new JRPNRef(val));
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public String toString() {
		return "JRPNMap: " + map.toString();
	}

}
