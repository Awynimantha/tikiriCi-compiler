package com.project.tikiriCi.assembly_gen.ASMT;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.parser.GrammerElement;

public class ASMTNode {
    private String ASMTreetype;
    private GrammerElement grammerElement;
    private List<ASMTNode> children;
   
    public ASMTNode() {
        this.children = new ArrayList<ASMTNode>();
    }

    public ASMTNode(String asmTreeType) {
        this.ASMTreetype = asmTreeType;
        this.children = new ArrayList<ASMTNode>();
    }
    
    public ASMTNode(GrammerElement grammerElement, String asmTreeType) {
        this.grammerElement = grammerElement;
        this.ASMTreetype = asmTreeType;
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

    public ASMTNode getChild(int index) {
        if(index<children.size()){
            return children.get(index);
        }
        return null;
    }

    public void setChildren(List<ASMTNode> children) {
        this.children = children;
    }

    public void addChild(ASMTNode astNode) {
        this.children.add(astNode);
    }

    public String getASMTreeType() {
        return this.ASMTreetype;
    }

    
}
