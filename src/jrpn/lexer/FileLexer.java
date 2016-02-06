package jrpn.lexer;

import java.io.IOException;
import java.nio.file.*;

public class FileLexer implements LexReader {

	public final String source;

	private byte[] file;

	private int idx = 0, lineno = 1;

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
		if (idx + 1 == file.length)
			return 0;
		return (char) file[idx + 1];
	}

	@Override
	public boolean is_done() {
		return idx == file.length;
	}

	@Override
	public int lineno() {
		return 0;
	}

	@Override
	public String source() {
		return source;
	}

}
