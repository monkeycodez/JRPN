package jrpn.parser.lexer;

import java.io.IOException;
import java.nio.file.*;

public class FileLexer implements LexReader {

	public final String	source;

	private byte[]		file;

	private int			idx	= 0, lineno = 1;

	public FileLexer(Path src) throws IOException {
		source = src.toString();
		file = Files.readAllBytes(src);
	}

	@Override
	public char next() {
		if (is_done()) {
			return 0;
		}
		char c = (char) file[idx++];
		if (c == '\n') {
			lineno++;
		}
		return c;
	}

	@Override
	public char peek() {
		if (idx == file.length)
			return 0;
		return (char) file[idx];
	}

	@Override
	public boolean is_done() {
		return idx == file.length;
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
