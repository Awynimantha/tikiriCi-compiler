package com.project.tikiriCi.bytecode_gen;

import org.objectweb.asm.ClassWriter;

public class MainClass {
    private ClassWriter classWriter;
    private Method method;

    public MainClass(Method method) {
        this.classWriter = method.getClassWriter();
        this.method = method;
    }

    public ClassWriter getClassWriter() {
        return classWriter;
    }

    

}
