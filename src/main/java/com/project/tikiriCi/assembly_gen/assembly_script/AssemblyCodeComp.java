package com.project.tikiriCi.assembly_gen.assembly_script;

public abstract class AssemblyCodeComp { 
    public String astNodeType;

    public AssemblyCodeComp(String astNodeType) {
        this.astNodeType = astNodeType;
    }

    abstract public String generateAssembly();

    public String getASTNodeType() {
        return this.astNodeType;
    }

}
