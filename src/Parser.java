/* 		OBJECT-ORIENTED RECOGNIZER FOR SIMPLE EXPRESSIONS
expr    -> term   (+ | -) expr | term
term    -> factor (* | /) term | factor
factor  -> int_lit | '(' expr ')'     
*/

public class Parser {
	public static void main(String[] args) {
		System.out.println("Enter a program which ends with Keyword \"end\"\n");
		Lexer.lex();
		new Program();
		Code.output();
	}
}
class Program {
	Decls dec;
	Stmts stmts;
	public Program() {
		dec = new Decls();
		stmts = new Stmts();
		if(Lexer.nextToken == Token.END) {
			Code.gen(Code.lineNumber + ": return");
		}
	}
}

class Decls {
	IDlist idlist;
	public Decls() {
		if (Lexer.nextToken == Token.INT) {
			Lexer.lex();
			idlist = new IDlist();
		}
	}
}
class IDlist {
	public IDlist() {	
		while(Lexer.nextToken != Token.SEMICOLON) {
			if(Lexer.nextToken == Token.ID){
				Code.storeId(Lexer.id);
			}
			Lexer.lex();
		}
	}
}
class Stmts {
	Stmt statement;
	Stmts statements;
	public Stmts() {
		statement = new Stmt();
		if (Lexer.nextToken != Token.END) {
			statements = new Stmts();
		}		
	}
	
}

class Stmt {
	Assign assign;
	Cmpd cmpd;
	Cond cond;
	Loop loop;
	
	public Stmt() {
		switch (Lexer.nextToken){
		case Token.ID  :
			assign = new Assign();
			break;
		case Token.LBRACE :
			cmpd = new Cmpd();
			break;
		case Token.IF :
			cond = new Cond();
            break;
		case Token.FOR :
			loop = new Loop();
			break;
		default :
            Lexer.lex();
            break;
		}
	}
	
	
}

class Assign {
	
	Expr expr;
	public Assign(){
		if(Lexer.nextToken == Token.ID){
			int rhs = Code.search(Lexer.id);
			Lexer.lex();
			if(Lexer.nextToken == Token.ASSIGN_OP){
				Lexer.lex();
				expr = new Expr();
			}
			Code.gen(Code.store(rhs));
		}
		
	}

}

class Cmpd {
	Stmts statements;
	public Cmpd () {
		if (Lexer.nextToken == Token.LBRACE) {
			statements = new Stmts();
			Lexer.lex();
			if (Lexer.nextToken == Token.RBRACE){
				Lexer.lex();
			}
		}
	}
}

class Cond {
}

class Loop {
	
}
class Expr   { // expr -> term (+ | -) expr | terms
	Term t;
	Expr e;
	char op;

	public Expr() {
		t = new Term();
		if (Lexer.nextToken == Token.ADD_OP || Lexer.nextToken == Token.SUB_OP) {
			op = Lexer.nextChar;
			Lexer.lex();
			e = new Expr();
			Code.gen(Code.opcode(op));	 
		}
	}
}

class Rexpr {
	Expr expr1;
	Expr expr2;
	public Rexpr() {
		expr1 = new Expr();
		Lexer.lex();
		switch(Lexer.nextToken) {
		   case Token.LESS_THAN:
			   expr2 = new Expr();
		       break;
		   case Token.GREATER_THAN:
			   expr2 = new Expr();
		       break;
		   case Token.EQUAL_TO :
			   expr2 = new Expr();
		       break;
		   case Token.NOT_EQUAL_TO :
			   expr2 = new Expr();
		       break;
		   default :
			   break;
		}
	}
}
class Term    { // term -> factor (* | /) term | factor
	Factor f;
	Term t;
	char op;

	public Term() {
		f = new Factor();
		if (Lexer.nextToken == Token.MULT_OP || Lexer.nextToken == Token.DIV_OP) {
			op = Lexer.nextChar;
			Lexer.lex();
			t = new Term();
			Code.gen(Code.opcode(op));
			}
	}
}

class Factor { // factor -> number | '(' expr ')'
	Expr e;
	int i;

	public Factor() {
		switch (Lexer.nextToken) {
		case Token.INT_LIT: // number
			i = Lexer.intValue;
			Code.gen(Code.intcode(i));
			Lexer.lex();
			break;
		case Token.LEFT_PAREN: // '('
			Lexer.lex();
			e = new Expr();
			Lexer.lex(); // skip over ')'
			break;
		default:
			break;
		}
	}
}

class Code {
	static String[] code = new String[100];
	static int codeptr = 0;
	static char[] ids = new char[27];
	static int idpointer = 0;
	static int lineNumber = 0;
	
	public static void gen(String s) {
		code[codeptr] = s;
		codeptr++;
	}
	
	public static String store(int rhs) {
		// TODO Auto-generated method stub
		return (lineNumber++) + ": istore_" + rhs;
	}

	public static String intcode(int i) {
		int variable = lineNumber;
		if (i > 127){
			lineNumber = lineNumber + 3;
			return variable +  ": sipush " + i;
		}
		if (i > 5){
			lineNumber = lineNumber + 2;
			return variable + ": bipush " + i;
		}
		return (lineNumber++) + ": iconst_" + i;
	}
	
	public static String opcode(char op) {
		switch(op) {
		case '+' : return (lineNumber++) + "iadd";
		case '-':  return (lineNumber++) + "isub";
		case '*':  return (lineNumber++) + "imul";
		case '/':  return (lineNumber++) + "idiv";
		default: return "";
		}
	}
	
	public static void output() {
		for (int i=0; i<codeptr; i++)
			System.out.println(code[i]);
	}
	
	public static void storeId(char c){
		ids[idpointer] = c;
		idpointer++;
	}
	
	public static int search(char c){
		for(int i=0;i<ids.length;i++){
			if(ids[i] == c){
				return i+1;
			}
		}
		return -1;
	}
}


