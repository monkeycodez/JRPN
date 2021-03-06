package jrpn.parser;

import java.util.*;

import jrpn.parser.lexer.TokenProvider;
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

	private Expr parse_comma(Token top) throws JRPNException {
		Token nxt = prov.next();
		if (nxt.type == TType.IDEN) {
			return new RefExpr(top, nxt);
		} else if (nxt.type == TType.DOT) {
			nxt = prov.next();
			if (nxt.type == TType.IDEN) {
				return new RefDotExpr(top, nxt);
			}
		}else if(nxt.type == TType.AT){
			return RefExpr.getr_listidx(top, parse_statement());
			
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
		if (args.size() > 0) {
			if (args.get(0).text.equals("this")) {
				body.nthis = true;
			}
		}
		return body;
	}

	private Expr parse_map(Token top) throws JRPNException {
		List<Expr> k = new ArrayList<>(), v = new ArrayList<>();
		while (true) {
			Token t = prov.peek();
			if (t.type == TType.RBRACK) {
				prov.next();
				break;
			} else if (t.type == TType.EOF) {
				throw new JRPNSyntaxError(t, "Unexpected EOF");
			}
			k.add(parse_statement());
			t = prov.peek();
			if (t.type != TType.COLON) {
				throw new JRPNSyntaxError(t, "Unexpected statement");
			}
			prov.next();
			v.add(parse_statement());
		}
		return new MapExpr(top, k.toArray(new Expr[1]), v.toArray(new Expr[1]));
	}

	private Expr parse_if(Token top) throws JRPNException {
		Expr cond = parse_statement();
		Expr bod = parse_statement();
		if (prov.peek().type == TType.ELSE) {
			prov.next();
			return new IfElseExpr(top, cond, bod, parse_statement());
		} else if (prov.peek().type == TType.ELIF) {
			List<Expr> elifc = new LinkedList<>(), elifb = new LinkedList<>();
			while (prov.peek().type == TType.ELIF) {
				prov.next();
				elifc.add(parse_statement());
				elifb.add(parse_statement());
			}
			if (prov.peek().type == TType.ELSE) {
				prov.next();
				return new IfElIfElseExpr(top, cond, bod, elifc, elifb,
						parse_statement());
			}
			return new IfElIfExpr(top, cond, bod, elifc, elifb);
		}
		return new IfExpr(top, cond, bod);
	}

	private Expr parse_while(Token top) throws JRPNException {
		Expr cond = parse_statement();
		Expr body = parse_statement();
		return new WhileExpr(top, cond, body);
	}

	public boolean is_done() throws JRPNException {
		return prov.is_done();
	}
	
	private Expr parse_list(Token top) throws JRPNException{
		ListExpr e = new ListExpr(top);
		
		while(prov.peek().type != TType.RPAREN){
			Expr ex = parse_statement();
			e.items.add(ex);
		}
		prov.next();
		return e;
	}

	public Expr parse_statement() throws JRPNException {
		Token top = prov.next();
		Expr ex = null;
		switch (top.type) {
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
			case LBRACK:
				ex = parse_map(top);
				break;
			case LPAREN: 
				ex = parse_list(top);
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
				break;
			case FALSE:
				ex = Expr.false_expr(top);
				break;
			case TRUE:
				ex = Expr.true_expr(top);
				break;
			case IF:
				ex = parse_if(top);
				break;
			case WHILE:
				ex = parse_while(top);
				break;
			case AT:
				ex = Expr.index_expr(top, parse_statement());
				break; 
			case RPAREN:
			case RBRACK:
			case RBRACE:
			case ELSE:
			case ELIF:
			case COLON:
			case EXCL:
				break;

		}
		if (ex == null) {
			throw new JRPNSyntaxError(prov.peek(), "Unexpected token "
					+ top.type + " text: " + top.text + " at: ");
		}
		if(prov.peek().type == TType.EXCL){
			return Expr.itg_call_expr(prov.next(), ex);
		}
		return ex;
	}

}
