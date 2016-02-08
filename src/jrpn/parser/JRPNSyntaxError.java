package jrpn.parser;

import jrpn.run.JRPNException;
import jrpn.syn.Token;

public class JRPNSyntaxError extends JRPNException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public JRPNSyntaxError(Token at, String msg) {
		this(msg, at.source, at.lineno);
	}

	public JRPNSyntaxError(Exception e) {
		super(e);
	}

	public JRPNSyntaxError(String message, String fileName, int lineNumber,
			int columnNumber) {
		super(message, fileName, lineNumber, columnNumber);
	}

	public JRPNSyntaxError(String message, String fileName, int lineNumber) {
		super(message, fileName, lineNumber);
	}

	public JRPNSyntaxError(String s) {
		super(s);
	}

}
