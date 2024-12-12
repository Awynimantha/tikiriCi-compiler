package com.project.tikiriCi.parser.ASMT;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Registers;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AAST.AAST;
import com.project.tikiriCi.parser.AAST.AASTNode;
import com.project.tikiriCi.parser.AAST.AASTNodeVisitor;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.utility.LocalUtil;

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
        AASTNodeVisitor asmtNodeVisitor = new AASTNodeVisitor();
        this.root = aastRoot.accept(asmtNodeVisitor);
        traverseNode(aastRoot, root, asmtNodeVisitor);
    }

    public int replaceRegister() {
        //breadth first traversal
        Queue<ASMTNode> queue = new LinkedList<ASMTNode>();
        ASMTNode asmtNode = new ASMTNode();
        int regNumber = 1;
        queue.offer(this.root);
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
                regNumber = Short.parseShort(numString);
                regNumber = (regNumber+1)*(8);
                String newTemRegName = "-" + regNumber + "("+Registers.BASE_POINTER+")";
                asmtNode.getGrammerElement().setValue(newTemRegName);
            }
            for (ASMTNode node : asmtNodesList) {
                queue.offer(node);              
            }
            
        }
        return regNumber;
    }

    public void fixRegAndStack() {
        Queue<ASMTNode> queue = new LinkedList<ASMTNode>();
        ASMTNode asmtNode = new ASMTNode();
        int numOfReg = replaceRegister();

        //create allocate size node
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(numOfReg+"");
        ASMTNode stackSize = new ASMTNode(grammerElement);

        //create the allocatesize node
        ASMTNode allocateStack = new ASMTNode(ASMTreeType.ALLOCATESTACK);
        allocateStack.addChild(stackSize);

        queue.offer(this.root);
        while(!queue.isEmpty()){
            //pop the child
            asmtNode = queue.poll();
            List<ASMTNode> asmtNodeList = asmtNode.getChildren();
            //implementation
            if(asmtNode.getASMTreeType() == ASMTreeType.INSTRUCTION){
                asmtNode.addChildrenToFront(allocateStack);
            }

            for(ASMTNode node: asmtNodeList) {
                queue.offer(node);
            }
        }

    }

      public void processMovNodes() {
        Queue<ASMTNode> queue = new LinkedList<ASMTNode>();
        ASMTNode asmtNode = new ASMTNode();
        int numOfReg = replaceRegister();

        //create allocate size node
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(numOfReg+"");
        ASMTNode stackSize = new ASMTNode(grammerElement ,ASMTreeType.INTEGER);

        //create the allocatesize node
        ASMTNode allocateStack = new ASMTNode(ASMTreeType.ALLOCATESTACK);
        allocateStack.addChild(stackSize);

        queue.offer(this.root);
        while(!queue.isEmpty()){
            //pop the child
            asmtNode = queue.poll();
            List<ASMTNode> asmtNodeList = asmtNode.getChildren();
          
            List<ASMTNode> newChildren = new ArrayList<ASMTNode>();
            for(ASMTNode node: asmtNodeList) {
                if(node.getASMTreeType() == ASMTreeType.MOV){
                    if(LocalUtil.isInvalidMov(node)){
                        List<ASMTNode> newMovNodesList = fixMoveNode(node);
                        newChildren.add(newMovNodesList.get(0));
                        newChildren.add(newMovNodesList.get(1));
                        queue.offer(newMovNodesList.get(0));
                        queue.offer(newMovNodesList.get(1));
                        continue;
                    }
                }
                newChildren.add(node);
                queue.offer(node);
            }
            asmtNode.emptyChildren();
            asmtNode.setChildren(newChildren);
        }

    }

    public List<ASMTNode> fixMoveNode(ASMTNode asmtNode) {
        AASTNodeVisitor asmtNodeVisitor = new AASTNodeVisitor();
        List<ASMTNode> asmtNodesList = new ArrayList<ASMTNode>();
        ASMTNode firstPReg = asmtNode.getChild(0);
        ASMTNode secondReg = asmtNode.getChild(1);

        ASMTNode register = new ASMTNode(ASMTreeType.REG);
        register.addChild(new ASMTNode(ASMTreeType.R10));
        //remove the node visitor
        ASMTNode firstMov = asmtNodeVisitor.createMovNode(firstPReg, register);
        ASMTNode secondMov = asmtNodeVisitor.createMovNode(register, secondReg);

        if(firstPReg.getASMTreeType() == ASMTreeType.PSEUDO ||
            secondReg.getASMTreeType() == ASMTreeType.PSEUDO){           
                asmtNodesList.add(firstMov);
                asmtNodesList.add(secondMov);
        }
        return asmtNodesList;
    }

    public void traverseNode(AASTNode aastNode, ASMTNode asmtNode, AASTNodeVisitor astNodeVisitor) {
        List<AASTNode> childrenNodes = aastNode.getChildren();
        for (AASTNode child : childrenNodes) {
            String astNodeType = child.getAASTNodeType();
            //process only important node
            if(astNodeType== AASTNodeType.PROGRAM || astNodeType == AASTNodeType.FUNCTION || 
                astNodeType==AASTNodeType.INSTRUCTION){
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
            if(node.getASMTreeType() == ASMTreeType.IMM || node.getASMTreeType() == ASMTreeType.PSEUDO ||
                node.getASMTreeType() == ASMTreeType.INTEGER || node.getASMTreeType() == ASMTreeType.FUNCTION) {
                System.out.println(node.getASMTreeType()+"-->"+node.getGrammerElement().getValue());
            } else{
                System.out.println(node.getASMTreeType());
            }
            traverse(node);
        }
    }
}