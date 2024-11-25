package com.project.tikiriCi.config;

import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
   public static Terminal INT = new Terminal(TreeNodeType.INTEGER,  TokenType.CONSTANT, true);

   public static Terminal IDENTIFIER = new Terminal("identifier", TokenType.IDENTIFIER, true);

   public static NonTerminal EXP = new NonTerminal(TreeNodeType.EXPRESSION, Arrays.asList(
      new Derivation(INT)
   ), TokenType.NULL);
   public static NonTerminal STATEMENT = new NonTerminal(TreeNodeType.STATEMENT, Arrays.asList(
      new Derivation(
         new Terminal(TreeNodeType.RETURN, TokenType.RETURN, true),
         EXP,
         new Terminal("semicolon", TokenType.SEMICOLON, false)
      )
   ), TokenType.NULL);
   
   public static NonTerminal FUNCTION = new NonTerminal(TreeNodeType.FUNCTION, Arrays.asList(
      new Derivation(
         new Terminal("int", TokenType.TYPE, false), //not sure
         IDENTIFIER,
         new Terminal("(", TokenType.LEFT_PARAN, false),
         new Terminal("void", TokenType.VOID, false),
         new Terminal(")", TokenType.RIGHT_PARAN, false),
         new Terminal("{", TokenType.LEFT_BRACE, false),
         STATEMENT,
         new Terminal("}", TokenType.RIGHT_BRACE, false)
      )
   ), TokenType.NULL); 

   public static NonTerminal START = new NonTerminal(TreeNodeType.PROGRAM, Arrays.asList(
      new Derivation(FUNCTION)  
   ), TokenType.NULL);






}
