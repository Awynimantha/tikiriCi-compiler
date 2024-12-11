package com.project.tikiriCi.parser;

import java.io.EOFException;
import java.util.List;

import javax.swing.text.AsyncBoxView.ChildLocator;

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
            // GrammerElement expGrammerElement = Grammar.EXP;
            // ASTNode expNode = new ASTNode(expGrammerElement);
            // astNode.addChild(expNode);
            ASTNode expNode= parseExpression(0);
            List<ASTNode> nodes = expNode.getChildren();
            for (ASTNode childNode : nodes) {
                astNode.addChild(childNode);
            }
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
        } else {
            //choose the best derivation
            for (Derivation derivation : derivations) {
                GrammerElement firstGrammerElement = derivation.peekDerivation();
                if(firstGrammerElement.getIsTerminal()){
                    if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
                        pickedDerivation = derivation;
                        break;
                    }
                } else {
                    List<Derivation> firstGrammerElementDerivation = firstGrammerElement.getDerivation();
                    for (Derivation deriv : firstGrammerElementDerivation) {
                        firstGrammerElement = deriv.peekDerivation();
                        if(firstGrammerElement.getIsTerminal()){
                            if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
                                pickedDerivation = derivation;
                                break;
                            }
                        } else{
                            List<Derivation> fgrammerChildren = firstGrammerElement.getDerivation();
                            for (Derivation derivationl : fgrammerChildren) {
                                GrammerElement firstElement = derivationl.peekDerivation();
                                if(nextToken.getTokenType() == firstElement.getTokenType()){
                                    pickedDerivation = derivation;
                                    break;      
                                }
                            }
                        }
                    }
                }
            }
        }
        
        for (GrammerElement grammerElement : pickedDerivation.getGrammarElements()) {
            ASTNode astNode = new ASTNode(grammerElement);
            if(grammerElement.getIsTerminal()){
                if(grammerElement.isASTNode()){
                    astNode.getGrammerElement().setValue(
                        LocalUtil.peekTokenList(this.tokens).getTokenValue().getStringValue());
                    node.addChild(astNode);
                }
                nextToken = consumeTerminal(this.tokens, grammerElement);                   
            } else {
                node.addChild(astNode);
                parseNode(astNode);
            }
        }  
    }

    private ASTNode parserFactor() {
        if(nextToken.getTokenType() == TokenType.CONSTANT) {
            GrammerElement  grammerElement = Grammar.INT.clone();
            grammerElement.setValue(nextToken.getTokenValue().getStringValue());
            this.nextToken = consumeTerminal(tokens, grammerElement);
            ASTNode factorNode = new ASTNode(Grammar.FACTOR);
            factorNode.addChild(new ASTNode(grammerElement));
            return factorNode;
        } else if(nextToken.getTokenType() == TokenType.HYPHONE || nextToken.getTokenType() == TokenType.TILDE){
            ASTNode operator = parseBinOp();
            ASTNode inner_exp = parserFactor();
            ASTNode factorNode  = new ASTNode(Grammar.FACTOR);
            factorNode.addChild(operator);
            factorNode.addChild(inner_exp);
        } else if(nextToken.getTokenType() == TokenType.LEFT_PARAN){
            this.nextToken = consumeTerminal(tokens, TokenType.LEFT_PARAN);
            ASTNode factorNode = new ASTNode(Grammar.FACTOR);
            ASTNode expression = parseExpression(0);
            factorNode.addChild(expression);
            this.nextToken = consumeTerminal(tokens, TokenType.RIGHT_PARAN);
            return factorNode;
        } else {
            throw new IllegalArgumentException("Malformed factor: Unexpected token '"  + "'");
        }
        return null;
    }

    private ASTNode parseBinOp() {
        GrammerElement binOpGrammerElement = Grammar.BINOP;
        GrammerElement operandGrammerElement = new GrammerElement();
        ASTNode binOPNode = new ASTNode(binOpGrammerElement);
        ASTNode operandNode = new ASTNode();
        if(nextToken.getTokenType() == TokenType.PLUS) {
            operandGrammerElement = Grammar.PLUS;
        } else if(nextToken.getTokenType() == TokenType.MUL) {
            operandGrammerElement = Grammar.MUL;
        }
        operandNode.setGrammerElement(operandGrammerElement);
        operandNode.setValue(nextToken.getTokenValue().getStringValue());
        this.nextToken = consumeTerminal(tokens, operandGrammerElement);
        binOPNode.addChild(operandNode);
        return binOPNode;
        
    }

    // private ASTNode parseUnaryNode() {

    // }

    private Derivation pickFactorDerivation(ASTNode astNode) {
        Derivation returnDerivation = new Derivation();
        List<Derivation> derivations = Grammar.FACTOR.getDerivation();
        if(nextToken.getTokenType() == TokenType.CONSTANT) {
            returnDerivation = derivations.get(0);
        } else if(nextToken.getTokenType() == TokenType.TILDE || nextToken.getTokenType() == TokenType.HYPHONE) {
            returnDerivation = derivations.get(1);
        } else if(nextToken.getTokenType() == TokenType.LEFT_PARAN) {
            returnDerivation = derivations.get(2);
        }
        return returnDerivation;
    }

    private ASTNode parseExpression(int minPrec) { 
        ASTNode left = parserFactor();
        ASTNode expLeft = new ASTNode(Grammar.EXP);
        expLeft.addChildren(left.getChildren());;
        while(LocalUtil.isBinaryOp(nextToken) && nextToken.getPrecedence() >= minPrec) {
            int prec = nextToken.getPrecedence();
            ASTNode operator = parseBinOp();
            ASTNode rightExp = parseExpression(prec+1);
            expLeft = new ASTNode(Grammar.EXP, expLeft, operator, rightExp);
            
        }      
        return expLeft;
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
            System.out.println("Error: Expected a \""+grammerElement.getTokenType()+ "\", \""
                + firstToken.getTokenType() + "\" found");
        }
        return firstToken;
    }

    private Token consumeTerminal(List<Token> tokens, String tokeType) {
        int firstIndex = 0;
        if(tokens.size()<1){
            System.out.println("Error: Expected a"+ tokeType+" nothing found");
        }
        Token firstToken = tokens.get(firstIndex);
        if(tokeType == firstToken.getTokenType()){
            tokens.remove(firstIndex);
            if(tokens.size()>0){
                firstToken = tokens.get(firstIndex);
            }
        } else{
            System.out.println("Error: Expected a \""+tokeType+ "\", \""
                + firstToken.getTokenType() + "\" found");
        }
        return firstToken;
    }
}
