package jrpn.run;

public final class JRPNVMCodes {

	public static final byte	NOP	= 0, EXIT = 1, DUP = 2, PUSHC = 3, POP = 4,
			GETV = 5, PRINT = 6, EPRINT = 7, CALL = 8, SET = 9, REF = 10,
			BREAK = 11, PUSHFRAME = 12, POPFRAME = 13, NEWMAP = 14,
			MAPSETM = 15, MAPCGET = 16, MAPCGETR = 17, JMP = 18, JMPIFFN = 19;

	/*
	 * Code useage
	 * 
	 * Function
	 * 	From: GETV arg1 GETV arg2... PUSHC func CALL
	 *  func: PUSHFRAME REf argN SET ... REF arg1 SET (FUNCTION BODY) POPFRAME
	 *  
	 * If-Then:
	 * 	PUSHC cond  (Must return a bool
	 * 	CALL
	 * 	JMPIFF end   (jumps to the end if FALSE
	 * 	PUSHC body
	 *  CALL
	 *  end: ... rest of the code
	 *  
	 * While:
	 * 	start: PUSHC cond
	 * 	CALL
	 * 	JMPIFFN end
	 * 	PUSHC body
	 * 	CALL
	 * 	JMP start 
	 * 	end: ... rest of code
	 * 
	 */

	private JRPNVMCodes() {
	}
}
