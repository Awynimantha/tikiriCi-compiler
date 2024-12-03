package com.project.tikiriCi.assembly_gen.ASMT;

import java.util.ArrayList;
import java.util.List;

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

    public ASMTNode createInstruction(AASTNode aastNode) {
        ASMTNode instructionNode = new ASMTNode(ASMTreeType.INSTRUCTION);
        List<AASTNode> nodeList = aastNode.getChildren();
        List<ASMTNode> asmNodeList = new ArrayList<ASMTNode>();
        for (AASTNode childNode : nodeList) {
            if(childNode.getAASTNodeType() == AASTNodeType.RETURN){
                asmNodeList = createReturnNode(aastNode);
            }   
        }
        for (ASMTNode asmtNode : asmNodeList) {
            instructionNode.addChild(asmtNode);
        }
        return instructionNode;
    }

    public List<ASMTNode> createReturnNode(AASTNode aastNode) {
        List<ASMTNode> nodeList = new ArrayList<ASMTNode>();
        AASTNode returnVal = aastNode.getChild(1);
        ASMTNode val = new ASMTNode();
        ASMTNode asmtNode = new ASMTNode(ASMTreeType.MOV);
        ASMTNode register = new ASMTNode(ASMTreeType.REG);
        ASMTNode REGAX = new ASMTNode(ASMTreeType.AX);
        register.addChild(REGAX);
        //define the two operands
        if(returnVal.getAASTNodeType() == AASTNodeType.CONSTANCE){
            GrammerElement grammerElement = new GrammerElement();
            grammerElement.setValue(returnVal.getGrammerElement().getValue());
            val = new ASMTNode(grammerElement, ASMTreeType.IMM);
        } else if(returnVal.getAASTNodeType() == AASTNodeType.VAR){
            GrammerElement grammerElement = new GrammerElement();
            grammerElement.setValue(returnVal.getGrammerElement().getValue());
            val = new ASMTNode(grammerElement, ASMTreeType.PSEUDO);
        }

        asmtNode.addChild(val);
        asmtNode.addChild(register);
        nodeList.add(asmtNode);
        nodeList.add(new ASMTNode(ASMTreeType.RET));
        return nodeList;
    }


    
}
