package com.project.tikiriCi.parser.assembly_gen;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.assembly_gen.ASMT.ASMTNode;
import com.project.tikiriCi.parser.assembly_gen.ASMT.AASTNodeVisitor;

public class AASTNode {
    private String aASTNodeType;
    private List<AASTNode> children;
    private GrammerElement grammerElement;

    public AASTNode(){
        this.children = new ArrayList<AASTNode>();
    }

    public AASTNode(String aASTNodeType) {
        this.aASTNodeType = aASTNodeType;
        this.children = new ArrayList<AASTNode>();
    }

    public AASTNode(GrammerElement grammerElement, String aASTNodeType) {
        this.grammerElement = grammerElement;
        this.aASTNodeType = aASTNodeType;
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
        return this.aASTNodeType;
    }

    public void setAASTNodeType(String ASTNodeType) {
        this.aASTNodeType = ASTNodeType; 
    }

    public void insertChildAtStart(AASTNode aastNode) {
        children.add(0, aastNode);
    }

    public AASTNode getChild(int index) {
        if(children.size()>index) {
            return children.get(index);
        }
        return null;
    }
    
     public ASMTNode accept(AASTNodeVisitor nodeVisitor) {
        if(aASTNodeType == AASTNodeType.PROGRAM){
            return nodeVisitor.createProgramASTMTNode(this);
        } else if(aASTNodeType == AASTNodeType.FUNCTION) {
            return nodeVisitor.createFunctionASTMNode(this);
        } else if(aASTNodeType == AASTNodeType.INSTRUCTION){
            return nodeVisitor.createInstruction(this);
        }

        return new ASMTNode();
    }



    
}
