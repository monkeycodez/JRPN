package jrpn.parser;

import java.util.*;

import jrpn.lexer.TokenProvider;
import jrpn.run.JRPNException;
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
	Expr			prev;

	public JRPNParser(TokenProvider from) {
		prov = from;
	}

	private IdenExpr parse_iden(Token top) {
		return new IdenExpr(top);
	}

	private RefExpr parse_comma(Token top) throws JRPNException {
		Token nxt = prov.next();
		if (nxt.type == TType.IDEN) {
			return new RefExpr(top, nxt);
		}
		throw new JRPNSyntaxError(nxt, "invalid REF statement");
	}

	private BlockExpr parse_block(Token top) throws JRPNException {
		BlockExpr b = new BlockExpr(top);
		while (true) {
			TType n = prov.peek().type;
			if (n == TType.RBRACE)
				break;
			if (n == TType.EOF)
				throw new JRPNSyntaxError(prov.peek(),
						"Unexpected EOF in block");
			b.stmts.add(parse_statement());
		}
		prov.next();
		return b;
	}

	private Expr parse_func(Token top) throws JRPNException {
		// First, grab args, args may be none, one, or (arg, arg ...)
		Token n = prov.peek();
		List<Token> args = new LinkedList<>();
		switch (n.type) {
			case IDEN:
				args.add(prov.next());
				break;
			case LPAREN:
				prov.next();
				while (true) {
					n = prov.next();
					if (n.type == TType.RPAREN) {
						System.out.println("Breaking");
						break;
					} else if (n.type == TType.IDEN) {
						args.add(n);
					} else {
						throw new JRPNSyntaxError(n, "Unknown token: " + n.type);
					}
				}
				break;
			default:
				return null;
		}
		if (prov.peek().type != TType.LBRACE) {
			throw new JRPNSyntaxError(n, "Function does not have a body");
		}
		// Grab the method body
		BlockExpr body = parse_block(prov.next());
		// Push the args onto the stack
		for (Token t : args) {
			RefExpr a = new RefExpr(t, t);
			SetExpr s = new SetExpr(t);
			body.stmts.addFirst(s);
			body.stmts.addFirst(a);
		}
		body.stmts.addFirst(Expr.push_frame_expr(top));
		body.stmts.addLast(Expr.pop_frame_expr(prov.peek()));
		return body;
	}

	public Expr parse_statement() throws JRPNException {
		Token top = prov.next();
		Expr ex = null;
		switch (top.type) {
			case EXCL:
			case CALL:
				ex = Expr.call_expr(top);
				break;
			case BSLASH:
				throw new JRPNSyntaxError(top, "Unexpected backslash");
			case COMMA:
				ex = parse_comma(top);
				break;
			case DOT:
				if (prov.peek().type == TType.IDEN) {
					Token nxt = prov.next();
					ex = new DotExpr(top, nxt.text);
				}
				break;
			case EOF:
				ex = Expr.exit_expr(top);
				break;
			case IDEN:
				ex = parse_iden(top);
				break;
			case LBRACE:
				ex = parse_block(top);
				break;
			case LBRACK: // TODO impl map
				break;
			case LPAREN: // TODO impl list
				break;
			case NUM:
				ex = new NumberExpr(top);
				break;
			case STRING:
				ex = new StrExpr(top);
				break;
			case SET:
				ex = new SetExpr(top);
				break;
			case POP:
				ex = new PopExpr(top);
				break;
			case FUNC:
				ex = parse_func(top);
				break;
			case PRINTLN:
				ex = Expr.println_expr(top);
				break;
			case EPRINTLN:
				ex = Expr.eprintln_expr(top);
			default:
				break;

		}
		if (ex == null) {
			throw new JRPNSyntaxError(prov.peek(), "Unexpected token "
					+ top.type + " text: " + top.text + " at: ");
		}
		prev = ex;
		return ex;
	}

}
