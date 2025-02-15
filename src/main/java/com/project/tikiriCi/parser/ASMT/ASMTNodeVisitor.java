package com.project.tikiriCi.parser.ASMT;

import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.Registers;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.utility.LocalUtil;

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
         if(unary_op.getTokenType() == TokenType.COMPLEMENT) {
            asm = "notq "+operand_value+"\n";
        } else if(unary_op.getTokenType() == TokenType.SUB) {
            asm = "subq "+operand_value+"\n";

        }
        return asm;
    }
 
    public String createJumpAssembly(ASMTNode asmtNode) {
        ASMTNode labelNode = asmtNode.getChild(0);
        String labelName = labelNode.getValue();
        String asm = "";
        //hardcoded, no issue found
        String test = LocalUtil.createAssemblyStatment("test","%rax,","%rax");
        if(asmtNode.getASMTreeType() == ASMTreeType.J) {
            asm = LocalUtil.createAssemblyStatment(test, "JMP", labelName);
        } else if(asmtNode.getASMTreeType() == ASMTreeType.JZ) {
            asm = LocalUtil.createAssemblyStatment(test, "JZ", labelName);
        } else if(asmtNode.getASMTreeType() == ASMTreeType.JZN) {
            asm = LocalUtil.createAssemblyStatment(test, "JNZ", labelName);
        }
        return asm;
    }

    public String createLabelAssembly(ASMTNode asmtNode) {
        String labelName = asmtNode.getChild(0).getValue();
        String asm = LocalUtil.createAssemblyStatment(labelName.concat(":"));
        return asm;
    }



    public String createCmpAssembly(ASMTNode asmtNode) {
        ASMTNode binary_op = asmtNode.getChild(0);
        ASMTNode operand1 = asmtNode.getChild(1);
        ASMTNode operand2 = asmtNode.getChild(2);
        String asm = "";
        String set = "";
        if(binary_op.getTokenType() == TokenType.EQUAL) {
            set = "sete";
        } else if(binary_op.getTokenType() == TokenType.NOTEQUAL) {
            set = "setne";
        } else if(binary_op.getTokenType() == TokenType.LEFT_CHEVRON) {
            set = "setl";
        } else if(binary_op.getTokenType() == TokenType.EQUAL_LEFT_CHEVRON) {
            set = "setle";
        } else if(binary_op.getTokenType() == TokenType.RIGHT_CHEVRON) {
            set = "setg";
        } else if(binary_op.getTokenType() == TokenType.EQUAL_RIGHT_CHEVRON) {
            set = "setge";
        }
        asm = "cmp "+getNodeValue(operand1)+", %rax \n"+set +" %al \nmovzx %al ,%rax\nmovq %rax, "+getNodeValue(operand2)+"\n";
        return asm;
    }

    public String createBinaryAssembly(ASMTNode asmtNode) {
        ASMTNode binary_op = asmtNode.getChild(0);
        ASMTNode operand1 = asmtNode.getChild(1);
        ASMTNode operand2 = asmtNode.getChild(2);
        String asm = "";
        
        if(binary_op.getTokenType() == TokenType.PLUS) {
            asm = "addq "+getNodeValue(operand1)+", "+getNodeValue(operand2)+"\n";
        } else if(binary_op.getTokenType() == TokenType.MUL) {
            asm = "imulq "+getNodeValue(operand1)+", "+getNodeValue(operand2)+"\n";
        } else if(binary_op.getTokenType() == TokenType.SUB) {
            asm = "subq "+getNodeValue(operand1)+", "+getNodeValue(operand2)+"\n";
        } 
        return asm; 
    }

    public String createReturnAssembly(ASMTNode asmtNode) {
        return "movq %rbp, %rsp \npopq %rbp \nmovq %rax, %rdi \nmovq $60, %rax  \nsyscall\n";          
    }






    
}
