package jrpn.parser;

import jrpn.run.*;
import jrpn.syn.Token;

public abstract class Expr {

	private Token	from;

	public Expr(Token from) {
		this.setFrom(from);
	}

	public abstract void compile(ExeBuilder comp, CChunkBuilder chunk);

	public static Expr push_frame_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.PUSHFRAME, 0, t.lineno);
			}

		};
	}

	public static Expr pop_frame_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.POPFRAME, 0, t.lineno);
			}

		};
	}

	public static Expr println_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.PRINT, 0, t.lineno);
			}

		};
	}

	public static Expr eprintln_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.EPRINT, 0, t.lineno);
			}

		};
	}

	public static Expr call_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.CALL, 0, t.lineno);
			}

		};
	}
	
	public static Expr itg_call_expr(Token t, Expr nxt) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				nxt.compile(comp, chunk);
				chunk.add_instr(JRPNVMCodes.CALL, 0, t.lineno);
			}

		};
	}

	public static Expr true_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.PUSHC, 1, t.lineno);
			}

		};
	}

	public static Expr false_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.PUSHC, 2, t.lineno);
			}

		};
	}

	public static Expr exit_expr(Token t) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				chunk.add_instr(JRPNVMCodes.EXIT, 0, t.lineno);
			}

		};
	}
	
	public static Expr index_expr(Token t, Expr idx) {
		return new Expr(t) {

			@Override
			public void compile(ExeBuilder comp, CChunkBuilder chunk) {
				idx.compile(comp, chunk);
				chunk.add_instr(JRPNVMCodes.LISTGET, 0, t.lineno);
			}

		};
	}

	public Token getFrom() {
		return from;
	}

	public void setFrom(Token from) {
		this.from = from;
	}

}
