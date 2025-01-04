package com.project.tikiriCi.parser.AST;

import java.util.HashMap;
import java.util.List;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.parser.Derivation;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.semantic_analyser.SemanticAnalyser;
import com.project.tikiriCi.parser.semantic_analyser.SemanticVariable;

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
        traverseNode(ASTRoot,0);
    }

    public void analyseSematics() throws CompilerException{ 
        SemanticAnalyser semanticAnalyser = new SemanticAnalyser();
        HashMap<String, SemanticVariable> variableMap = new HashMap<>();
        semanticTraverse(ASTRoot, semanticAnalyser, variableMap); 
    }

    public void semanticTraverse(ASTNode astNode, SemanticAnalyser semanticAnalyser, 
    HashMap<String, SemanticVariable> variableMap) throws CompilerException{
        List<ASTNode> children = astNode.getChildren();
        for (ASTNode child : children) {        
            if(child.getASTNodeType().equals(ASTNodeType.BLOCK)) {
                semanticAnalyser.blockAnalyser(child, variableMap);
            }
            semanticTraverse(child, semanticAnalyser, variableMap);
        }
    }

    //Ugly printer
    public void traverseNode(ASTNode astNode, int depth) {
        // Generate indentation for pretty printing based on the depth
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  "); // Two spaces per level, you can customize this
        }
        String sindent = indent.toString() ;

        List<ASTNode> children = astNode.getChildren();
        for (ASTNode child : children) {        
            GrammerElement grammerElement = child.getGrammerElement();

            // Print the current node with proper indentation
            if (grammerElement.getIsTerminal()) {
                System.out.println(sindent + child.getGrammerElement().getName() + " ---> " + child.getGrammerElement().getValue());
            } else {
                System.out.println(sindent + child.getGrammerElement().getName());
            }

            // Recurse for child nodes with incremented depth
            traverseNode(child, depth + 1);
    }
}




    
}
