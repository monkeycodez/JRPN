package jrpn.run;

import java.util.Map;

import jrpn.lang.JRPNRef;
import jrpn.parser.*;
import jrpn.parser.lexer.TokenProvider;

public class ExecDriver {

	public static void exec(TokenProvider src, Map<String, JRPNRef> gbls,
			boolean aexec) throws JRPNException {
		JRPNParser pr = new JRPNParser(src);
		ExeBuilder b = new ExeBuilder(src.peek().source);
		if (gbls != null)
			b.env.globals.putAll(gbls);
		b.env.globals.putAll(JRPNVMCodes.deffunc);
		if (aexec) {
			_aexec(pr, b);
		}
	}

	static void _aexec(JRPNParser pr, ExeBuilder b) throws JRPNException {
		JRPNExcecutor ex = new JRPNExcecutor(b.env);
		while (!pr.is_done()) {
			Expr e = pr.parse_statement();
			b.write_expr(e);
			ex.eval();
		}
	}

}
