package com.project.tikiriCi.assembly_gen.assembly_script;

import com.project.tikiriCi.config.ASTNodeType;

/**
 * EntryPoint
 */
public class EntryPointAssembly extends AssemblyCodeComp {

    public EntryPointAssembly() {
        super(ASTNodeType.PROGRAM); 
    }

    public String generateAssembly(){
        return "section .text \n global _start";
    }

    

    
}