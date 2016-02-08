package jrpn.parser.lexer;

import jrpn.run.JRPNException;
import jrpn.syn.Token;

public interface TokenProvider {

	public Token next() throws JRPNException;

	public Token peek() throws JRPNException;

	public Token peek(int num) throws JRPNException;

	public boolean is_done() throws JRPNException;

}
