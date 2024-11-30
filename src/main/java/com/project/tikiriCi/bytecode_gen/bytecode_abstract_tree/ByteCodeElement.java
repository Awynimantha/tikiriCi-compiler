package com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree;

public abstract class ByteCodeElement extends BASTNode{
    private String ASTNodeType;

    public ByteCodeElement(String ASTNodeType) {
        this.ASTNodeType = ASTNodeType;
    }

    public String getByteCodeElement() {
        return this.ASTNodeType;
    }
}
