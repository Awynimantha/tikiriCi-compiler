package com.project.tikiriCi.assembly_gen.assembly_script;

public abstract class AssemblyCodeComp { 
    public String treeNodeType;

    public AssemblyCodeComp(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    }

    abstract public String generateAssembly();

    public String getTreeNodeType() {
        return this.treeNodeType;
    }

}
