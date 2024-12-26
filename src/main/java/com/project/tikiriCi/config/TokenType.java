package com.project.tikiriCi.config;

public class TokenType {
   public static String SYMBOL = "[^\\w\\s]+";
   public static String TOKEN = "[\\w]+\\b";
   public static String IDENTIFIER = "\\b[a-zA-Z_]\\w*\\b";
   public static String CONSTANT = "^[0-9]+\\b$";
   public static String INTEGER = "int\\b";
   public static String VOID = "void\\b";
   public static String RETURN = "\\breturn\\b";
   public static String IF = "\\bif\\b";
   public static String ELSE = "\\belse\\b";
   public static String LEFT_PARAN = "^\\($";
   public static String RIGHT_PARAN = "^\\)$";
   public static String LEFT_BRACE = "^\\{$";
   public static String RIGHT_BRACE = "^\\}$"; 
   public static String SEMICOLON = "^;$";
   public static String NOT = "^\\!$";
   public static String QUESTION_MARK = "^\\?$";
   public static String COLON = "^\\:$";
   public static String AND = "&&";
   public static String OR = "\\|\\|";
   public static String EQUAL = "^==$"; 
   public static String ASSIGN = "^=$"; 
   public static String NOTEQUAL = "^!=$";
   public static String LEFT_CHEVRON = "^<$";
   public static String RIGHT_CHEVRON = "^>$";
   public static String EQUAL_RIGHT_CHEVRON = "^>=$";
   public static String EQUAL_LEFT_CHEVRON = "^<=$";
   public static String TYPE = "int\\b";
   public static String COMPLEMENT = "\\~";
   public static String SUB = "^\\-$";
   public static String PLUS = "^\\+$";
   public static String MUL = "^\\*$";
   public static String DIV = "^\\/$";
   public static String MOD = "^\\%$";
   public static String DOUBLE_SUB = "\\--";
   public static String NULL = "null";
   public static char WHITESPACE =  ' ';
   public static char TAB = '\t';
   public static char NEW_LINE = '\n';
   public static String [] SYMBOLS = {QUESTION_MARK, COLON, ASSIGN, EQUAL_RIGHT_CHEVRON ,EQUAL_LEFT_CHEVRON, RIGHT_CHEVRON, LEFT_CHEVRON ,NOTEQUAL, EQUAL, OR, AND, NOT, LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE,
      SEMICOLON,COMPLEMENT, SUB, PLUS, MUL, DIV, MOD};
   public static String[] BINARY_OPS = {QUESTION_MARK, ASSIGN, EQUAL_RIGHT_CHEVRON ,EQUAL_LEFT_CHEVRON, RIGHT_CHEVRON, LEFT_CHEVRON ,NOTEQUAL, EQUAL, OR, AND, SUB, PLUS, MUL, DIV, MOD};
   //if the token is specialized identifier do not append
   public static String [] TOKENS =  {COLON, QUESTION_MARK, IF, ELSE, ASSIGN, EQUAL_RIGHT_CHEVRON ,EQUAL_LEFT_CHEVRON, RIGHT_CHEVRON, LEFT_CHEVRON ,NOTEQUAL, EQUAL, OR, AND, NOT, PLUS, MUL, DIV, MOD, COMPLEMENT, SUB, DOUBLE_SUB, TYPE, CONSTANT, INTEGER, VOID, RETURN, 
      LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, IDENTIFIER};
   public static char [] IGNORE_TOKENS = {WHITESPACE, TAB, NEW_LINE};

}
