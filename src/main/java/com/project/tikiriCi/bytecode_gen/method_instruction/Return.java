package com.project.tikiriCi.bytecode_gen.method_instruction;

import org.objectweb.asm.MethodVisitor;

import com.project.tikiriCi.bytecode_gen.Method;

public class Return extends Instruction {

    public Return(Method method) {
        super(method.getMethodVisitor());
     
    }

    @Override
    public void writeToClassWriter() {
       
    }

 
    
}
