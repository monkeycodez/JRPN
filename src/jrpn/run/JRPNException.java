package jrpn.run;

import javax.script.ScriptException;

public class JRPNException extends ScriptException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JRPNException(Exception e) {
		super(e);
	}

	public JRPNException(String message, String fileName, int lineNumber,
			int columnNumber) {
		super(message, fileName, lineNumber, columnNumber);
	}

	public JRPNException(String message, String fileName, int lineNumber) {
		super(message, fileName, lineNumber);
	}

	public JRPNException(String s) {
		super(s);
	}

}
