package jrpn.lang;

public class JRPNNum extends Number implements JRPNObj {

	private static final double	SIGMA				= .001;

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	public final double			val;

	public JRPNNum(double v) {
		val = v;
	}

	public String toString() {
		return Double.toString(val);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof Number) {
			return SIGMA > ((Number) obj).doubleValue() - val;
		}
		return super.equals(obj);
	}

	@Override
	public int intValue() {
		return (int) val;
	}

	@Override
	public long longValue() {
		return (long) val;
	}

	@Override
	public float floatValue() {
		return (float) val;
	}

	@Override
	public double doubleValue() {
		return val;
	}
}
