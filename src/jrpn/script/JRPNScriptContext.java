package jrpn.script;

import java.io.*;
import java.util.*;

import javax.script.*;

public class JRPNScriptContext implements ScriptContext {

	private JRPNBindings	global, engine;
	private Writer			out, err;
	private Reader			in;

	@Override
	public void setBindings(Bindings bindings, int scope) {
		if (scope == ENGINE_SCOPE) {
			engine.putAll(bindings);
		} else if (scope == GLOBAL_SCOPE) {
			global.putAll(bindings);
		}
	}

	@Override
	public Bindings getBindings(int scope) {
		if (scope == ENGINE_SCOPE) {
			return engine;
		} else if (scope == GLOBAL_SCOPE) {
			return global;
		}
		return null;
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		getBindings(scope).put(name, value);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		return getBindings(scope).get(name);
	}

	@Override
	public Object removeAttribute(String name, int scope) {
		return getBindings(scope).remove(name);
	}

	@Override
	public Object getAttribute(String name) {
		return global.get(name);
	}

	@Override
	public int getAttributesScope(String name) {
		if (global.containsKey(name)) {
			return GLOBAL_SCOPE;
		} else if (engine.containsKey(name)) {
			return ENGINE_SCOPE;
		}
		return -1;
	}

	@Override
	public Writer getWriter() {
		return out;
	}

	@Override
	public Writer getErrorWriter() {
		return err;
	}

	@Override
	public void setWriter(Writer writer) {
		out = writer;
	}

	@Override
	public void setErrorWriter(Writer writer) {
		err = writer;
	}

	@Override
	public Reader getReader() {
		return in;
	}

	@Override
	public void setReader(Reader reader) {
		in = reader;
	}

	@Override
	public List<Integer> getScopes() {
		List<Integer> l = new ArrayList<>();
		l.add(GLOBAL_SCOPE);
		l.add(ENGINE_SCOPE);
		return l;
	}
}
