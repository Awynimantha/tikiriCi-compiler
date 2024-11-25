package com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree;

public abstract class ByteCodeElement extends BASTNode{
    private String treeNodeType;

    public ByteCodeElement(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    }

    public String getByteCodeElement() {
        return this.treeNodeType;
    }
}
