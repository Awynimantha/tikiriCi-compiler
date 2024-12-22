package com.project.tikiriCi.parser.AAST;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.ASMT.ASMTNode;
import com.project.tikiriCi.parser.AST.ASTNode;

public class AASTNodeVisitor {

    public AASTNodeVisitor() {

    }

    public ASMTNode createProgramASTMTNode(AASTNode aastNode) {
        GrammerElement grammerElement = aastNode.getGrammerElement();
        return new ASMTNode(grammerElement, AASTNodeType.PROGRAM); 
    }

    public ASMTNode createFunctionASTMNode(AASTNode aastNode) {
        GrammerElement grammerElement = aastNode.getGrammerElement();
        return new ASMTNode(grammerElement, AASTNodeType.FUNCTION);
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
    
    public ASMTNode createOpreatorNode(AASTNode uoperatorAAST) {
        ASMTNode operator = new ASMTNode();
        operator = new ASMTNode(uoperatorAAST.getGrammerElement());
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

    public ASMTNode createCompareNode(ASMTNode operator,ASMTNode operand1, ASMTNode dst) {
        ASMTNode cmpNode = new ASMTNode(ASMTreeType.CMP);
        cmpNode.addChild(operator);
        cmpNode.addChild(operand1);
        cmpNode.addChild(dst);
        return cmpNode;
    }

    public ASMTNode createCopyNode(ASMTNode operand1, ASMTNode operand2) {
        ASMTNode copyNode = new ASMTNode(ASMTreeType.MOV);
        copyNode.addChild(operand1);
        copyNode.addChild(operand2);
        return copyNode;
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
            } else if(childNode.getAASTNodeType() == AASTNodeType.COPY) {
                asmNodeList = createCopyInstruction(childNode);
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
        ASMTNode operatorASM = createOpreatorNode(operatorNode);
        ASMTNode unary_instruction = createUnaryNode(operatorASM, destinationASM);

        nodeList.add(moveNode);
        nodeList.add(unary_instruction);
        return nodeList;
    }

      public List<ASMTNode> createCopyInstruction(AASTNode aastNode) {
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        //aastNode is the unary
        AASTNode sourceNode = aastNode.getChild(0);
        ASMTNode sourceASM = createOperandNode(sourceNode);

        AASTNode destinationNode = aastNode.getChild(1);
        ASMTNode destinationASM = createOperandNode(destinationNode);

        ASMTNode copyNode = createCopyNode(sourceASM, destinationASM);
        nodeList.add(copyNode);
        return nodeList; 
    }

    /*
     * Binary(op, src1, src2, dst) => 
        Mov(src1, dst)
        Binary(op, src2, dst)

       
     Binary(op, src1, src2, dst) => 
        Mov(SRC1, RAX) 
        CMP(SRC2, RAX, dst)
     */
    public List<ASMTNode> createBinaryInstruction(AASTNode aastNode) {
        ASMTNode op = createOpreatorNode(aastNode.getChild(0));
        ASMTNode src1 = createOperandNode(aastNode.getChild(1));
        ASMTNode src2 = createOperandNode(aastNode.getChild(2));
        ASMTNode dst = createOperandNode(aastNode.getChild(3));
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        ASMTNode register = new ASMTNode(ASMTreeType.REG);
        ASMTNode REGAX = new ASMTNode(ASMTreeType.AX);
        register.addChild(REGAX);
        String[] cmpTokenList = {TokenType.EQUAL, TokenType.NOTEQUAL, TokenType.LEFT_CHEVRON, 
                TokenType.EQUAL_LEFT_CHEVRON, TokenType.RIGHT_CHEVRON, TokenType.EQUAL_RIGHT_CHEVRON};
        for (String string : cmpTokenList) {
            if(op.getTokenType() == string) {
                ASMTNode movNode = createMovNode(src1, register);
                ASMTNode cmpNode = createCompareNode(op,src2, dst);
                nodeList.add(movNode);
                nodeList.add(cmpNode);
                return nodeList;
            }
        }
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
