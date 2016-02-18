package jrpn.parser.lexer;

import java.io.*;

import jrpn.run.JRPNException;

public class ReaderLexReader implements LexReader {

	PushbackReader	in;
	String			source;
	boolean			done	= false;
	int				lineno	= 1;

	public ReaderLexReader(Reader r) {
		in = new PushbackReader(r);
		source = r.toString();

	}

	public ReaderLexReader(Reader r, String src) {
		in = new PushbackReader(r);
		source = src;

	}

	@Override
	public char next() throws JRPNException {
		int i = 0;
		try {
			i = in.read();
		} catch (IOException e) {
			throw new JRPNException(e);
		}
		if (i == -1) {
			done = true;
			i = 0;
			try {
				in.close();
			} catch (IOException e) {
				throw new JRPNException(e);
			}
		}
		if (i == '\n') {
			lineno++;
		}
		return (char) i;
	}

	@Override
	public char peek() throws JRPNException {
		int i = 0;
		try {
			i = in.read();
			if (i == -1) {
				i = 0;
			} else {
				in.unread(i);
			}
		} catch (IOException e) {
			throw new JRPNException(e);
		}

		return (char) i;
	}

	@Override
	public boolean is_done() {
		return done;
	}

	@Override
	public int lineno() {

		return lineno;
	}

	@Override
	public String source() {
		return source;
	}

}
