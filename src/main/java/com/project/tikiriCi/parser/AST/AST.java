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
                if(grammerElement.getIsTerminal()){
                    if(grammerElement.isASTNode()){
                        node.addChild(astNode);
                    }
                    consumeTerminal(this.tokens, grammerElement);

                } else {
                    node.addChild(astNode);
                    parseElment(astNode);
                }
            }    
        }
    }

    private ASTNode consumeTerminal(List<Token> tokens, GrammerElement grammerElement) {
        ASTNode astNode = new ASTNode();
        int firstIndex = 0;
        Token firstToken = tokens.get(firstIndex);
        //System.out.println(grammerElement.getTokenType()+"------"+firstToken.getTokenType());
        if(grammerElement.getTokenType() == firstToken.getTokenType()){
            grammerElement.setValue(firstToken.getTokenValue().getStringValue());
            astNode = new ASTNode(grammerElement);
            tokens.remove(firstIndex);
        } else{
             System.out.println("Error--------------------");
        }
        return astNode;
    }

    public void traverse() {
        System.out.println(ASTRoot.getGrammerElement().getName());
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
