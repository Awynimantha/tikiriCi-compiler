package com.project.tikiriCi.bytecode_gen.method_instruction;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassWriter;

import com.project.tikiriCi.bytecode_gen.Method;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.VariableType;

public class Return extends Instruction {
    private int intValue;
    private Boolean boolValue;
    private String stringValue;
    private String variableType;

    public Return(Method method, int intValue) {
        super(method.getMethodVisitor(), method.getClassWriter(), ASTNodeType.RETURN);     
        this.intValue = intValue;
        this.variableType = VariableType.IntegeValue;
        writeToMethodVisitor();
    }

    public Return(Method method, Boolean boolValue) {
        super(method.getMethodVisitor(), method.getClassWriter(), ASTNodeType.RETURN);
        this.boolValue = boolValue;
        this.variableType = VariableType.BooleanValue;
    }

     public Return(Method method, String stringValue) {
        super(method.getMethodVisitor(), method.getClassWriter(), ASTNodeType.RETURN);
        this.stringValue = stringValue;
        this.variableType = VariableType.BooleanValue;
    }

    @Override
    public void writeToMethodVisitor() {
        MethodVisitor methodVisitor = this.getMethodVisitor();
        ClassWriter classWriter = this.getClassWriter();
        methodVisitor.visitCode();
        methodVisitor.visitLdcInsn(intValue);
        methodVisitor.visitInsn(IRETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd(); 
        classWriter.visitEnd();
    }
    
}
