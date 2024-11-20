package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

import com.project.tikiriCi.bytecode_gen.method_instruction.Instruction;

public class Method {
    private ClassWriter classWriter;
    private Instruction instruction;
    public Method(Instruction instruction) {
        this.classWriter = instruction.getClassWriter();
        this.instruction = instruction;
    }

    public ClassWriter getClassWriter() {
        return this.classWriter;
    }

    
    
}
