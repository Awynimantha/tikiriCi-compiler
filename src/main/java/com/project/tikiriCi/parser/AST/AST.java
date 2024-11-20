package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.GrammerElement;

public class AST {
    private ASTNode ASTRoot;
    private List<Token> tokens;

    public AST(List<Token> tokens) {
        //always start with start symbol
        ASTNode startNode = new ASTNode(Grammar.START);
        this.ASTRoot = startNode;
        this.tokens = tokens;
    }

    public void createAST() {
       ASTNode node = this.ASTRoot;
       parseElment(node);

    } 

    public void parseElment(ASTNode node) {
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        for (Derivation derivation : derivations) {
            for (GrammerElement grammerElement : derivation.getGrammarElements()) {
                ASTNode astNode = new ASTNode(grammerElement);
                node.addChild(astNode);
                if(grammerElement.getIsTerminal()){
                    consumeTerminal(this.tokens, grammerElement);
                } else {
                    parseElment(astNode);
                }
            }    
        }
    }

    private void consumeTerminal(List<Token> tokens, GrammerElement grammerElement) {
        int firstIndex = 0;
        Token firstToken = tokens.get(firstIndex);
        //System.out.println(grammerElement.getTokenType()+"------"+firstToken.getTokenType());
        if(grammerElement.getTokenType() == firstToken.getTokenType()){
            tokens.remove(firstIndex);
        } else{
             System.out.println("Error--------------------");
        }

    }

    public void traverse() {
        System.out.println(ASTRoot.getGrammerElement().getName());
        traverseNode(ASTRoot);
    }

    public void traverseNode(ASTNode astNode) {
        List<ASTNode> children = astNode.getChildren();
        for (ASTNode child : children) {
            System.out.println(child.getGrammerElement().getName());
            traverseNode(child);
        }


    }

    
}
