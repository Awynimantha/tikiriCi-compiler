package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

public class Program {
    private ClassWriter classWriter;
    private MainClass mainClass;
    
    public Program(MainClass mainClass) {
        this.classWriter = mainClass.getClassWriter();
        this.mainClass = mainClass;
    }

    public void generateProgram() {
        //write bytecode
    }
}
