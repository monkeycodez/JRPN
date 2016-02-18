package jrpn.run;

import java.util.*;

import jrpn.lang.*;

public class JRPNList implements JRPNObj {

	List<JRPNRef>	lst	= new ArrayList<>();

	public void add(JRPNObj o){
		lst.add(new JRPNRef(o));
	}
	
	
	@Override
	public String toString() {
		return lst.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return lst.equals(obj);
	}
}
