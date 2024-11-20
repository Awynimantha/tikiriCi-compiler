package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

public class MainClass {
    private ClassWriter classWriter;
    private Method method;
    
    public MainClass(ClassWriter classWriter, Method method) {
        this.classWriter = classWriter;
        this.method = method;
    }

    

}
