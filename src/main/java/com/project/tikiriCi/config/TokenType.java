package com.project.tikiriCi.config;

public class TokenType {
   public static String SYMBOL = "[^\\w\\s]";
   public static String TOKEN = "[\\w]+\\b";
   public static String IDENTIFIER = "\\b[a-zA-Z_]\\w*\\b";
   public static String CONSTANT = "[0-9]+\\b";
   public static String INTEGER = "int\\b";
   public static String VOID = "void\\b";
   public static String RETURN = "\\breturn\\b";
   public static String LEFT_PARAN = "\\(";
   public static String RIGHT_PARAN = "\\)";
   public static String LEFT_BRACE = "\\{";
   public static String RIGHT_BRACE = "\\}"; 
   public static String SEMICOLON = ";";
   public static String TYPE = "int\\b";
   public static String TILDE = "\\~";
   public static String HYPHONE = "\\-";
   public static String PLUS = "\\+";
   public static String MUL = "\\*";
   public static String DIV = "\\/";
   public static String MOD = "\\%";
   public static String DOUBLE_HYPHONE = "\\--";
   public static String NULL = "null";
   public static char WHITESPACE =  ' ';
   public static char TAB = '\t';
   public static char NEW_LINE = '\n';
   public static String [] SYMBOLS = {LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE,
   SEMICOLON,TILDE, HYPHONE, PLUS, MUL, DIV, MOD};
   public static String [] TOKENS =  {PLUS, MUL, DIV, MOD, TILDE, HYPHONE, DOUBLE_HYPHONE, TYPE, CONSTANT, INTEGER, VOID, RETURN, 
      LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, IDENTIFIER};
   public static char [] IGNORE_TOKENS = {WHITESPACE, TAB, NEW_LINE};
}
