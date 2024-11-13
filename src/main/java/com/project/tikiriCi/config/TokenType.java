package com.project.tikiriCi.config;

public class TokenType {
   public static String  IDENTIFIER = "[a-zA-Z_]\\w*\\b";
   public static String CONSTANT = "[0-9]+\\b";
   public static String INTEGER = "int\\b";
   public static String VOID = "void\\b";
   public static String RETURN = "return\\b";
   public static String LEFT_PARAN = "\\(";
   public static String RIGHT_PARAN = "\\)";
   public static String LEFT_BRACE = "{";
   public static String RIGHT_BRACE = "}"; 
   public static String SEMICOLON = ";";
   public static String NULL = "null";
   public static String WHITESPACE = " ";
   public static String [] TOKENS =  { CONSTANT, INTEGER, VOID, RETURN, 
      LEFT_PARAN, RIGHT_PARAN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON, IDENTIFIER};
}
