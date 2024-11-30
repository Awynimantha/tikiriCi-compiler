package com.project.tikiriCi.bytecode_gen.method_instruction;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.project.tikiriCi.bytecode_gen.BytecodeComp;
import com.project.tikiriCi.config.ASTNodeType;



public abstract class Instruction extends BytecodeComp{
    private MethodVisitor methodVisitor;
    private ClassWriter classWriter;
   
    public Instruction(MethodVisitor methodVisitor, ClassWriter classWriter, String ASTNodeType) {
        super(ASTNodeType);
        this.methodVisitor = methodVisitor;
        this.classWriter = classWriter;
    }

    public ClassWriter getClassWriter() {
        return classWriter;
    }

    public void setClassWriter(ClassWriter classWriter) {
        this.classWriter = classWriter;
    }

    public abstract void writeToMethodVisitor();
    
    public void writeToClassWriter() {
        //no class writer
    }

    public MethodVisitor getMethodVisitor() {
        return this.methodVisitor;
    }
   
}
