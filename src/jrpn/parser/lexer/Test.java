package jrpn.parser.lexer;

import java.nio.file.Paths;

import jrpn.parser.*;
import jrpn.run.ExecDriver;

public class Test {

	public static void main(String args[]) throws Exception {
		TokenProvider prov =
				new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		// while (!prov.is_done()) {
		// System.out.println(prov.next());
		// }
		prov = new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		JRPNParser p = new JRPNParser(prov);
		Expr e = p.parse_statement();
		// while (!prov.is_done()) {
		// System.out.println(e);
		// e = p.parse_statement();
		// if (e == null)
		// break;
		// }
		prov = new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		ExecDriver.exec(prov, null, true);

	}

}
