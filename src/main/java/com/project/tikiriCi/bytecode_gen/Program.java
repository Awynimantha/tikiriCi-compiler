package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

public class Program extends BytecodeComp{
    private ClassWriter classWriter;
    
    public Program(MainClass mainClass, String ASTNodeType) {
        super(ASTNodeType);
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES 
        | ClassWriter.COMPUTE_MAXS);
        writeToClassWriter();
    }

    public void writeToClassWriter() {
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }
}
