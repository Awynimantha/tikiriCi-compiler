package com.project.tikiriCi.bytecode_gen.method_instruction;

import org.objectweb.asm.ClassWriter;

public class Instruction {
    private ClassWriter classWriter;

    public Instruction() {
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }
    
}
