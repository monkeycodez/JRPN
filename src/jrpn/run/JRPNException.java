package jrpn.run;

import javax.script.ScriptException;

public class JRPNException extends ScriptException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JRPNException(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	public JRPNException(String message, String fileName, int lineNumber,
			int columnNumber) {
		super(message, fileName, lineNumber, columnNumber);
		// TODO Auto-generated constructor stub
	}

	public JRPNException(String message, String fileName, int lineNumber) {
		super(message, fileName, lineNumber);
		// TODO Auto-generated constructor stub
	}

	public JRPNException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

}
