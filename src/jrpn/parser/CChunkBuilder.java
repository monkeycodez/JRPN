package jrpn.parser;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import jrpn.run.*;

public class CChunkBuilder {

	private static final AtomicInteger	id_gen	= new AtomicInteger();

	public static int get_uid() {
		return id_gen.getAndIncrement();
	}

	private static final int	START_SZ	= 200;

	int							opcodes[], args[], linenos[];
	int							idx			= 0;
	String						source;

	Map<String, Integer>		marks		= new HashMap<>();
	Map<String, List<Integer>>	to_mark		= new HashMap<>();

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

	public void set_mark(String name) {
		marks.put(name, idx);
		if (to_mark.get(name) == null)
			return;
		for (int i : to_mark.get(name)) {
			args[i] = idx;
		}
	}

	public void add_instr(int code, String mark, int lineno) {
		_ensure_cap();
		opcodes[idx] = code;
		args[idx] = -1;
		linenos[idx] = lineno;
		Integer i = marks.get(mark);
		if (i == null) {
			List<Integer> tm = to_mark.get(mark);
			if (tm == null) {
				tm = new LinkedList<>();
				to_mark.put(mark, tm);
			}
			tm.add(idx);
		} else {
			args[idx] = i;
		}
		idx++;
	}

	public void add_instr(int code, int arg, int lineno) {
		_ensure_cap();
		opcodes[idx] = code;
		args[idx] = arg;
		linenos[idx] = lineno;
		idx++;
	}

	public void call_instr(int lineno) {
		add_instr(JRPNVMCodes.CALL, -1, lineno);
	}

	public JRPNCodeObj create_code() {
		// trim arrays
		opcodes = Arrays.copyOf(opcodes, idx);
		args = Arrays.copyOf(args, idx);
		linenos = Arrays.copyOf(linenos, idx);
		return new JRPNCodeObj(opcodes, args, linenos, source);
	}

}
