package com.project.tikiriCi.assembly_gen.ASMT;

import java.util.List;

import com.project.tikiriCi.assembly_gen.AAST;
import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.config.AASTNodeType;

public class ASMT {
    private ASMTNode root;

    public ASMTNode getRoot() {
        return root;
    }

    public void setRoot(ASMTNode root) {
        this.root = root;
    }
    
    public void createASMT(AAST aast) {
        AASTNode asmtRoot = aast.getRoot();
        ASMTNodeVisitor asmtNodeVisitor = new ASMTNodeVisitor();
        this.root = asmtRoot.accept(asmtNodeVisitor);
        
    }

    public void traverseNode(AASTNode aastNode, ASMTNode asmtNode, ASMTNodeVisitor astNodeVisitor) {
        List<AASTNode> childrenNodes = aastNode.getChildren();
        for (AASTNode children : childrenNodes) {
            String astNodeType = children.getAASTNodeType();
            if(astNodeType== AASTNodeType.PROGRAM || astNodeType == AASTNodeType.FUNCTION || astNodeType==AASTNodeType.INSTRUCTION){
                ASMTNode newAASTNode = children.accept(astNodeVisitor);
                traverseNode(children, newAASTNode, astNodeVisitor);
                asmtNode.addChild(newAASTNode);
            }
        }
    }
}