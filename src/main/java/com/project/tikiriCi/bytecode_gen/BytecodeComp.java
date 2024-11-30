package com.project.tikiriCi.bytecode_gen;

public abstract class BytecodeComp {
    private String ASTNodeType;

    public BytecodeComp(String ASTNodeType) {
        this.ASTNodeType = ASTNodeType;
    }

    public String getASTNodeType() {
        return this.ASTNodeType;
    }

    public abstract void writeToClassWriter();
    
}
