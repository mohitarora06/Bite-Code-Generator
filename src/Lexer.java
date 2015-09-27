import java.io.*;

public class Lexer {

	static private char ch = ' ';
	static private char ident = ' ';
	static private Buffer buffer = new Buffer(new DataInputStream(System.in));
	static public int nextToken;
	static public char nextChar;
	static public int intValue;
	static public char id = ' ';

	public static int lex() {
		while (Character.isWhitespace(ch))
			ch = buffer.getChar();
		if (Character.isLetter(ch)) {
			ident = Character.toLowerCase(ch);
			ch = buffer.getChar();
			if (ident == 'i' && ch == 'f'){
				ch = buffer.getChar();
				nextToken = Token.IF;
			}
			else if (ident == 'f' && ch == 'o'){
				ch = buffer.getChar();
				ch = buffer.getChar();
				nextToken = Token.FOR;
			}
			else if (ident == 'e' && ch == 'l'){
				ch = buffer.getChar();
				ch = buffer.getChar();
				ch = buffer.getChar();
				nextToken = Token.ELSE;
			}
			else if (ident == 'e' && ch == 'n'){
				ch = buffer.getChar();
				ch = buffer.getChar();
				nextToken = Token.END;
			}
			else if (ident == 'i' && ch == 'n'){
				ch = buffer.getChar();
				ch = buffer.getChar();
				nextToken = Token.INT;
			}
			else {
				id = ident;
				nextToken = Token.ID;
			}
		} else if (Character.isDigit(ch)) {
			nextToken = getNumToken(); // intValue would be set
		} else {
			nextChar = ch;
			switch (ch) {
			case ';':
				nextToken = Token.SEMICOLON;
				break;
			case ',':
				nextToken = Token.COMMA;
				break;
			case '+':
				nextToken = Token.ADD_OP;
				break;
			case '-':
				nextToken = Token.SUB_OP;
				break;
			case '*':
				nextToken = Token.MULT_OP;
				break;
			case '/':
				nextToken = Token.DIV_OP;
				break;
			case '=':
				ident = ch;
				ch = buffer.getChar();
				if(ch == '=') nextToken = Token.EQUAL_TO;
				else nextToken = Token.ASSIGN_OP;
				break;
			case '(':
				nextToken = Token.LEFT_PAREN;
				break;
			case ')':
				nextToken = Token.RIGHT_PAREN;
				break;
			case '{':
				nextToken = Token.LBRACE;
				break;
			case '<':
				nextToken = Token.LESS_THAN;
				break;
			case '>':
				nextToken = Token.GREATER_THAN;
				break;
			case '!':
				ch = buffer.getChar();
				nextToken = Token.NOT_EQUAL_TO;
				break;
			default:
				error("Illegal character " + ch);
				break;
			}
			ch = buffer.getChar();
		}
		return nextToken;
	} // lex

	private static int getNumToken() {
		int num = 0;
		do {
			num = num * 10 + Character.digit(ch, 10);
			ch = buffer.getChar();
		} while (Character.isDigit(ch));
		intValue = num;
		return Token.INT_LIT;
	}
	
	public int number() {
		return intValue;
	} // number

	public Character identifier() {
		return ident;
	} // letter

	public static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	} // error


}
