package jrpn.run;

import jrpn.lang.*;

public interface JRPNCallable extends JRPNObj {

	public int call(JRPNEnv env, int ip);

	//NOW, begin built in functions

	abstract static class builtin implements JRPNCallable {

		abstract void _call(JRPNEnv env);

		@Override
		public int call(JRPNEnv env, int ip) {
			_call(env);
			return ip;
		}

	}

	static final JRPNCallable	plus	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val(), n2 =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(n.val
														+ n2.val));

											}
										};

	static final JRPNCallable	minus	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val(), n2 =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(n.val
														- n2.val));

											}
										};

	static final JRPNCallable	mult	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val(), n2 =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(n.val
														* n2.val));

											}
										};

	static final JRPNCallable	div		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val(), n2 =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(n.val
														/ n2.val));

											}
										};

	static final JRPNCallable	mod		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val(), n2 =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(n.val
														% n2.val));

											}
										};

	static final JRPNCallable	floor	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNNum n =
														(JRPNNum) env.pop_val();
												env.push_val(new JRPNNum(
														(int) n.val));

											}
										};

}
