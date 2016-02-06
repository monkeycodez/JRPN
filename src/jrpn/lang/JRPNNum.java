package jrpn.lang;

public class JRPNNum implements JRPNObj {

	public final double	val;

	public JRPNNum(double v) {
		val = v;
	}

	public String toString() {
		return Double.toString(val);
	}

}
