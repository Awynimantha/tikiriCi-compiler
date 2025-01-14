package com.project.tikiriCi.parser.AST;

import java.util.HashMap;
import java.util.List;

import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.semantic_analyser.SemanticAnalyser;
import com.project.tikiriCi.parser.semantic_analyser.SemanticVariable;

public class AST {
    private ASTNode ASTRoot;  
    
    public AST() {
        ASTNode startNode = new ASTNode(Grammar.PROGRAM);
        this.ASTRoot = startNode;
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
        ASTNode functionNode = astNode.getChild(0);
        ASTNode blockNode = functionNode.getChild(2);
        semanticAnalyser.blockAnalyser(blockNode, variableMap);
        // List<ASTNode> children = astNode.getChildren();
        // for (ASTNode child : children) {        
        //     if(child.getASTNodeType().equals(ASTNodeType.BLOCK)) {
        //         semanticAnalyser.blockAnalyser(blockNode, variableMap);
        //     }
        //     semanticTraverse(child, semanticAnalyser, variableMap);
        // }
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
