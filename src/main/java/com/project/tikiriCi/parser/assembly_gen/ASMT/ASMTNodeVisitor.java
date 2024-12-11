package com.project.tikiriCi.parser.assembly_gen.ASMT;

import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Registers;

public class ASMTNodeVisitor {

    private String getAllocatedSize(ASMTNode asmtNode) {
        ASMTNode intNode = asmtNode.getChild(0);
        return intNode.getValue();
    }

    private String getNodeValue(ASMTNode asmtNode) {
        if(asmtNode.getASMTreeType() == ASMTreeType.REG){
            ASMTNode reg_name = asmtNode.getChild(0);
            if(reg_name.getASMTreeType() == ASMTreeType.AX){
                return Registers.RAX;
            } else if(reg_name.getASMTreeType() == ASMTreeType.R10){
                return Registers.R10;
            }
        } else if(asmtNode.getASMTreeType() == ASMTreeType.IMM){
            return "$"+asmtNode.getValue();
        }
        return asmtNode.getValue();
    }


    public String createProgramAssembly(ASMTNode asmtNode) {
        return ".global _start\n";
    }

    public String createFunctionAssembly(ASMTNode asmtNode) {
        return "_start:\n";
    }

    public String createAllocateSizeAssembly(ASMTNode asmtNode) {
        String allocSize = getAllocatedSize(asmtNode);
        String asm = "pushq %rbp \nmovq %rsp, %rbp \nsubq $"+allocSize+", %rsp \n";
        return asm;

    }

    public String createMovAssembly(ASMTNode asmtNode) {
        String src = getNodeValue(asmtNode.getChild(0));
        String dst = getNodeValue(asmtNode.getChild(1));
        String asm = "movq %s,%s \n";
        String returnCode = String.format(asm, src, dst);
        return returnCode;

    }

    public String createUnaryAssembly(ASMTNode asmtNode) {
        ASMTNode unary_op = asmtNode.getChild(0);
        ASMTNode operand = asmtNode.getChild(1);
        String operand_value =  getNodeValue(operand);
        String asm = "";
        if(unary_op.getASMTreeType() == ASMTreeType.NOT) {
            asm = "notq "+ operand_value+"\n";
        } else if(unary_op.getASMTreeType() == ASMTreeType.NEGATE) {
            asm = "negq "+operand_value+"\n";
        }
        return asm;
    }

    public String createBinaryAssembly(ASMTNode asmtNode) {
        ASMTNode binary_op = asmtNode.getChild(0);
        ASMTNode operand1 = asmtNode.getChild(1);
        ASMTNode operand2 = asmtNode.getChild(2);
        String asm = "";
        if(binary_op.getASMTreeType() == ASMTreeType.ADD) {
            asm = "addq "+getNodeValue(operand1)+", "+getNodeValue(operand2)+"\n";
        } else if(binary_op.getASMTreeType() == ASMTreeType.MUL) {
            asm = "imulq "+getNodeValue(operand1)+", "+getNodeValue(operand2)+"\n";
        } else if(binary_op.getASMTreeType() == ASMTreeType.SUB) {
            
        }
        return asm;
    }

    public String createReturnAssembly(ASMTNode asmtNode) {
        return "movq %rbp, %rsp \npopq %rbp \nmovq %rax, %rdi \nmovq $60, %rax  \nsyscall ";          
    }






    
}
