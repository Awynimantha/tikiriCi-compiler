package com.project.tikiriCi.parser.AST;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.config.ASTNodeType;
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

    public ASTNode getChild(int index) {
        if (index<children.size()){
            return children.get(index);
        }
        return null;
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

    public AASTNode accept(ASTNodeVisitor nodeVisitor) {
        if(grammerElement.getName() == ASTNodeType.PROGRAM){
            return nodeVisitor.createProgramNode(this);
        } else if(grammerElement.getName() == ASTNodeType.FUNCTION) {
            return nodeVisitor.createFunctionNode(this);
        } else if(grammerElement.getName() == ASTNodeType.STATEMENT){
            return nodeVisitor.createInstructionNode(this);
        }

        return new AASTNode();
    }

    public String getValue() {
        return grammerElement.getValue();
    }

    public String getASTNodeType() {
        return grammerElement.getName();
    }

    public String getTokenType() {
        return grammerElement.getTokenType();
    }

    
}
