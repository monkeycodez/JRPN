package jrpn.parser.lexer;

import java.nio.file.Paths;

import jrpn.run.ExecDriver;

public class Test {

	public static void main(String args[]) throws Exception {
		TokenProvider prov =
				new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		prov = new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		ExecDriver.exec(prov, null, true);

	}

}
