package jrpn.parser;

import jrpn.run.JRPNException;

public class JRPNSyntaxError extends JRPNException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JRPNSyntaxError(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	public JRPNSyntaxError(String message, String fileName, int lineNumber,
			int columnNumber) {
		super(message, fileName, lineNumber, columnNumber);
		// TODO Auto-generated constructor stub
	}

	public JRPNSyntaxError(String message, String fileName, int lineNumber) {
		super(message, fileName, lineNumber);
		// TODO Auto-generated constructor stub
	}

	public JRPNSyntaxError(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

}
