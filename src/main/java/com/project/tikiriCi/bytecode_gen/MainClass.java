package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

public class MainClass extends BytecodeComp{
    private ClassWriter classWriter;

    public MainClass(Program program, String treeNodeType) {
        super(treeNodeType);
        this.classWriter = program.getClassWriter();
        writeToClassWriter();
    }

    public ClassWriter getClassWriter() {
        return classWriter;
    }

    @Override
    public void writeToClassWriter() {
        classWriter.visit(V1_8, ACC_PUBLIC, "Main", null, "java/lang/Object", null);
        MethodVisitor constructor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD, 0); // Load "this"
        constructor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false); // Call super()
        constructor.visitInsn(RETURN); // Return
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
    }

    
    

}
