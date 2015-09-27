class Token {   
public static final int SEMICOLON = 0; 
public static final int COMMA = 1;
public static final int PERIOD    = 2;   
public static final int ADD_OP    = 3;   
public static final int SUB_OP    = 4;   
public static final int MULT_OP   = 5;   
public static final int DIV_OP    = 6;   
public static final int ASSIGN_OP  = 7;   
public static final int LEFT_PAREN= 8;   
public static final int RIGHT_PAREN= 9;   
public static final int ID    = 10;   
public static final int INT_LIT    = 11;
public static final int END = 12;
public static final int IF = 13;
public static final int FOR = 14;
public static final int LBRACE = 15;
public static final int ELSE = 16;
public static final int INT = 17;  
public static final int RBRACE = 18;
public static final int LESS_THAN = 19;
public static final int GREATER_THAN = 20;
public static final int EQUAL_TO = 21;
public static final int NOT_EQUAL_TO = 22;
private static String[] lexemes = {   
    ";", ",", ".", "+", "-", "*", "/", "=", "(", ")", "ID", "NUMBER","END","IF","FOR","{","ELSE","INT","}","<",">","==","!="
    };   
  
public static String toString (int i) {   
    if (i < 0 || i > NOT_EQUAL_TO)   
       return "";   
    else return lexemes[i];   
}
} 
