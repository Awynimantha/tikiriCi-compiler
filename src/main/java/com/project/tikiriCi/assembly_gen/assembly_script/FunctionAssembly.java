package com.project.tikiriCi.assembly_gen.assembly_script;

import com.project.tikiriCi.config.ASTNodeType;

public class FunctionAssembly extends AssemblyCodeComp {

    public FunctionAssembly() {
        super(ASTNodeType.FUNCTION);
    }

    public String generateAssembly(){
        return "\n_start\n";
    }

    
}
