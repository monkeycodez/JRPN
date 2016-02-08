package jrpn.parser;

import java.util.Arrays;

import jrpn.run.JRPNCodeObj;

public class CChunkBuilder {

	static final int	START_SZ	= 200;

	int					opcodes[], args[], linenos[];
	int					idx			= 0;
	String				source;

	CChunkBuilder(String src) {
		source = src;
		opcodes = new int[START_SZ];
		args = new int[START_SZ];
		linenos = new int[START_SZ];
	}

	void _ensure_cap() {
		if (idx + 1 == opcodes.length) {
			// realloc arrays
			opcodes = Arrays.copyOf(opcodes, opcodes.length * 3 / 2);
			args = Arrays.copyOf(args, opcodes.length * 3 / 2);
			linenos = Arrays.copyOf(linenos, opcodes.length * 3 / 2);

		}
	}

	public void add_instr(int code, int arg, int lineno) {
		_ensure_cap();
		opcodes[idx] = code;
		args[idx] = arg;
		linenos[idx] = lineno;
		idx++;
	}

	public JRPNCodeObj create_code() {
		// trim arrays
		opcodes = Arrays.copyOf(opcodes, idx);
		args = Arrays.copyOf(args, idx);
		linenos = Arrays.copyOf(linenos, idx);
		return new JRPNCodeObj(opcodes, args, linenos, source);
	}

}
