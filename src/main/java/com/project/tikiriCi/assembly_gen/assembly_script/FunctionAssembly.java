package com.project.tikiriCi.assembly_gen.assembly_script;

import com.project.tikiriCi.config.TreeNodeType;

public class FunctionAssembly extends AssemblyCodeComp {

    public FunctionAssembly() {
        super(TreeNodeType.FUNCTION);
    }

    public String generateAssembly(){
        return "\n_start\n";
    }

    
}
