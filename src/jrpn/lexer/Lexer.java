package jrpn.lexer;

import jrpn.syn.*;

public class Lexer extends AbstractTokenProvider {

	private LexReader	rdr;

	public Lexer(LexReader l) {
		rdr = l;
	}

	private Token _ntk(TType t) {
		return new Token(t, rdr.lineno(), rdr.source());
	}

	private Token _ntk(TType t, String text) {
		return new Token(t, text, rdr.lineno(), rdr.source());
	}

	private StringBuilder	str;

	private Token _parse_str() {
		do {
			char c = rdr.next();
			if (c == '\"') {
				break;
			} else if (c == '\\') {
				c = rdr.next();
				switch (c) {
					case '\n':
						str.append('\n');
						break;
					case '\t':
						str.append('\t');
						break;
					default:
						str.append(c);
						break;
				}
				c = rdr.next();
				continue;
			}
			str.append(c);
		} while (!rdr.is_done());
		return _ntk(TType.STRING, str.toString());
	}

	private Token _parse_iden() {
		do {
			char c = rdr.next();
			if (Character.isWhitespace(c)) {
				break;
			} else if (TType.punct.containsKey(c)) {
				break;
			} else if (c == '\"') {
				// TODO throw error
			}
			str.append(c);
		} while (!rdr.is_done());
		String val = str.toString();
		if (TType.keywds.containsKey(val)) {
			return _ntk(TType.keywds.get(val));
		}
		return _ntk(TType.IDEN, str.toString());
	}

	private Token _parse_num() {
		do {
			char c = rdr.next();

			if (Character.isWhitespace(c)) {
				break;
			} else if (Character.isDigit(c) || c == '.') {
				str.append(c);
				continue;
			}
			// TODO throw error
		} while (!rdr.is_done());
		return _ntk(TType.NUM, str.toString());
	}

	@Override
	protected Token _next() {
		if (rdr.is_done())
			return _ntk(TType.EOF);
		char c = rdr.next();
		if (Character.isWhitespace(c))
			return _next();
		TType tp = TType.punct.get(c);
		if (tp != null) {
			return _ntk(tp);
		}
		str = new StringBuilder();
		if (c == '\"') {
			return _parse_str();
		}
		str.append(c);
		if (c == '-') {
			if (Character.isDigit(rdr.peek()) || rdr.peek() == '.') {
				return _parse_iden();
			}
		} else if (Character.isDigit(c)) {
			_parse_num();
		}
		return _parse_iden();
	}

}
