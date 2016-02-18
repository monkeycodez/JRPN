package jrpn.parser.lexer;

import jrpn.run.JRPNException;

public interface LexReader {

	public char next() throws JRPNException;

	public char peek() throws JRPNException;

	public boolean is_done();

	public int lineno();

	public String source();

}
