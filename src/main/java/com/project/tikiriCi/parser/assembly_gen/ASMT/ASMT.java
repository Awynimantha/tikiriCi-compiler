package com.project.tikiriCi.parser.assembly_gen.ASMT;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Registers;
import com.project.tikiriCi.parser.assembly_gen.AAST;
import com.project.tikiriCi.parser.assembly_gen.AASTNode;

public class ASMT {
    private ASMTNode root;

    public ASMTNode getRoot() {
        return root;
    }

    public void setRoot(ASMTNode root) {
        this.root = root;
    }
    
    public void createASMT(AAST aast) {
        AASTNode aastRoot = aast.getRoot();
        ASMTNodeVisitor asmtNodeVisitor = new ASMTNodeVisitor();
        this.root = aastRoot.accept(asmtNodeVisitor);
        traverseNode(aastRoot, root, asmtNodeVisitor);
    }

    public void replaceRegister() {
        //breadth first traversal
        Queue<ASMTNode> queue = new LinkedList<ASMTNode>();
        ASMTNode asmtNode = new ASMTNode();
        queue.offer(root);
        while(!queue.isEmpty()) {
            asmtNode = queue.poll();
            List<ASMTNode> asmtNodesList = asmtNode.getChildren();
            if(asmtNode.getASMTreeType() == ASMTreeType.PSEUDO){
                String temRegName = asmtNode.getGrammerElement().getValue();
                String[] regNumberStr = temRegName.split("\\.");
                if(regNumberStr.length<=1){
                    continue;
                }
                String numString = regNumberStr[1];
                int regNumber = Short.parseShort(numString);
                regNumber = (regNumber+1)*(4);
                String newTemRegName = "-" + regNumber + "("+Registers.BASE_POINTER+")";
                asmtNode.getGrammerElement().setValue(newTemRegName);
            }
            for (ASMTNode node : asmtNodesList) {
                queue.offer(node);              
            }
            
        }
    }

    public void traverseNode(AASTNode aastNode, ASMTNode asmtNode, ASMTNodeVisitor astNodeVisitor) {
        List<AASTNode> childrenNodes = aastNode.getChildren();
        for (AASTNode child : childrenNodes) {
            String astNodeType = child.getAASTNodeType();
            //process only important node
            if(astNodeType== AASTNodeType.PROGRAM || astNodeType == AASTNodeType.FUNCTION || astNodeType==AASTNodeType.INSTRUCTION){
                ASMTNode newAASTNode = child.accept(astNodeVisitor);
                traverseNode(child, newAASTNode, astNodeVisitor);
                asmtNode.addChild(newAASTNode);
            }
        }
    }

    public void traverseTree(){
        traverse(root);
    }
    
    public void traverse(ASMTNode asmtNode) {
        for (ASMTNode node : asmtNode.getChildren()) {
            if(node.getASMTreeType() == ASMTreeType.IMM || node.getASMTreeType() == ASMTreeType.PSEUDO) {
                System.out.println(node.getASMTreeType()+"-->"+node.getGrammerElement().getValue());
            } else{
                System.out.println(node.getASMTreeType());
            }
            traverse(node);
        }
    }
}