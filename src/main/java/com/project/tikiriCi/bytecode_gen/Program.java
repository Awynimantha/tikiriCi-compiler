package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

public class Program extends BytecodeComp{
    private ClassWriter classWriter;
    
    public Program(MainClass mainClass) {
        this.classWriter = new ClassWriter(0);
        writeToClassWriter();
    }

    public void writeToClassWriter() {
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }
}
