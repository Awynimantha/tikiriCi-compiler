package com.project.tikiriCi.assembly_gen;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.parser.GrammerElement;

public class AASTNode {
    private String treeNodeType;
    private List<AASTNode> children;
    private GrammerElement grammerElement;

    public AASTNode(){
        this.children = new ArrayList<AASTNode>();
    }

    public AASTNode(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
        this.treeNodeType = grammerElement.getName();
        this.children = new ArrayList<AASTNode>();
    }

    public GrammerElement getGrammerElement(){
        return grammerElement;
    }

    public void setGrammerElement(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
    }

    public void addChildren(AASTNode aastNode) {
        children.add(aastNode);
    }

    public List<AASTNode> getChildren() {
        return this.children;
    }

    public String getAASTNodeType() {
        return this.treeNodeType;
    }

    public void setAASTNodeType(String treeNodeType) {
        this.treeNodeType = treeNodeType; 
    }



    
}
