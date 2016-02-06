package jrpn.lexer;

import jrpn.syn.Token;

public interface TokenProvider {

	public Token next();

	public Token peek();

	public Token peek(int num);

	public boolean is_done();

}
