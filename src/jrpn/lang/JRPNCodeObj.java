package jrpn.lang;

public class JRPNCodeObj implements JRPNObj {

	public final int	code[];
	public final int	arg[];
	public int			lineno[];
	public String		source;

	public JRPNCodeObj(int code[], int arg[]) {
		this.code = code;
		this.arg = arg;
	}

}
