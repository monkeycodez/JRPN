package jrpn.script;

import java.util.*;

import javax.script.Bindings;

import jrpn.lang.*;

public class JRPNBindings implements Bindings {

	private HashMap<String, JRPNRef>	bindings;

	private static JRPNObj _tojobj(Object fr) {
		if (fr instanceof String) {
			return new JRPNString((String) fr);
		} else if (fr instanceof Number) {
			return new JRPNNum(((Number) fr).doubleValue());
		}
		throw new UnsupportedOperationException();
	}

	public int size() {
		return bindings.size();
	}

	public boolean isEmpty() {
		return bindings.isEmpty();
	}

	public JRPNObj get(Object key) {
		return bindings.get(key);
	}

	Map<String, JRPNRef> getm() {
		return bindings;
	}

	public boolean containsKey(Object key) {
		return bindings.containsKey(key);
	}

	public JRPNObj put(String key, Object value) {
		JRPNObj o = _tojobj(value);
		bindings.put(key, new JRPNRef(o));
		return o;
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		for (Map.Entry<? extends String, ? extends Object> e : m.entrySet()) {
			this.put(e.getKey(), e.getValue());
		}
	}

	public JRPNObj remove(Object key) {
		return bindings.remove(key);
	}

	public void clear() {
		bindings.clear();
	}

	public boolean containsValue(Object value) {
		return bindings.containsValue(value);
	}

	public Set<String> keySet() {
		return bindings.keySet();
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public Collection values() {
		return bindings.values();
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public Set entrySet() {
		return bindings.entrySet();
	}

}
