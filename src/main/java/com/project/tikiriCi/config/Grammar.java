package com.project.tikiriCi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.NonTerminal;
import com.project.tikiriCi.parser.Terminal;

public class Grammar {
    public static Terminal INT = new Terminal(ASTNodeType.INTEGER,  TokenType.CONSTANT, true);

    public static Terminal IDENTIFIER = new Terminal("identifier", TokenType.IDENTIFIER, true);

    public static Terminal SEMICOLON = new Terminal("semicolon", TokenType.SEMICOLON, false);

    public static Terminal SUB = new Terminal(ASTNodeType.NEGATE,TokenType.SUB, true);

    public static Terminal COMPLEMENT =  new Terminal(ASTNodeType.COMPLEMENT, TokenType.COMPLEMENT, true);

    public static Terminal TYPE = new Terminal("type", TokenType.TYPE, true);

    public static Terminal NOT =  new Terminal(ASTNodeType.NOT, TokenType.NOT, true);

    public static Terminal PLUS = new Terminal(ASTNodeType.PLUS, TokenType.PLUS, true);

    public static Terminal MUL = new Terminal(ASTNodeType.MUL, TokenType.MUL, true);

    public static Terminal DIV = new Terminal(ASTNodeType.DIV, TokenType.DIV, true);

    public static Terminal MOD = new Terminal(ASTNodeType.MOD, TokenType.MOD, true);

    public static Terminal AND = new Terminal(ASTNodeType.AND, TokenType.AND, true);

    public static Terminal OR = new Terminal(ASTNodeType.OR, TokenType.OR, true);

    public static Terminal EQUAL = new Terminal(ASTNodeType.EQUAL, TokenType.EQUAL, true);

    public static Terminal NOTEQUAL = new Terminal(ASTNodeType.NOTEQUAL, TokenType.NOTEQUAL, true);

    public static Terminal LESSTHAN = new Terminal(ASTNodeType.LESSTHAN, TokenType.LEFT_CHEVRON, true);

    public static Terminal LESSOREQUAL = new Terminal(ASTNodeType.LESSOREQUAL, TokenType.EQUAL_LEFT_CHEVRON, true);

    public static Terminal GREATERTHAN = new Terminal(ASTNodeType.GREATERTHAN, TokenType.RIGHT_CHEVRON, true);

    public static Terminal GREATEROREQUAL = new Terminal(ASTNodeType.GREATEROREQUAL, TokenType.EQUAL_RIGHT_CHEVRON, true);
    
    public static Terminal ASSIGN = new Terminal(ASTNodeType.ASSIGN, TokenType.ASSIGN, true);

    public static Terminal QUESTION_MARK = new Terminal(ASTNodeType.QUESTION_MARK, TokenType.QUESTION_MARK, true);

    public static Terminal COLON = new Terminal(ASTNodeType.COLON, TokenType.COLON, true);

    public static Terminal IF = new Terminal("if", TokenType.IF, false);

    public static Terminal ELSE = new Terminal("else", TokenType.ELSE, false);


    public static NonTerminal BINOP = new NonTerminal(ASTNodeType.BINOP, Arrays.asList(
        new Derivation(PLUS),
        new Derivation(MUL),
        new Derivation(SUB),
        new Derivation(DIV),
        new Derivation(MOD),
        new Derivation(OR),
        new Derivation(EQUAL),
        new Derivation(NOTEQUAL),
        new Derivation(LESSTHAN),
        new Derivation(LESSOREQUAL),
        new Derivation(GREATERTHAN),
        new Derivation(GREATEROREQUAL)
    ), TokenType.NULL);

   public static NonTerminal UNOP = new NonTerminal(ASTNodeType.UNOP, Arrays.asList(
        new Derivation(SUB),
        new Derivation(COMPLEMENT),
        new Derivation(NOT)
   ), TokenType.NULL);

    
    public static NonTerminal FACTOR = createFactor();
    public static NonTerminal EXP = createExpression();
    public static NonTerminal STATEMENT = createStatement();
    
    public static NonTerminal ELSE_EXP = new NonTerminal(ASTNodeType.ELSE_EXPRESSION, Arrays.asList(
        new Derivation(
            ELSE,
            STATEMENT
        ),
        //no else part
        new Derivation(

        )
    ), TokenType.NULL);

    
    public static NonTerminal DECLARATION = new NonTerminal(ASTNodeType.DECLARATION, Arrays.asList(
        new Derivation(
            new Terminal("int", TokenType.TYPE, true),
            new Terminal(ASTNodeType.VAR, TokenType.IDENTIFIER, true),
            ASSIGN,
            EXP,
            new Terminal("semicolon", TokenType.SEMICOLON, false)
        ),
        new Derivation(
            new Terminal("int", TokenType.TYPE, false),
            IDENTIFIER,
            new Terminal("semicolon", TokenType.SEMICOLON, false)
        )
    ), TokenType.NULL);

    public static NonTerminal BLOCKITEM = createBlockList();

    public static NonTerminal FUNCTION = new NonTerminal(ASTNodeType.FUNCTION, Arrays.asList(
        new Derivation(
            new Terminal(ASTNodeType.TYPE, TokenType.TYPE, true), //not sure
            IDENTIFIER,
            new Terminal("(", TokenType.LEFT_PARAN, false),
            new Terminal("void", TokenType.VOID, false),
            new Terminal(")", TokenType.RIGHT_PARAN, false),
            new Terminal("{", TokenType.LEFT_BRACE, false),
            BLOCKITEM,
            new Terminal("}", TokenType.RIGHT_BRACE, false)
        )
    ), TokenType.NULL);
   

    public static NonTerminal PROGRAM = new NonTerminal(ASTNodeType.PROGRAM, Arrays.asList(
        new Derivation(FUNCTION)
    ), TokenType.NULL);

    public static NonTerminal createBlockList() {
        NonTerminal blockList = new NonTerminal(ASTNodeType.BLOCKITEM, new ArrayList<Derivation>(),
         TokenType.NULL);
         blockList.getDerivation().addAll( Arrays.asList(
            new Derivation(STATEMENT, BLOCKITEM),
            new Derivation(DECLARATION,BLOCKITEM),
            new Derivation()
            )
         );
        return blockList;
    }

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
                ),
                new Derivation(
                    expression,
                    QUESTION_MARK,
                    expression,
                    COLON,
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
                ),
                new Derivation(
                    IDENTIFIER
                )
            )
        );

        return expression;
    }

    public static GrammerElement getBinOpGrammarElement(String tokenType) throws CompilerException{
        Terminal[] terminals = {PLUS, SUB, MUL, DIV, MOD, LESSOREQUAL, LESSTHAN, 
            GREATEROREQUAL, GREATERTHAN, EQUAL, NOTEQUAL, OR, AND};
        for (Terminal terminal : terminals) {
           if(terminal.getTokenType() == tokenType) {
                return terminal.clone();
           }
        }
        throw new CompilerException("Error: Binary operator "+tokenType + " does not exist");
    }

    public static GrammerElement getUnOpGrammarElement(String tokenType) throws CompilerException{
        Terminal[] terminals = {SUB, NOT, COMPLEMENT};
        for (Terminal terminal : terminals) {
           if(terminal.getTokenType() == tokenType) {
                return terminal.clone();
           }
        }
        throw new CompilerException("Error: Binary operator "+tokenType + " does not exist");
    }

    public static NonTerminal createStatement() {
        NonTerminal statement = new NonTerminal(ASTNodeType.STATEMENT, new ArrayList<Derivation>(), TokenType.NULL);
        statement.getDerivation().addAll(Arrays.asList(
            new Derivation(
            new Terminal(ASTNodeType.RETURN, TokenType.RETURN, true),
            EXP,
            new Terminal("semicolon", TokenType.SEMICOLON, false)
        ),
        new Derivation(
            EXP,
            SEMICOLON
        ),
        new Derivation(
            SEMICOLON
        ),
        new Derivation(
            IF,
            new Terminal("(", TokenType.LEFT_PARAN, false),
            EXP,
            new Terminal(")", TokenType.RIGHT_PARAN, false),
            STATEMENT
        )
        ));
        return statement;
    }
    
    

}
