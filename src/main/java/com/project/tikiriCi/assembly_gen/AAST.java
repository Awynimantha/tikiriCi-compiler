package com.project.tikiriCi.assembly_gen;

import com.project.tikiriCi.assembly_gen.elements.AssFunction;
import com.project.tikiriCi.assembly_gen.elements.AssInteger;
import com.project.tikiriCi.assembly_gen.elements.AssProgram;
import com.project.tikiriCi.assembly_gen.elements.AssReturn;
import com.project.tikiriCi.config.ASTNodeType;
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
        // AST node children is needed to create the AAST node in the tree
        GrammerElement grammerElement = astNode.getGrammerElement();
        if(grammerElement.getName() == ASTNodeType.FUNCTION){
            String functionName = astNode.popChild().getGrammerElement().getValue();
            AssFunction assFunction = new AssFunction(astNode.getGrammerElement(), 
                "", functionName);
            aastNode.addChildren(assFunction);
            for (ASTNode node : astNode.getChildren()) {
                traverseNode(node, assFunction);
            }
        } else if(grammerElement.getName() == ASTNodeType.STATEMENT){
            ASTNode retNode = astNode.popChild();
            ASTNode expNode = astNode.popChild();
            ASTNode intNode = expNode.popChild();
            if(retNode.getGrammerElement().getName() == ASTNodeType.RETURN){
                if(intNode.getGrammerElement().getName() == ASTNodeType.INTEGER){
                    AssInteger assInteger = new AssInteger(intNode.getGrammerElement(),intNode.getGrammerElement().
                            getValue());
                    AssReturn assReturn = new AssReturn(assInteger,retNode.getGrammerElement());
                    aastNode.addChildren(assReturn);
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

    public void traverseTree(){
        traverse(root);
    }

    public void traverse(AASTNode aastNode) {
        System.out.println(aastNode.getAASTNodeType());
        for (AASTNode node : aastNode.getChildren()) {
            traverse(node);
        }
    }

    
}
