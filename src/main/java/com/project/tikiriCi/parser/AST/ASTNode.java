package com.project.tikiriCi.parser.AST;

import java.util.ArrayList;
import java.util.List;
import com.project.tikiriCi.parser.GrammerElement;

public class ASTNode {
    private GrammerElement grammerElement;
    private List<ASTNode> children;
   
    public ASTNode() {
        this.children = new ArrayList<ASTNode>() ;
    }

    public ASTNode(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<ASTNode>() ;
    }

    public GrammerElement getGrammerElement() {
        return grammerElement;
    }

    public void setGrammerElement(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

    public void addChild(ASTNode astNode) {
        this.children.add(astNode);
    }

    public ASTNode popChild() {
        int index = 0;
        ASTNode astNode = children.get(index);
        children.remove(astNode);
        return astNode;

    }

    public ASTNode popChild(int index) {
        ASTNode astNode = children.get(index);
        children.remove(index);
        return astNode;
    }


    
}
