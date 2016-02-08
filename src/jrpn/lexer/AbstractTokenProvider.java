package jrpn.lexer;

import java.util.LinkedList;

import jrpn.run.JRPNException;
import jrpn.syn.*;

public abstract class AbstractTokenProvider implements TokenProvider {

	private boolean				done	= false;

	private LinkedList<Token>	tkque	= new LinkedList<>();

	private void _ensure_cap(int num) throws JRPNException {
		if (tkque.size() < num) {
			for (int i = tkque.size(); i < num; i++) {
				tkque.add(_next());
			}
		}
	}

	@Override
	public Token next() throws JRPNException {
		_ensure_cap(1);
		Token t = tkque.poll();
		if (t.type == TType.EOF)
			done = true;
		return t;
	}

	@Override
	public Token peek() throws JRPNException {
		_ensure_cap(1);
		return tkque.peek();
	}

	@Override
	public Token peek(int num) throws JRPNException {
		_ensure_cap(num);
		return tkque.get(num - 1);
	}

	@Override
	public boolean is_done() {
		return done;
	}

	protected abstract Token _next() throws JRPNException;

}
