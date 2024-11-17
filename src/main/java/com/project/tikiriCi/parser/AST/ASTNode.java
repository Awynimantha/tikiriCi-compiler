package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.parser.GrammerElement;

public class ASTNode {
    private GrammerElement grammerElement;
    private List<ASTNode> children;
    
    public ASTNode(GrammerElement grammerElement, List<ASTNode> children) {
        this.grammerElement = grammerElement;
        this.children = children;
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


    
}
