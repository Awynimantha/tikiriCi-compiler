package com.project.tikiriCi.config;

public class TokenType {
   public static String  IDENTIFIER = "^[a-zA-Z_]\\w*\\b(?!\\n)$";
   public static String CONSTANT = "^[0-9]+\\b(?!\\n)$";
   public static String INTEGER = "^int\\b(?!\\n)$";
   public static String VOID = "^void\\b(?!\\n)$";
   public static String RETURN = "^return\\b(?!\\n)$";
   public static String LEFT_PARAN = "^\\((?!\\n)$";
   public static String RIGHT_PARAN = "^\\)(?!\\n)$";
   public static String LEFT_BRACE = "^\\{(?!\\n)$";
   public static String RIGHT_BRACE = "^\\}(?!\\n)$"; 
   public static String SEMICOLON = "^;(?!\\n)$";
   public static String NULL = "null";
   public static char WHITESPACE =  ' ';
   public static String [] TOKENS =  { CONSTANT, INTEGER, VOID, RETURN, 
      LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, IDENTIFIER};
}
