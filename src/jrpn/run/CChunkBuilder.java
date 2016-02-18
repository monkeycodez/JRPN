package jrpn.run;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CChunkBuilder {

	private static final AtomicInteger	id_gen	= new AtomicInteger();

	public static int get_uid() {
		return id_gen.getAndIncrement();
	}

	private static final int	START_SZ	= 200;

	JRPNCodeObj					cobj;

	int							idx			= 0;
	String						source;

	Map<String, Integer>		marks		= new HashMap<>();
	Map<String, List<Integer>>	to_mark		= new HashMap<>();
	public boolean				nthis		= false;

	public CChunkBuilder(String src) {
		source = src;
		cobj = new JRPNCodeObj(src);
		cobj.code = new int[START_SZ];
		cobj.arg = new int[START_SZ];
		cobj.lineno = new int[START_SZ];
	}

	void _ensure_cap() {
		if (idx + 1 == cobj.code.length) {
			// realloc arrays
			cobj.code = Arrays.copyOf(cobj.code, cobj.code.length * 3 / 2);
			cobj.arg = Arrays.copyOf(cobj.arg, cobj.code.length * 3 / 2);
			cobj.lineno = Arrays.copyOf(cobj.lineno, cobj.code.length * 3 / 2);

		}
	}

	public void set_mark(String name) {
		marks.put(name, idx);
		if (to_mark.get(name) == null)
			return;
		for (int i : to_mark.get(name)) {
			cobj.arg[i] = idx;
		}
	}

	public void add_instr(int code, String mark, int lineno) {
		_ensure_cap();
		cobj.code[idx] = code;
		cobj.arg[idx] = -1;
		cobj.lineno[idx] = lineno;
		Integer i = marks.get(mark);
		if (i == null) {
			List<Integer> tm = to_mark.get(mark);
			if (tm == null) {
				tm = new LinkedList<>();
				to_mark.put(mark, tm);
			}
			tm.add(idx);
		} else {
			cobj.arg[idx] = i;
		}
		idx++;
	}

	public void add_instr(int code, int arg, int lineno) {
		_ensure_cap();
		cobj.code[idx] = code;
		cobj.arg[idx] = arg;
		cobj.lineno[idx] = lineno;
		idx++;
	}

	public void set_this() {
		cobj.nt = true;
	}

	public void call_instr(int lineno) {
		add_instr(JRPNVMCodes.CALL, -1, lineno);
	}

	public JRPNCodeObj create_code() {
		// trim arrays
		cobj.code = Arrays.copyOf(cobj.code, idx);
		cobj.arg = Arrays.copyOf(cobj.arg, idx);
		cobj.lineno = Arrays.copyOf(cobj.lineno, idx);
		return cobj;
	}

}
