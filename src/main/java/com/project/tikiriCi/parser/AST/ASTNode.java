package com.project.tikiriCi.parser.AST;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.AAST.AASTNode;
import com.project.tikiriCi.parser.GrammerElement;

public class ASTNode {
    private GrammerElement grammerElement;
    private List<ASTNode> children;
   
    public ASTNode() {
        this.children = new ArrayList<>() ;
        this.grammerElement = new GrammerElement();
    }

    public ASTNode(String astNodeType, Boolean isTerminal) {
        this.children = new ArrayList<>() ;
        this.grammerElement = new GrammerElement();
        this.grammerElement.setName(astNodeType);
        this.grammerElement.setIsTerminal(isTerminal);
    }

    public ASTNode(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<>() ;
    }
    
    public ASTNode(GrammerElement grammerElement, String astNodeType) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<>() ;
        grammerElement.setName(astNodeType);
    }

     public ASTNode(GrammerElement grammerElement, ASTNode... astNodeList) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<>() ;
        for (ASTNode astNode : astNodeList) {
            addChild(astNode);
        }
    }

    public void setASTNodeType(String nod) {
        this.grammerElement.setName(nod);
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
        if(grammerElement.getName().equals(ASTNodeType.PROGRAM)){
            return nodeVisitor.createProgramNode(this);
        } else if(grammerElement.getName().equals(ASTNodeType.FUNCTION)) {
            return nodeVisitor.createFunctionNode(this);
        }
        return new AASTNode();
    }

    public String getValue() {
        return grammerElement.getValue();
    }

    public void setValue(String value) {
        grammerElement.setValue(value);
    }

    public String getASTNodeType() {
        return grammerElement.getName();
    }

    public String getTokenType() {
        return grammerElement.getTokenType();
    }

    public ASTNode builChild(GrammerElement grammerElement, Token nextToken) {
        GrammerElement grammar = grammerElement.clone();
        grammar.setValue(nextToken.getTokenValue().getStringValue());
        ASTNode astNode = new ASTNode(grammar);
        this.addChild(astNode);
        return astNode;
    }

    public void addChildren(List<ASTNode> childrenList) {
        for (ASTNode astNode : childrenList) {
            children.add(astNode);
        }
    }

    /**
     * Spit first matching node
     */
    public ASTNode getTerminalChildByASTNodeType(String nodeType){
        ASTNode returnASTNode = new ASTNode();
        for (ASTNode astNode : children) {
            if(astNode.grammerElement.getTokenType() == nodeType) {
                returnASTNode = astNode;
            }           
        }
        return returnASTNode;
    }

    public ASTNode getNonTerminalChildByASTNodeType(String nodeType){
        ASTNode returnASTNode = new ASTNode();
        for (ASTNode astNode : children) {
            if(astNode.getASTNodeType() == nodeType) {
                returnASTNode = astNode;
            }           
        }
        return returnASTNode;
    }
    
}
