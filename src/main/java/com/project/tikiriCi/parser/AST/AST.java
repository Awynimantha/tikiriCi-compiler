package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.GrammerElement;

public class AST {
    private ASTNode ASTRoot;  
    
    public AST() {
        ASTNode startNode = new ASTNode(Grammar.PROGRAM);
        this.ASTRoot = startNode;
    }
    
    
    
    public void parseElment(ASTNode node) {
        //LL(K) parser k = 1
        List<Derivation> derivations = node.getGrammerElement().getDerivation();
        Derivation pickedDerivation = new Derivation();
        // for (Derivation derivation : derivations) {
        //     GrammerElement firstGrammerElement = derivation.peekDerivation();
        //     if(firstGrammerElement.getIsTerminal()){
        //         if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
        //             pickedDerivation = derivation;
        //             break;
        //         }
        //     } else {
        //         List<Derivation> firstGrammerElementDerivation = firstGrammerElement.getDerivation();
        //         for (Derivation deriv : firstGrammerElementDerivation) {
        //             firstGrammerElement = deriv.peekDerivation();
        //             if(firstGrammerElement.getIsTerminal()){
        //                 if(nextToken.getTokenType() == firstGrammerElement.getTokenType()){
        //                     pickedDerivation = derivation;
        //                     break;
        //                 }
        //             } else{
        //                 List<Derivation> fgrammerChildren = firstGrammerElement.getDerivation();
        //                 for (Derivation derivationl : fgrammerChildren) {
        //                     GrammerElement firstElement = derivationl.peekDerivation();
        //                     if(nextToken.getTokenType() == firstElement.getTokenType()){
        //                         pickedDerivation = derivation;
        //                         break;      
        //                     }
        //                 }

        //             }
        //         }
        //     }
        // }
        //Process the picked derivation
        // for (GrammerElement grammerElement : pickedDerivation.getGrammarElements()) {
        //     ASTNode astNode = new ASTNode(grammerElement);
        //     if(grammerElement.getIsTerminal()){
        //         if(grammerElement.isASTNode()){
        //             astNode.getGrammerElement().setValue(
        //                 LocalUtil.peekTokenList(tokens).getTokenValue().getStringValue());
        //             node.addChild(astNode);
        //         }
        //         nextToken = consumeTerminal(this.tokens, grammerElement);                   
        //     } else {
        //         node.addChild(astNode);
        //         parseElment(astNode);
        //     }
        // }    
        
    }

    public ASTNode getASTRoot() {
        return ASTRoot;
    }

    public void traverse() {
        traverseNode(ASTRoot);
    }
    //Ugly printer
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
