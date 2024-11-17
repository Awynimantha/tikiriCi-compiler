package com.project.tikiriCi.config;

import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
   public static Terminal INT = new Terminal("integer", Arrays.asList(
      new Derivation()
   ),  TokenType.INTEGER);

   public static Terminal IDENTIFIER = new Terminal("identifier", Arrays.asList(
      new Derivation()
   ), TokenType.IDENTIFIER);

   public static NonTerminal EXP = new NonTerminal("expression", Arrays.asList(
      new Derivation()
   ), TokenType.NULL);
   public static NonTerminal STATEMENT = new NonTerminal("statement", Arrays.asList(
      new Derivation()
   ), TokenType.NULL);
   
   public static NonTerminal FUNCTION = new NonTerminal("function", Arrays.asList(
      new Derivation()
   ), TokenType.NULL); 

   public static NonTerminal START = new NonTerminal("start", Arrays.asList(
      new Derivation(FUNCTION)  
   ), TokenType.NULL);






}
