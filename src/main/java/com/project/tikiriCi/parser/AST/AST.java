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
        ASTNode startNode = new ASTNode(Grammar.START, null);
        this.ASTRoot = startNode;
        this.tokens = tokens;
    }

    public void createAST() {
       ASTNode node = this.ASTRoot;

    } 

    public void parseElment(ASTNode node) {
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        for (Derivation derivation : derivations) {
            for (GrammerElement grammerElement : derivation.getGrammarElements()) {
                ASTNode astNode = new ASTNode(grammerElement, null);
                node.addChild(astNode);
                if(grammerElement.getIsTerminal()){
                    consumeTerminal(this.tokens, grammerElement);
                } else {
                    parseElment(node);
                }
            }    

        }
    }

    public void consumeTerminal(List<Token> tokens, GrammerElement grammerElement) {
        int firstIndex = 0;
        Token firstToken = tokens.get(firstIndex);
        if(grammerElement.getTokenType() == firstToken.getTokenType()){
            tokens.remove(firstIndex);
        }

    }

    
}
