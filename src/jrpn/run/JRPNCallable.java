package jrpn.run;

import jrpn.lang.*;

public interface JRPNCallable extends JRPNObj {

	public int call(JRPNEnv env, int ip);

	default boolean needs_this() {
		return false;
	}

	public default String name() {
		return null;
	}

	public default boolean autoexec() {
		return false;
	}

	//NOW, begin built in functions

	abstract static class builtin implements JRPNCallable {

		abstract void _call(JRPNEnv env);

		public abstract String name();

		public boolean autoexec() {
			return true;
		}

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

											@Override
											public String name() {
												return "+";
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

											@Override
											public String name() {
												return "-";
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

											@Override
											public String name() {
												return "*";
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

											@Override
											public String name() {
												return "/";
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

											@Override
											public String name() {
												return "%";
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

											@Override
											public String name() {
												return "floor";
											}
										};

	static final JRPNCallable	cat		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(new JRPNString(env
														.pop_val().toString()
														+ env.pop_val()
																.toString()));

											}

											@Override
											public String name() {
												return "cat";
											}
										};

	static final JRPNCallable	newmap	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(new JRPNMap());

											}

											@Override
											public String name() {
												return "newmap";
											}
										};

	static final JRPNCallable	equal	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(env.pop_val()
														.equals(env.pop_val()) ? JRPNBool.TRUE
														: JRPNBool.FALSE);

											}

											@Override
											public String name() {
												return "=";
											}
										};

	static final JRPNCallable	nequal	= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(env.pop_val()
														.equals(env.pop_val()) ? JRPNBool.FALSE
														: JRPNBool.TRUE);

											}

											@Override
											public String name() {
												return "/=";
											}
										};

	static final JRPNCallable	not		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(env.pop_val() != JRPNBool.FALSE ? JRPNBool.FALSE
														: JRPNBool.TRUE);

											}

											@Override
											public String name() {
												return "not";
											}
										};

	static final JRPNCallable	or		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNObj a = env.pop_val();
												JRPNObj b = env.pop_val();
												env.push_val(a != JRPNBool.FALSE
														&& b != JRPNBool.FALSE ? JRPNBool.TRUE
														: JRPNBool.FALSE);

											}

											@Override
											public String name() {
												return "||";
											}
										};

	static final JRPNCallable	and		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(env.pop_val() == JRPNBool.FALSE
														|| env.pop_val() == JRPNBool.FALSE ? JRPNBool.FALSE
														: JRPNBool.TRUE);

											}

											@Override
											public String name() {
												return "||";
											}
										};

	static final JRPNCallable	get		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												env.push_val(((JRPNMap) env
														.pop_val()).get(env
														.pop_val()).ref);

											}

											@Override
											public String name() {
												return "||";
											}
										};

	static final JRPNCallable	set		= new builtin() {

											@Override
											public void _call(JRPNEnv env) {
												JRPNMap m =
														(JRPNMap) env.pop_val();
												JRPNRef r =
														m.get(env.pop_val());
												r.ref = env.pop_val();
											}

											@Override
											public String name() {
												return "||";
											}
										};

}
