package jrpn.lexer;

import java.io.IOException;
import java.nio.file.Paths;

public class Test {

	public static void main(String args[]) throws IOException {
		TokenProvider prov =
				new Lexer(new FileLexer(Paths.get("src/script/test.jrpn")));
		while (!prov.is_done()) {
			System.out.println(prov.next());
		}
	}

}
