package com.project.tikiriCi.assembly_gen.ASMT;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.assembly_gen.AAST;
import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.parser.GrammerElement;

public class ASMTNodeVisitor {

    public ASMTNodeVisitor() {

    }

    public ASMTNode createProgramASTMTNode(AASTNode aastNode) {
        return new ASMTNode(Grammar.PROGRAM, ASMTreeType.PROGRAM); 
    }

    public ASMTNode createFunctionASTMNode(AASTNode aastNode) {
        return new ASMTNode(Grammar.FUNCTION, ASMTreeType.FUNCTION);

    }

    private ASMTNode createOperandNode(AASTNode aastNode){
        ASMTNode val = new ASMTNode();
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(aastNode.getGrammerElement().getValue());
        if(aastNode.getAASTNodeType() == AASTNodeType.CONSTANCE){
            val = new ASMTNode(grammerElement, ASMTreeType.IMM);
        } else if(aastNode.getAASTNodeType() == AASTNodeType.VAR){
            val = new ASMTNode(grammerElement, ASMTreeType.PSEUDO);
        }

        return val;
    }

    private ASMTNode createUOpreatorNode(AASTNode uoperatorAAST) {
        ASMTNode operator = new ASMTNode();
        if(uoperatorAAST.getAASTNodeType() == AASTNodeType.COMPLEMENT){
            operator = new ASMTNode(ASMTreeType.NOT);
        } else if(uoperatorAAST.getAASTNodeType() == AASTNodeType.NEGATE) {
            operator = new ASMTNode(ASMTreeType.NEGATE);
        }
        return operator;
    }


    private ASMTNode createMovNode(ASMTNode source, ASMTNode destination) {
        ASMTNode moveNode = new ASMTNode(ASMTreeType.MOV);
        moveNode.addChild(source);
        moveNode.addChild(destination);
        return moveNode;
    }

    private ASMTNode createUnaryNode(ASMTNode unary_operator, ASMTNode destination){
        ASMTNode unary = new ASMTNode(ASMTreeType.UNARY);
        unary.addChild(unary_operator);
        unary.addChild(destination);
        return unary;
    }

    public ASMTNode createInstruction(AASTNode aastNode) {
        ASMTNode instructionNode = new ASMTNode(ASMTreeType.INSTRUCTION);
        List<AASTNode> nodeList = aastNode.getChildren();
        List<ASMTNode> asmNodeList = new ArrayList<ASMTNode>();
        for (AASTNode childNode : nodeList) {
            if(childNode.getAASTNodeType() == AASTNodeType.RETURN){
                asmNodeList = createReturnInstruction(childNode);
            }   
        }
        for (ASMTNode asmtNode : asmNodeList) {
            instructionNode.addChild(asmtNode);
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
        AASTNode returnVal = aastNode.getChild(1);
        val = createOperandNode(returnVal);
        ASMTNode movNode = createMovNode(val, register);
        nodeList.add(movNode);
        nodeList.add(new ASMTNode(ASMTreeType.RET));
        return nodeList;
    }

    public List<ASMTNode> createUnaryInstruction(AASTNode aastNode){
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        //aastNode is the unary
        AASTNode sourceNode = aastNode.getChild(1);
        ASMTNode sourceASM = createOperandNode(sourceNode);

        AASTNode destinationNode = aastNode.getChild(2);
        ASMTNode destinationASM = createOperandNode(destinationNode);

        ASMTNode moveNode = createMovNode(sourceASM, destinationASM);
        
        AASTNode operatorNode = aastNode.getChild(0);
        ASMTNode operatorASM = createOperandNode(operatorNode);
        ASMTNode unary_instruction = new ASMTNode(ASMTreeType.UNARY);
        unary_instruction.addChild(operatorASM);
        unary_instruction.addChild(destinationASM);

        nodeList.add(moveNode);
        nodeList.add(unary_instruction);
        return nodeList;
    }


    
}
