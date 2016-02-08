package jrpn.parser.lexer;

public interface LexReader {

	public char next();

	public char peek();

	public boolean is_done();

	public int lineno();

	public String source();

}
