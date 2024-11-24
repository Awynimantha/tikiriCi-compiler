package com.project.tikiriCi.bytecode_gen;

import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Method extends BytecodeComp{
    private ClassWriter classWriter;
    private MethodVisitor methodVisitor;
    
    public Method(MainClass mainClass, String treeNodeType) {
        super(treeNodeType);
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
        MethodVisitor mainMethod = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", 
            "([Ljava/lang/String;)I", null, null);
        this.methodVisitor = mainMethod;
        
    }

    
    
}
