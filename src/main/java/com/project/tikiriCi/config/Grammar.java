package com.project.tikiriCi.config;

import java.util.ArrayList;
import java.util.Arrays;

import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
   public static Terminal INT = new Terminal(ASTNodeType.INTEGER,  TokenType.CONSTANT, true);

   public static Terminal IDENTIFIER = new Terminal("identifier", TokenType.IDENTIFIER, true);

   public static Terminal HYPHON = new Terminal(ASTNodeType.NEGATE,TokenType.HYPHONE, true);

   public static Terminal TILDE =  new Terminal(ASTNodeType.COMPLEMENT, TokenType.TILDE, true);

   public static Terminal PLUS = new Terminal(ASTNodeType.PLUS, TokenType.PLUS, true);

   public static Terminal MUL = new Terminal(ASTNodeType.MUL, TokenType.MUL, true);

   public static Terminal DIV = new Terminal(ASTNodeType.DIV, TokenType.DIV, true);

   public static Terminal MOD = new Terminal(ASTNodeType.MOD, TokenType.MOD, true);

   public static NonTerminal BINOP = new NonTerminal(ASTNodeType.BINOP, Arrays.asList(
      new Derivation(PLUS),
      new Derivation(MUL),
      new Derivation(DIV),
      new Derivation(MOD)
   ), TokenType.NULL);

   public static NonTerminal UNOP = new NonTerminal(ASTNodeType.UNOP, Arrays.asList(
      new Derivation(HYPHON),
      new Derivation(TILDE)
   ), TokenType.NULL);

   
   public static NonTerminal FACTOR = createFactor();
   public static NonTerminal EXP = createExpression();
   

   public static NonTerminal STATEMENT = new NonTerminal(ASTNodeType.STATEMENT, Arrays.asList(
      new Derivation(
         new Terminal(ASTNodeType.RETURN, TokenType.RETURN, true),
         EXP,
         new Terminal("semicolon", TokenType.SEMICOLON, false)
      )
   ), TokenType.NULL);
   

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

    private static NonTerminal createFactor() {
        NonTerminal factor = new NonTerminal(ASTNodeType.FACTOR, new ArrayList<Derivation>(), TokenType.NULL);
        return factor;
    }

    private static NonTerminal createExpression() {
        NonTerminal expression = new NonTerminal(ASTNodeType.EXPRESSION, new ArrayList<Derivation>(), TokenType.NULL);
        NonTerminal factor = createFactor();

        expression.getDerivation().addAll(
            Arrays.asList(
                new Derivation(
                    factor 
                ),
                new Derivation(
                    expression,
                    BINOP,
                    expression
                )
            )
        );

        FACTOR.getDerivation().addAll(
            Arrays.asList(
                new Derivation(
                    INT
                ),
                new Derivation(
                    UNOP,
                    factor
                ),
                new Derivation(
                    new Terminal("(", TokenType.LEFT_PARAN, true),
                    expression, // Reference the existing expression object
                    new Terminal(")", TokenType.RIGHT_PARAN, true)
                )
            )
        );

        return expression;
    }
    
    

}
