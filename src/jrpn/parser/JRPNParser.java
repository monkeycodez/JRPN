package jrpn.parser;

import jrpn.lexer.TokenProvider;
import jrpn.syn.*;

/*
 * NUM:	 num
 * IDEN: iden
 * STRING: str
 * REF: 	COMMA IDEN
 * OREF:	VAL COMMA DOT IDEN
 * STATEMENT: NUM | REF | OREF | STRING | IDEN | BLOCK
 * BLOCK: { STATEMENTS... }
 */
public class JRPNParser {

	TokenProvider	prov;

	public JRPNParser(TokenProvider from) {
		prov = from;
	}

	private IdenExpr parse_iden(Token top) {
		return new IdenExpr(top);
	}

	public Expr parse_statement() {
		Token top = prov.next();
		Expr ex = null;
		switch (top.type) {
			case EXCL:
			case CALL:
				ex = new CallExpr(top);
				break;
			case BSLASH:
				break;
			case COMMA:
				break;
			case DOT:
				if (prov.peek().type == TType.IDEN) {
					Token nxt = prov.next();
					ex = new DotExpr(top, nxt.text);
				}
				break;
			case EOF:
				ex = new ExitExpr(top);
				break;
			case IDEN:
				ex = parse_iden(top);
				break;
			case LBRACE:
				break;
			case LBRACK:
				break;
			case LPAREN:
				break;
			case NUM:
				ex = new NumberExpr(top);
				break;
			case RBRACE:
			case RBRACK:
			case RPAREN:
				// TODO throw error
				break;
			case STRING:
				ex = new StrExpr(top);
				break;
			case SET:
				ex = new SetExpr(top);
			default:
				break;

		}
		return ex;
	}

}
