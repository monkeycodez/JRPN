package jrpn.script;

import java.io.Reader;

import javax.script.*;

import jrpn.run.JRPNVMCodes;

public class JRPNScriptEngine implements ScriptEngine {

	JRPNScriptEngineFactory	from;
	JRPNBindings			globals;

	public JRPNScriptEngine(JRPNScriptEngineFactory fr, JRPNBindings globals) {
		from = fr;
		this.globals = globals;
	}

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		JRPNBindings b = new JRPNBindings();
		b.putAll(JRPNVMCodes.deffunc);
		b.putAll(context.getBindings(ScriptContext.GLOBAL_SCOPE));
		b.putAll(context.getBindings(ScriptContext.ENGINE_SCOPE));
		return eval(script, b);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		JRPNBindings b = new JRPNBindings();
		b.putAll(JRPNVMCodes.deffunc);
		b.putAll(context.getBindings(ScriptContext.GLOBAL_SCOPE));
		b.putAll(context.getBindings(ScriptContext.ENGINE_SCOPE));
		return eval(reader, b);
	}

	@Override
	public Object eval(String script) throws ScriptException {
		return eval(script, (Bindings) null);
	}

	@Override
	public Object eval(Reader reader) throws ScriptException {
		return eval(reader, (Bindings) null);
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {

		return null;
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(String key, Object value) {
		globals.put(key, value);
	}

	@Override
	public Object get(String key) {
		return globals.get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		return globals;
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		JRPNBindings b = new JRPNBindings();
		b.putAll(globals);
		b.putAll(bindings);
		globals = b;
	}

	@Override
	public Bindings createBindings() {
		return new JRPNBindings();
	}

	@Override
	public ScriptContext getContext() {
		return null;
	}

	@Override
	public void setContext(ScriptContext context) {

	}

	@Override
	public ScriptEngineFactory getFactory() {
		return from;
	}

}
