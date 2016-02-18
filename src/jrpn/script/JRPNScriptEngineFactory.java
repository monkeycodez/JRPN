package jrpn.script;

import java.util.*;

import javax.script.*;

public class JRPNScriptEngineFactory implements ScriptEngineFactory {

	static final String	E_VERS	= ".1", E_NM = "{S}POP";

	final JRPNBindings	globals	= new JRPNBindings();

	@Override
	public String getEngineName() {
		return E_NM;
	}

	@Override
	public String getEngineVersion() {
		return E_VERS;
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList(".jrpn");
	}

	@Override
	public List<String> getMimeTypes() {
		return Arrays.asList("application/jrpn-script");
	}

	@Override
	public List<String> getNames() {
		return Arrays.asList("jrpn", "{S}POP");
	}

	@Override
	public String getLanguageName() {
		return "jrpn";
	}

	@Override
	public String getLanguageVersion() {
		return ".1a";
	}

	@Override
	public Object getParameter(String key) {
		switch (key) {
			case ScriptEngine.ENGINE:
				return getEngineName();
			case ScriptEngine.ENGINE_VERSION:
				return getEngineVersion();
			case ScriptEngine.LANGUAGE:
				return getLanguageName();
			case ScriptEngine.LANGUAGE_VERSION:
				return getLanguageVersion();
			case ScriptEngine.NAME:
				return "jrpn";
			case "THREADING":
				return null;
		}
		return null;
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		StringBuilder str = new StringBuilder();
		for (String s : args) {
			str.append(s).append(' ');
		}
		str.append(obj).append(m).append('!');
		return str.toString();
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		return toDisplay + " println ";
	}

	@Override
	public String getProgram(String... statements) {
		StringBuilder s = new StringBuilder();
		for (String st : statements) {
			s.append(st).append(' ');
		}
		return s.toString();
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new JRPNScriptEngine(this, globals);
	}

}
