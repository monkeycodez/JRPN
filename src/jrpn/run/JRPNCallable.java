package jrpn.run;

import jrpn.lang.JRPNObj;

public interface JRPNCallable extends JRPNObj {

	public int call(JRPNEnv env, int ip);

}
