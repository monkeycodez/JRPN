package jrpn.run;

public final class JRPNVMCodes {

	public static final byte	NOP	= 0, EXIT = 1, DUP = 2, PUSHC = 3, POP = 4,
			GETV = 5, PRINT = 6, EPRINT = 7, CALL = 8, SET = 9, REF = 10,
			BREAK = 11, PUSHFRAME = 12, POPFRAME = 13, NEWMAP = 14,
			MAPSETM = 15, MAPCGET = 16, MAPCGETR = 17;

	private JRPNVMCodes() {
	}
}
