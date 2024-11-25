package com.project.tikiriCi.assembly_gen;

import com.project.tikiriCi.assembly_gen.elements.AssFunction;
import com.project.tikiriCi.assembly_gen.elements.AssInteger;
import com.project.tikiriCi.assembly_gen.elements.AssProgram;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.config.TreeNodeType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.AST.ASTNode;

public class AAST {
    private AASTNode root;

    public AASTNode getRoot() {
        return root;
    }

    public void setRoot(AASTNode root) {
        this.root = root;
    }

    public void createAAST(AST ast) {
        ASTNode astRoot = ast.getASTRoot();
        AssProgram assProgram = new AssProgram();
        this.root = assProgram;
        for (ASTNode astNode : astRoot.getChildren()) {
           traverseNode(astNode, this.root); 
        }
    }

    public void traverseNode(ASTNode astNode, AASTNode aastNode) {
        GrammerElement grammerElement = astNode.getGrammerElement();
        if(grammerElement.getName() == TreeNodeType.FUNCTION){
            String functionName = astNode.popChild(0).getGrammerElement().getValue();
            String functionType = astNode.popChild(1).getGrammerElement().getValue();
            AssFunction assFunction = new AssFunction(astNode.getGrammerElement(), 
                functionType, functionName);
            aastNode.addChildren(assFunction);
            for (ASTNode node : astNode.getChildren()) {
                traverseNode(node, assFunction);
            }
        } else if(grammerElement.getName() == TreeNodeType.STATEMENT){
            ASTNode aNode = astNode.popChild(0);
            if(aNode.getGrammerElement().getName() == TreeNodeType.RETURN){
                if(aNode.getGrammerElement().getName() == TreeNodeType.INTEGER){
                    AssInteger assInteger = new AssInteger(aNode.getGrammerElement().
                            getValue());
                    aastNode.addChildren(assInteger);
                    for (ASTNode node : astNode.getChildren()) {
                        traverseNode(node, assInteger);
                        
                    }
                }
            }
        } else {
            for (ASTNode node : astNode.getChildren()) {
                traverseNode(node, aastNode);

            }
        }
    }

    
}
