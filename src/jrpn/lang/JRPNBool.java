package jrpn.lang;

public class JRPNBool implements JRPNObj {

	public static final JRPNBool	TRUE	= new JRPNBool() {
												@Override
												public String toString() {
													return "true";
												}
											};

	public static final JRPNBool	FALSE	= new JRPNBool() {
												@Override
												public String toString() {
													return "false";
												}
											};

}
