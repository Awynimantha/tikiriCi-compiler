package com.project.tikiriCi.assembly_gen;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.parser.GrammerElement;

public class AASTNode {
    private String AASTNodeType;
    private List<AASTNode> children;
    private GrammerElement grammerElement;

    public AASTNode(){
        this.children = new ArrayList<AASTNode>();
    }

    public AASTNode(String aASTNodeType) {
        this.AASTNodeType = aASTNodeType;
        this.children = new ArrayList<AASTNode>();
    }

    public AASTNode(GrammerElement grammerElement, String aASTNodeType) {
        this.grammerElement = grammerElement;
        this.AASTNodeType = aASTNodeType;
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
        return this.AASTNodeType;
    }

    public void setAASTNodeType(String ASTNodeType) {
        this.AASTNodeType = ASTNodeType; 
    }



    
}
