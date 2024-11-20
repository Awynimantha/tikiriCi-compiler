package com.project.tikiriCi.config;

import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
   public static Terminal INT = new Terminal("integer",  TokenType.CONSTANT);

   public static Terminal IDENTIFIER = new Terminal("identifier", TokenType.IDENTIFIER);

   public static NonTerminal EXP = new NonTerminal("expression", Arrays.asList(
      new Derivation(INT)
   ), TokenType.NULL);
   public static NonTerminal STATEMENT = new NonTerminal("statement", Arrays.asList(
      new Derivation(
         new Terminal("return", TokenType.RETURN),
         EXP,
         new Terminal("semicolon", TokenType.SEMICOLON)
      )
   ), TokenType.NULL);
   
   public static NonTerminal FUNCTION = new NonTerminal("function", Arrays.asList(
      new Derivation(
         new Terminal("int", TokenType.TYPE),
         IDENTIFIER,
         new Terminal("(", TokenType.LEFT_PARAN),
         new Terminal("void", TokenType.VOID),
         new Terminal(")", TokenType.RIGHT_PARAN),
         new Terminal("{", TokenType.LEFT_BRACE),
         STATEMENT,
         new Terminal("}", TokenType.RIGHT_BRACE)
      )
   ), TokenType.NULL); 

   public static NonTerminal START = new NonTerminal("start", Arrays.asList(
      new Derivation(FUNCTION)  
   ), TokenType.NULL);






}
