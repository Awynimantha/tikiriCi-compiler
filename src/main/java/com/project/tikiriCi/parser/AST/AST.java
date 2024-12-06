package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.utility.LocalUtil;

public class AST {
    private ASTNode ASTRoot;  
    private List<Token> tokens;
    private Token nextToken;
    
    public AST(List<Token> tokens) {
        ASTNode startNode = new ASTNode(Grammar.PROGRAM);
        this.ASTRoot = startNode;
        this.tokens = tokens;
    }
    
    public void createAST() {
        ASTNode node = this.ASTRoot;
        this.nextToken = LocalUtil.peekTokenList(tokens);
        parseElment(node);
        
    } 
    
    public void parseElment(ASTNode node) {
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        Derivation pickedDerivation = new Derivation();
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
                    if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
                        pickedDerivation = derivation;
                        break;
                    }
                }
            }
        }

        for (GrammerElement grammerElement : pickedDerivation.getGrammarElements()) {
            ASTNode astNode = new ASTNode(grammerElement);
            if(grammerElement.getIsTerminal()){
                if(grammerElement.isASTNode()){
                    //shorten
                    astNode.getGrammerElement().setValue(LocalUtil.peekTokenList(tokens).getTokenValue().getStringValue());
                    node.addChild(astNode);
                }
                nextToken = consumeTerminal(this.tokens, grammerElement);                   
            } else {
                node.addChild(astNode);
                parseElment(astNode);
            }
        }    
        
    }

    public ASTNode getASTRoot() {
        return ASTRoot;
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

    public void traverse() {
        traverseNode(ASTRoot);
    }

    public void traverseNode(ASTNode astNode) {
        List<ASTNode> children = astNode.getChildren();
        for (ASTNode child : children) {        
            GrammerElement grammerElement = child.getGrammerElement();
            if(grammerElement.getIsTerminal()){
                System.out.println(child.getGrammerElement().getName()+"--->"+child.getGrammerElement().getValue());
            }   else {
                System.out.println(child.getGrammerElement().getName());
            }
            traverseNode(child);
        }
    }

    
}
