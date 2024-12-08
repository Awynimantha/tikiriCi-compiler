package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.utility.LocalUtil;

public class Parser {
    private AST ast;
    private List<Token> tokens;
    private Token nextToken;

    public Parser(List<Token> tokens) {
        this.ast = new AST();
        this.tokens = tokens;
        this.nextToken = tokens.get(0);
    }

    public void parse() {
        parseNode(ast.getASTRoot());
    }

    public AST getAST() {
        return this.ast;
    }

    public void parseNode(ASTNode astNode) {
        String nodeType = astNode.getGrammerElement().getName();
        if(nodeType == ASTNodeType.EXPRESSION) {

        } else {
            parseElement(astNode);
        }

    }

    public void parseElement(ASTNode node) {
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        String nodeType = node.getGrammerElement().getName();
        Derivation pickedDerivation = new Derivation();
        if(derivations.size() == 1) {
            pickedDerivation = derivations.get(0);
        } else if(nodeType == ASTNodeType.FACTOR){
            pickedDerivation = pickFactorDerivation(node);
        }
        
        for (GrammerElement grammerElement : pickedDerivation.getGrammarElements()) {
            ASTNode astNode = new ASTNode(grammerElement);
            if(grammerElement.getIsTerminal()){
                if(grammerElement.isASTNode()){
                    astNode.getGrammerElement().setValue(
                        LocalUtil.peekTokenList(tokens).getTokenValue().getStringValue());
                    node.addChild(astNode);
                }
                nextToken = consumeTerminal(this.tokens, grammerElement);                   
            } else {
                node.addChild(astNode);
                parseElement(astNode);
            }
        }  
    }

    private void parserFactor(ASTNode astNode) {
        Derivation pickedDerivation = pickFactorDerivation(astNode);
        for (GrammerElement grammerElement : pickedDerivation.getGrammarElements()) {
            ASTNode newNode = new ASTNode(grammerElement);
            if(grammerElement.getIsTerminal()){
                if(grammerElement.isASTNode()){
                    astNode.getGrammerElement().setValue(
                        LocalUtil.peekTokenList(tokens).getTokenValue().getStringValue());
                    astNode.addChild(newNode);
                }
                nextToken = consumeTerminal(this.tokens, grammerElement);                   
            } else {
                astNode.addChild(newNode);
                parseElement(astNode);
            }
        }  
        
    }

    private ASTNode parseBinOp() {
        List<Derivation> derivations = Grammar.BINOP.getDerivation();
        ASTNode returnNode = new ASTNode();
        for (Derivation derivation : derivations) {
            GrammerElement grammerElement = derivation.getGrammarElements().get(0);
            if(grammerElement.getTokenType() == nextToken.getTokenType()) {
                grammerElement.setName(ASTNodeType.BINOP);
                returnNode = new ASTNode(grammerElement);
                consumeTerminal(tokens, grammerElement);
            }         
        }
        return returnNode;
    }

    private Derivation pickFactorDerivation(ASTNode astNode) {
        Derivation returnDerivation = new Derivation();
        List<Derivation> derivations = astNode.getGrammerElement().getDerivation();
        if(nextToken.getTokenType() == TokenType.CONSTANT) {
            returnDerivation = derivations.get(0);
        } else if(nextToken.getTokenType() == TokenType.TILDE || nextToken.getTokenType() == TokenType.HYPHONE) {
            returnDerivation = derivations.get(1);
        } else if(nextToken.getTokenType() == TokenType.LEFT_PARAN) {
            returnDerivation = derivations.get(2);
        }
        return returnDerivation;
    }

    private void parseExpression(ASTNode astNode) {
        ASTNode leftNode = new ASTNode();
        parserFactor(leftNode);
        while(this.nextToken.getTokenType() == TokenType.PLUS || this.nextToken.getTokenType() == TokenType.HYPHONE) {
            ASTNode operator = parseBinOp();
            ASTNode rightNode = new ASTNode();
            parserFactor(rightNode);
        }
        

    }

  


    private Token consumeTerminal(List<Token> tokens, GrammerElement grammerElement) {
        int firstIndex = 0;
        if(tokens.size()<1){
            System.out.println("Error: Expected a"+ grammerElement.getTokenType()+" nothing found");
        }
        Token firstToken = tokens.get(firstIndex);
        if(grammerElement.getTokenType() == firstToken.getTokenType()){
            tokens.remove(firstIndex);
            if(tokens.size()>0){
                firstToken = tokens.get(firstIndex);
            }

        } else{
             System.out.println("Error: Expected a \""+grammerElement.getTokenType()+ "\", \""+firstToken.getTokenType()+ "\" found");
        }
        return firstToken;
    }
}
