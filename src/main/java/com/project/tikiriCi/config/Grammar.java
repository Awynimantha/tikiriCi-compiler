package com.project.tikiriCi.config;

import java.util.ArrayList;
import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
   public static Terminal INT = new Terminal(ASTNodeType.INTEGER,  TokenType.CONSTANT, true);

   public static Terminal IDENTIFIER = new Terminal("identifier", TokenType.IDENTIFIER, true);

   public static Terminal HYPHON = new Terminal("hyphone",TokenType.HYPHONE, true);

   public static Terminal TILDE =  new Terminal("tilde", TokenType.TILDE, true);

   public static NonTerminal UNOP = new NonTerminal(ASTNodeType.UNOP, Arrays.asList(
      new Derivation(HYPHON),
      new Derivation(TILDE)
   ), TokenType.NULL);

   public static NonTerminal EXP = createExpression();

   public static NonTerminal STATEMENT = new NonTerminal(ASTNodeType.STATEMENT, Arrays.asList(
      new Derivation(
         new Terminal("(", TokenType.LEFT_PARAN, true)
      ),
      new Derivation(
         new Terminal(ASTNodeType.RETURN, TokenType.RETURN, true),
         EXP,
         new Terminal("semicolon", TokenType.SEMICOLON, false)
      )
   ), TokenType.NULL);
   
  // public static NonTerminal STATEMENTS = createStatement();

   public static NonTerminal FUNCTION = new NonTerminal(ASTNodeType.FUNCTION, Arrays.asList(
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
   

   public static NonTerminal PROGRAM = new NonTerminal(ASTNodeType.PROGRAM, Arrays.asList(
      new Derivation(FUNCTION)
   ), TokenType.NULL);

   // private static NonTerminal createStatement() {
   //    NonTerminal statements = new NonTerminal("statements", new ArrayList<Derivation>(), TokenType.NULL);
   //    statements.getDerivation().addAll(Arrays.asList(
   //       new Derivation(
   //          STATEMENT,
   //          statements

   //       )
   //    ));
   //    return statements;
   // }

   private static NonTerminal createExpression() {
      NonTerminal expression = new NonTerminal("expression", new ArrayList<Derivation>(), TokenType.NULL);
      expression.getDerivation().addAll(Arrays.asList(
         new Derivation(
            INT
         ),
         new Derivation(
            UNOP,
            expression

         ),
         new Derivation(
            new Terminal("(", TokenType.LEFT_PARAN, false),
            expression,
            new Terminal(")", TokenType.RIGHT_PARAN, false)
         )
      ));
      return expression;
   }
   




}
