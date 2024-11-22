package com.project.tikiriCi.bytecode_gen;

import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Method extends BytecodeComp{
    private ClassWriter classWriter;
    private MethodVisitor methodVisitor;
    
    public Method(MainClass mainClass) {
        this.classWriter = mainClass.getClassWriter();
        writeToClassWriter();
    }

    public MethodVisitor getMethodVisitor() {
        return this.methodVisitor;
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }

    @Override
    public void writeToClassWriter() {
        MethodVisitor mainMethod = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        this.methodVisitor = mainMethod;
        mainMethod.visitCode();
        mainMethod.visitInsn(ICONST_9); // Push the constant 9 onto the stack
        mainMethod.visitMethodInsn(INVOKESTATIC, "java/lang/System", "exit", "(I)V", false); // Call System.exit(9)
        mainMethod.visitInsn(RETURN); // Return void
        mainMethod.visitMaxs(1, 1); // Specify stack and local variables
        mainMethod.visitEnd();
        classWriter.visitEnd();
        
    }

    
    
}
