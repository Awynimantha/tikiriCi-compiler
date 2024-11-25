package com.project.tikiriCi.bytecode_gen;

public abstract class BytecodeComp {
    private String treeNodeType;

    public BytecodeComp(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    }

    public String getTreeNodeType() {
        return this.treeNodeType;
    }

    public abstract void writeToClassWriter();
    
}
