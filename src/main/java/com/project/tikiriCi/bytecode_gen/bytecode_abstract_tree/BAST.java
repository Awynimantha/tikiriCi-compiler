package com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree;

import java.util.List;
import java.util.function.Function;

import com.project.tikiriCi.bytecode_gen.Method;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.AST.ASTNode;

public class BAST {
    private BASTNode root;

    public BASTNode getRoot() {
        return root;
    }

    public void setRoot(BASTNode root) {
        this.root = root;
    }

    public void createBAST(AST ast) {
        ASTNode astRoot = ast.getASTRoot();
        BASTNode bastNode = new BASTNode(astRoot.getGrammerElement());
        traverseNode(astRoot, bastNode);
    }

    public void traverseNode(ASTNode astNode, BASTNode bastNode) {
        List<ASTNode> children = astNode.getChildren();
        GrammerElement grammerElement = astNode.getGrammerElement();
        if(grammerElement.getName() == ASTNodeType.FUNCTION){
          
        } 
        for (ASTNode child : children) {
        }


    }

}
