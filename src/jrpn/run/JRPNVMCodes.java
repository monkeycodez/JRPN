package jrpn.run;

import java.lang.reflect.Field;
import java.util.*;

import jrpn.lang.JRPNRef;

public final class JRPNVMCodes {

	public static final byte
												EXIT	= 0,
														NOP = 1,
														DUP = 2,
														PUSHC = 3,
														POP = 4,
														GETV = 5,
														PRINT = 6,
														EPRINT = 7,
														CALL = 8,
														SET = 9,
														REF = 10,
														BREAK = 11,
														PUSHFRAME = 12,
														POPFRAME = 13,
														NEWMAP = 14,
														MAPSETM = 15,	// for use by MapExpr
														MAPCGET = 16, //For use by dotexpr
			MAPCGETR = 17, //For use by ,dotexpr
			JMP = 18,
			JMPIFFN = 19, //JMP if the top of the stack is FALSE
			NEWLIST = 20, //
			LISTADDL = 21,													//For use by listexpr only
			LISTGET = 22,
			LISTGETR = 23;
	
	
	public static final Map<String, JRPNRef>	deffunc	= new HashMap<>();

	static {
		Class<JRPNCallable> cl = JRPNCallable.class;
		Field funcs[] = cl.getFields();

		for (Field f : funcs) {
			if (cl.isAssignableFrom(f.getType())) {
				try {
					JRPNCallable c = (JRPNCallable) f.get(null);
					deffunc.put(c.name(), new JRPNRef(c));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

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
