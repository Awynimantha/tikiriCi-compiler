package com.project.tikiriCi.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.utility.LocalUtil;

public class TokenParser {
    private AST ast;
    private List<Token> tokens;
    private Token nextToken;

    public TokenParser(List<Token> tokens) {
        this.ast = new AST();
        this.tokens = tokens;
        this.nextToken = tokens.get(0);
    }

    public void parse() throws CompilerException {
        parseNode(ast.getASTRoot());
    }

    public AST getAST() {
        return this.ast;
    }

    
    public void parseNode(ASTNode astNode) throws CompilerException{
        String nodeType = astNode.getGrammerElement().getName();
        if(nodeType == ASTNodeType.EXPRESSION) {
            ASTNode expNode= parseExpression(0);
            List<ASTNode> nodes = expNode.getChildren();
            for (ASTNode childNode : nodes) {
                astNode.addChild(childNode);
            }
        } else if(nodeType == ASTNodeType.FUNCTION) {
            parseFunctionDerivation(astNode);
        } else if(nodeType == ASTNodeType.BLOCK) {
            parseBlockDerivation(astNode);
        } else {
            parseElement(astNode);
        }
    }

    //default parser
    public void parseElement(ASTNode node) throws CompilerException{
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        String nodeType = node.getGrammerElement().getName();
        Derivation pickedDerivation = new Derivation();
        if(derivations.size() == 1) {
            pickedDerivation = derivations.get(0);
        } else if(nodeType == ASTNodeType.FACTOR) {
            pickedDerivation = pickFactorDerivation(node);
        } else if(nodeType == ASTNodeType.STATEMENT) {
            pickedDerivation = pickStatementDerivation(node);
        } else {
            //default
            pickedDerivation = TokenParserUtils.lookAHeadDerivationPicker(derivations, nextToken);
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

    private ASTNode parserFactor() throws CompilerException{
        if(nextToken.getTokenType() == TokenType.CONSTANT) {
            GrammerElement  grammerElement = Grammar.INT.clone();
            grammerElement.setValue(nextToken.getTokenValue().getStringValue());
            this.nextToken = consumeTerminal(tokens, grammerElement);
            ASTNode factorNode = new ASTNode(Grammar.FACTOR);
            factorNode.addChild(new ASTNode(grammerElement));
            return factorNode;
        } else if(nextToken.getTokenType() == TokenType.IDENTIFIER) {
            GrammerElement  grammerElement = Grammar.IDENTIFIER.clone();
            grammerElement.setValue(nextToken.getTokenValue().getStringValue());
            this.nextToken = consumeTerminal(tokens, grammerElement);
            ASTNode factorNode = new ASTNode(Grammar.FACTOR);
            factorNode.addChild(new ASTNode(grammerElement, ASTNodeType.VAR));
            return factorNode;
        } else if(nextToken.getTokenType() == TokenType.SUB || nextToken.getTokenType() == TokenType.COMPLEMENT){
            ASTNode operator = parseUnOp();
            ASTNode inner_exp = parserFactor();
            ASTNode factorNode  = new ASTNode(Grammar.FACTOR);
            factorNode.addChild(operator);
            factorNode.addChild(inner_exp);
            return factorNode;
        } else if(nextToken.getTokenType() == TokenType.LEFT_PARAN){
            consumeTerminal(TokenType.LEFT_PARAN);
            ASTNode factorNode = new ASTNode(Grammar.FACTOR);
            ASTNode expression = parseExpression(0);
            factorNode.addChild(expression);
            consumeTerminal(TokenType.RIGHT_PARAN);
            return factorNode;
        } else {
            throw new CompilerException("Error: Unexpected token '"+ nextToken.getTokenValue().getStringValue() + "'");
        }
    }

    private ASTNode parseBinOp() throws CompilerException{
        GrammerElement binOpGrammerElement = Grammar.BINOP;
        GrammerElement operandGrammerElement = new GrammerElement();
        ASTNode binOPNode = new ASTNode(binOpGrammerElement);
        ASTNode operandNode = new ASTNode();
        operandGrammerElement = Grammar.getBinOpGrammarElement(nextToken.getTokenType());
        operandNode.setGrammerElement(operandGrammerElement);
        operandNode.setValue(nextToken.getTokenValue().getStringValue());
        this.nextToken = consumeTerminal(tokens, operandGrammerElement);
        binOPNode.addChild(operandNode);
        return binOPNode;      
    }

    private ASTNode parseUnOp() {
        GrammerElement unOpGrammerElement = Grammar.UNOP;
        GrammerElement operandGrammerElement = new GrammerElement();
        ASTNode unOPNode = new ASTNode(unOpGrammerElement);
        ASTNode operandNode = new ASTNode();
        if(nextToken.getTokenType() == TokenType.COMPLEMENT) {
            operandGrammerElement = Grammar.COMPLEMENT;
        } else if(nextToken.getTokenType() == TokenType.SUB) {
            operandGrammerElement = Grammar.SUB;
        }
        operandNode.setGrammerElement(operandGrammerElement);
        operandNode.setValue(nextToken.getTokenValue().getStringValue());
        this.nextToken = consumeTerminal(tokens, operandGrammerElement);
        unOPNode.addChild(operandNode);
        return unOPNode;        
    }

    private Derivation pickFactorDerivation(ASTNode astNode) {
        Derivation returnDerivation = new Derivation();
        List<Derivation> derivations = Grammar.FACTOR.getDerivation();
        if(nextToken.getTokenType() == TokenType.CONSTANT) {
            returnDerivation = derivations.get(0);
        } else if(nextToken.getTokenType() == TokenType.COMPLEMENT || 
            nextToken.getTokenType() == TokenType.SUB) {
            returnDerivation = derivations.get(1);
        } else if(nextToken.getTokenType() == TokenType.LEFT_PARAN) {
            returnDerivation = derivations.get(2);
        }
        return returnDerivation;
    }

    private Derivation pickStatementDerivation(ASTNode astNode) {
        Derivation returnDerivation = new Derivation();
        List<Derivation> derivations = Grammar.STATEMENT.getDerivation();
        if(nextToken.getTokenType() == TokenType.IF) {
            returnDerivation = derivations.get(3);
        } else if(nextToken.getTokenType() == TokenType.RETURN) {
            returnDerivation = derivations.get(0);
        } else if(nextToken.getTokenType() == TokenType.SEMICOLON) {
            returnDerivation = derivations.get(2);
        } else if(nextToken.getTokenType() == TokenType.LEFT_BRACE) {
            returnDerivation = derivations.get(4);
        } else {
            returnDerivation = derivations.get(1);
        }
        return returnDerivation;
    }

    private void parseFunctionDerivation(ASTNode astNode) throws CompilerException{
        Derivation derivation = Grammar.FUNCTION.getDerivation().get(0);
        List<GrammerElement> grammerElements = derivation.getGrammarElements();
        for (GrammerElement grammerElement : grammerElements) {
            if(grammerElement.isASTNode()) {
                astNode.builChild(grammerElement, nextToken);
            }
            nextToken = consumeTerminal(tokens, grammerElement);
            if(grammerElement.getTokenType() == TokenType.RIGHT_PARAN) {
                break;
            }
        }
        parseBlockDerivation(astNode);
    } 

    private void parseBlockDerivation(ASTNode astNode) throws CompilerException{ 
        ASTNode blockNode = new ASTNode(Grammar.BLOCK.clone());
        astNode.addChild(blockNode);
        consumeTerminal(TokenType.LEFT_BRACE);
        while(nextToken.getTokenType() != TokenType.RIGHT_BRACE) {
            parseBlockItemDerivation(blockNode);
        }
        consumeTerminal(TokenType.RIGHT_BRACE); 
    }

    private void parseBlockItemDerivation(ASTNode astNode) throws CompilerException{
        GrammerElement grammerElement = Grammar.BLOCKITEM.clone();
        ASTNode blockListNode = new ASTNode(grammerElement);
        astNode.addChild(blockListNode);
        if(nextToken.getTokenType() == TokenType.INTEGER) {
            ASTNode declarationNode = LocalUtil.addChildASTNode(blockListNode, Grammar.DECLARATION);
            parseDeclaration(declarationNode);
        } else {            
            ASTNode statementNode = LocalUtil.addChildASTNode(blockListNode, Grammar.STATEMENT);
            parseNode(statementNode);
        }
    }

    private void parseDeclaration(ASTNode astNode) throws CompilerException{
        Derivation derivation = Grammar.DECLARATION.getDerivation().get(0);
        for (GrammerElement grammerElement : derivation.getGrammarElements()) {
            GrammerElement newGrammarEl = grammerElement.clone();
            if(grammerElement.getName() == ASTNodeType.EXPRESSION) {
                ASTNode newNode = parseExpression(0);
                astNode.addChild(newNode);
                continue;
            }
            if(grammerElement.isASTNode()) {
                newGrammarEl.setValue(nextToken.getTokenValue().getStringValue());
                //condition not needed
                if(grammerElement.getTokenType() != TokenType.IDENTIFIER) {
                    astNode.addChild(new ASTNode(newGrammarEl));
                } else {
                    ASTNode node = new ASTNode(newGrammarEl);
                    node.setASTNodeType(ASTNodeType.VAR);
                    astNode.addChild(node);
                }
            }
            nextToken = consumeTerminal(tokens, grammerElement);
            if(grammerElement.getTokenType() == TokenType.IDENTIFIER && nextToken.getTokenType() == TokenType.SEMICOLON) {
                newGrammarEl = Grammar.SEMICOLON;
                nextToken = consumeTerminal(tokens, newGrammarEl);
                break;
            }
        }
    }

    private ASTNode parseExpression(int minPrec) throws CompilerException{ 
        ASTNode left = parserFactor();
        ASTNode expLeft = new ASTNode(Grammar.EXP);
        expLeft.addChildren(left.getChildren());
        while(LocalUtil.isBinaryOp(nextToken) && nextToken.getPrecedence() >= minPrec) {
            int prec = nextToken.getPrecedence();
            if(nextToken.getTokenType() == TokenType.ASSIGN){
                consumeTerminal(TokenType.ASSIGN);
                ASTNode rightExp = parseExpression(prec);
                GrammerElement grammerElement = new GrammerElement(ASTNodeType.ASSIGNMENT, null,
                     false, TokenType.NULL, true);
                //wrap assigment in expression node
                ASTNode expressionNode = new ASTNode(Grammar.EXP);
                ASTNode tmpNode = new ASTNode(grammerElement,expLeft, rightExp);
                expressionNode.addChild(tmpNode);
                expLeft = expressionNode;               
                
            } else if(nextToken.getTokenType() == TokenType.QUESTION_MARK) {
                consumeTerminal(TokenType.QUESTION_MARK);
                ASTNode middleExp = parseExpression(minPrec);
                consumeTerminal(TokenType.COLON);
                ASTNode rightExp = parseExpression(nextToken.getPrecedence());
                GrammerElement grammerElement = new GrammerElement(ASTNodeType.CONDITIONAL, null,
                     false, TokenType.NULL, true);
                ASTNode expressionNode = new ASTNode(Grammar.EXP);
                ASTNode conditionNode = new ASTNode(grammerElement, expLeft, middleExp, rightExp);
                expressionNode.addChild(conditionNode);
                expLeft = expressionNode;
            } else {
                ASTNode operator = parseBinOp();
                ASTNode rightExp = parseExpression(prec+1);
                expLeft = new ASTNode(Grammar.EXP, expLeft, operator, rightExp);
            }    
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

    private void consumeTerminal(String tokeType) {
        int firstIndex = 0;
        if(this.tokens.size()<1){
            System.out.println("Error: Expected a"+ tokeType+" nothing found");
        }
        Token firstToken = this.tokens.get(firstIndex);
        if(tokeType == firstToken.getTokenType()){
            tokens.remove(firstIndex);
            if(tokens.size()>0){
                firstToken = tokens.get(firstIndex);
            }
        } else{
            System.out.println("Error: Expected a \""+tokeType+ "\", \""
                + firstToken.getTokenType() + "\" found");
        }
        this.nextToken = firstToken;
    }
}
