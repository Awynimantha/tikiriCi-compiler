package com.project.tikiriCi.parser.AAST;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.ASMT.ASMTNode;

public class AASTNodeVisitor {

    public AASTNodeVisitor() {

    }

    public ASMTNode createProgramASTMTNode(AASTNode aastNode) {
        return new ASMTNode(Grammar.PROGRAM, ASMTreeType.PROGRAM); 
    }

    public ASMTNode createFunctionASTMNode(AASTNode aastNode) {
        GrammerElement grammerElement = aastNode.getGrammerElement();
        return new ASMTNode(grammerElement, ASMTreeType.FUNCTION);
    }

    public ASMTNode createOperandNode(AASTNode aastNode){
        ASMTNode val = new ASMTNode();
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(aastNode.getGrammerElement().getValue());
        if(aastNode.getAASTNodeType() == AASTNodeType.CONSTANCE){
            val = new ASMTNode(grammerElement, ASMTreeType.IMM);
            return val;
        } else if(aastNode.getAASTNodeType() == AASTNodeType.VAR){
            val = new ASMTNode(grammerElement, ASMTreeType.PSEUDO);
            return val;
        }
        return val;
    }

    public ASMTNode createUOpreatorNode(AASTNode uoperatorAAST) {
        ASMTNode operator = new ASMTNode();
        if(uoperatorAAST.getAASTNodeType() == AASTNodeType.COMPLEMENT){
            operator = new ASMTNode(ASMTreeType.NOT);
        } else if(uoperatorAAST.getAASTNodeType() == AASTNodeType.NEGATE) {
            operator = new ASMTNode(ASMTreeType.NEGATE);
        }
        return operator;
    }

    public ASMTNode createBinaryOpreatorNode(AASTNode boperatorAAST) {
        ASMTNode operator = new ASMTNode();
        if(boperatorAAST.getAASTNodeType() == AASTNodeType.PLUS){
            operator = new ASMTNode(ASMTreeType.ADD);
        } else if(boperatorAAST.getAASTNodeType() == AASTNodeType.MUL) {
            operator = new ASMTNode(ASMTreeType.MUL);
        }
        return operator;
    }


    public ASMTNode createMovNode(ASMTNode source, ASMTNode destination) {
        ASMTNode moveNode = new ASMTNode(ASMTreeType.MOV);
        moveNode.addChild(source);
        moveNode.addChild(destination);
        return moveNode;
    }

    public ASMTNode createUnaryNode(ASMTNode unary_operator, ASMTNode destination){
        ASMTNode unary = new ASMTNode(ASMTreeType.UNARY);
        unary.addChild(unary_operator);
        unary.addChild(destination);
        return unary;
    }

    public ASMTNode createBinaryNode(ASMTNode binary_operator, ASMTNode src, ASMTNode dst){
        ASMTNode binary = new ASMTNode(ASMTreeType.BINARY);
        binary.addChild(binary_operator);
        binary.addChild(src);
        binary.addChild(dst);
        return binary;
    }

    public ASMTNode createInstruction(AASTNode aastNode) {
        ASMTNode instructionNode = new ASMTNode(ASMTreeType.INSTRUCTION);
        List<AASTNode> nodeList = aastNode.getChildren();
        List<ASMTNode> asmNodeList = new ArrayList<ASMTNode>();
        for (AASTNode childNode : nodeList) {
            if(childNode.getAASTNodeType() == AASTNodeType.RETURN) {
                asmNodeList = createReturnInstruction(childNode);
            } else if(childNode.getAASTNodeType() == AASTNodeType.UNARY) {
                asmNodeList = createUnaryInstruction(childNode);
            } else if(childNode.getAASTNodeType() == AASTNodeType.BINARY) {
                asmNodeList = createBinaryInstruction(childNode);
            }  
            for (ASMTNode asmtNode : asmNodeList) {
                instructionNode.addChild(asmtNode);
            }
        }
        return instructionNode;
    }

    public List<ASMTNode> createReturnInstruction(AASTNode aastNode) {
        //aastNode is the return node
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        ASMTNode val = new ASMTNode();
        //create the register
        ASMTNode register = new ASMTNode(ASMTreeType.REG);
        ASMTNode REGAX = new ASMTNode(ASMTreeType.AX);
        register.addChild(REGAX);
        //define the two operands
        AASTNode returnVal = aastNode.getChild(0);
        val = createOperandNode(returnVal);
        ASMTNode movNode = createMovNode(val, register);
        nodeList.add(movNode);
        nodeList.add(new ASMTNode(ASMTreeType.RET));
        return nodeList;
    }

    public List<ASMTNode> createUnaryInstruction(AASTNode aastNode) {
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        //aastNode is the unary
        AASTNode sourceNode = aastNode.getChild(1);
        ASMTNode sourceASM = createOperandNode(sourceNode);

        AASTNode destinationNode = aastNode.getChild(2);
        ASMTNode destinationASM = createOperandNode(destinationNode);

        ASMTNode moveNode = createMovNode(sourceASM, destinationASM);
        
        AASTNode operatorNode = aastNode.getChild(0);
        ASMTNode operatorASM = createUOpreatorNode(operatorNode);
        ASMTNode unary_instruction = createUnaryNode(operatorASM, destinationASM);

        nodeList.add(moveNode);
        nodeList.add(unary_instruction);
        return nodeList;
    }

    /*
     * Binary(op, src1, src2, dst)
        to:
        Mov(src1, dst)
        Binary(op, src2, dst)
     */
    public List<ASMTNode> createBinaryInstruction(AASTNode aastNode) {
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        ASMTNode op = createBinaryOpreatorNode(aastNode.getChild(0));
        ASMTNode src1 = createOperandNode(aastNode.getChild(1));
        ASMTNode src2 = createOperandNode(aastNode.getChild(2));
        ASMTNode dst = createOperandNode(aastNode.getChild(3));
        ASMTNode register = new ASMTNode(ASMTreeType.REG);
        ASMTNode REGAX = new ASMTNode(ASMTreeType.AX);
        register.addChild(REGAX);
        ASMTNode moveNode1 = createMovNode(src1, dst);
        ASMTNode moveNode2 = createMovNode(dst, register);
        ASMTNode binaryNode = createBinaryNode(op, src2, register);
        ASMTNode moveNode3 = createMovNode(register, dst);
        nodeList.add(moveNode1);
        nodeList.add(moveNode2);
        nodeList.add(binaryNode);
        nodeList.add(moveNode3);
        return nodeList;
    }
    
}
