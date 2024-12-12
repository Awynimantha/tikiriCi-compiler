package com.project.tikiriCi.parser.ASMT;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.ASMTreeType;
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

    public String getValue() {
        return grammerElement.getValue();
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

    public void addChildrenToFront(ASMTNode asmtNode) {
        int front = 0;
        children.add(front, asmtNode);
    }

    public void addChildAt(int index, ASMTNode asmtNode) {
        children.add(index, asmtNode);
    }

    public void removeChildAt(int index) {
        if(index<children.size()){
            children.remove(index);
        }
    }

    public void emptyChildren() {
        children.clear();
    }

    public String accept(ASMTNodeVisitor asmtNodeVisitor) {
        if(ASMTreetype == ASMTreeType.PROGRAM) {
            return asmtNodeVisitor.createProgramAssembly(this);
        } else if(ASMTreetype == ASMTreeType.FUNCTION) {
            return asmtNodeVisitor.createFunctionAssembly(this);
        } else if(ASMTreetype == ASMTreeType.ALLOCATESTACK){
            return asmtNodeVisitor.createAllocateSizeAssembly(this);
        }else if(ASMTreetype == ASMTreeType.UNARY) {
            return asmtNodeVisitor.createUnaryAssembly(this);
        } else if(ASMTreetype == ASMTreeType.MOV) {
            return asmtNodeVisitor.createMovAssembly(this);
        } else if(ASMTreetype == ASMTreeType.RET) {
            return asmtNodeVisitor.createReturnAssembly(this);
        } else if(ASMTreetype == ASMTreeType.BINARY) {
            return asmtNodeVisitor.createBinaryAssembly(this);
        }
        return "";

    }

    
}
