package jrpn.lexer;

import java.nio.file.Paths;

import jrpn.parser.*;
import jrpn.run.*;

public class Test {

	public static void main(String args[]) throws Exception {
		TokenProvider prov =
				new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		while (!prov.is_done()) {
			System.out.println(prov.next());
		}
		prov = new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		JRPNParser p = new JRPNParser(prov);
		Expr e = p.parse_statement();
		while (!prov.is_done()) {
			System.out.println(e);
			e = p.parse_statement();
			if (e == null)
				break;
		}
		prov = new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		p = new JRPNParser(prov);
		e = null;
		ExeBuilder bld = new ExeBuilder("src/script/test.jrpn");
		while (!prov.is_done()) {
			e = p.parse_statement();
			bld.write_expr(e);
		}
		JRPNEnv env = bld.create_env();
		JRPNExcecutor exe = new JRPNExcecutor();
		exe.set_env(env);
		System.out.println("-------------------------");
		exe.eval((JRPNCodeObj) env.const_vals[0]);

	}

}
