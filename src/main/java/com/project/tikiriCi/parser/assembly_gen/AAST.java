package com.project.tikiriCi.parser.assembly_gen;

import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.parser.AST.ASTNodeVisitor;

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
        ASTNodeVisitor astNodeVisitor = new ASTNodeVisitor();
        this.root = astRoot.accept(astNodeVisitor);
        traverseNode(astRoot, this.root, astNodeVisitor);
        
    }

    public void traverseNode(ASTNode astNode, AASTNode aastNode, ASTNodeVisitor astNodeVisitor) {
        List<ASTNode> childrenNodes = astNode.getChildren();
        for (ASTNode children : childrenNodes) {
            String astNodeType = children.getASTNodeType();
            if(astNodeType== ASTNodeType.PROGRAM || astNodeType == ASTNodeType.FUNCTION || astNodeType==ASTNodeType.STATEMENT){
                AASTNode newAASTNode = children.accept(astNodeVisitor);
                traverseNode(children, newAASTNode, astNodeVisitor);
                aastNode.addChildren(newAASTNode);
            }
        }
    }

    // public void traverseNode(ASTNode astNode, AASTNode aastNode) {
    //     // AST node children is needed to create the AAST node in the tree
    //     GrammerElement grammerElement = astNode.getGrammerElement();
    //     if(grammerElement.getName() == ASTNodeType.FUNCTION){
    //         String functionName = astNode.popChild().getGrammerElement().getValue();
    //         AssFunction assFunction = new AssFunction(astNode.getGrammerElement(), 
    //             "", functionName);
    //         aastNode.addChildren(assFunction);
    //         for (ASTNode node : astNode.getChildren()) {
    //             traverseNode(node, assFunction);
    //         }
    //     } else if(grammerElement.getName() == ASTNodeType.STATEMENT){
    //         ASTNode retNode = astNode.popChild();
    //         ASTNode expNode = astNode.popChild();
    //         ASTNode intNode = expNode.popChild();
    //         if(retNode.getGrammerElement().getName() == ASTNodeType.RETURN){
    //             if(intNode.getGrammerElement().getName() == ASTNodeType.INTEGER){
    //                 AssInteger assInteger = new AssInteger(intNode.getGrammerElement(),intNode.getGrammerElement().
    //                         getValue());
    //                 AssReturn assReturn = new AssReturn(assInteger,retNode.getGrammerElement());
    //                 aastNode.addChildren(assReturn);
    //                 for (ASTNode node : astNode.getChildren()) {
    //                     traverseNode(node, assInteger);
                        
    //                 }
    //             }
    //         }
    //     } else {
    //         for (ASTNode node : astNode.getChildren()) {
    //             traverseNode(node, aastNode);

    //         }
    //     }
    // }

    public void traverseTree(){
        traverse(root);
    }
    
    public void traverse(AASTNode aastNode) {
        for (AASTNode node : aastNode.getChildren()) {
            if(node.getAASTNodeType() == AASTNodeType.VAR || node.getAASTNodeType() == AASTNodeType.CONSTANCE ||
            node.getAASTNodeType() == AASTNodeType.FUNCTION) {
                System.out.println(node.getAASTNodeType()+"-->"+node.getGrammerElement().getValue());
            } else{
                System.out.println(node.getAASTNodeType());
            }
            traverse(node);
        }
    }

    
}
