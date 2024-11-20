package com.project.tikiriCi.bytecode_gen.method_instruction;

import java.lang.invoke.MethodHandle;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.project.tikiriCi.bytecode_gen.BytecodeComp;

public abstract class Instruction extends BytecodeComp{
    private ClassWriter classWriter;
    private MethodVisitor methodVisitor;

    public Instruction(MethodVisitor methodVisitor) {
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        this.methodVisitor = methodVisitor;
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }

    public void writeToClass() {

    }


    
}
