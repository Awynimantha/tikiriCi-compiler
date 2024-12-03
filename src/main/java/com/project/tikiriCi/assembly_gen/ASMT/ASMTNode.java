package com.project.tikiriCi.assembly_gen.ASMT;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AST.ASTNode;

public class ASMTNode {
    private GrammerElement grammerElement;
    private List<ASMTNode> children;
   
    public ASMTNode() {
        this.children = new ArrayList<ASMTNode>();
    }
    
    public ASMTNode(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<ASMTNode>();
    } 
    
    public GrammerElement getGrammerElement() {
        return grammerElement;
    }

    public void setGrammerElement(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
    }

    public List<ASMTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASMTNode> children) {
        this.children = children;
    }
    
}
