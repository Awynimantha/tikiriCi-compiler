package com.project.tikiriCi.parser.assembly_gen;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.assembly_gen.assembly_script.AssemblyCodeComp;
import com.project.tikiriCi.parser.assembly_gen.assembly_script.EntryPointAssembly;
import com.project.tikiriCi.parser.assembly_gen.assembly_script.FunctionAssembly;
import com.project.tikiriCi.parser.assembly_gen.assembly_script.ReturnAssembly;

public class AssemblyCodeBuilder {
    public AssemblyCodeComp assemblyCodeComp;
    public AssemblyCodeBuilder(AASTNode aastNode) {
        if(aastNode.getAASTNodeType() == ASTNodeType.PROGRAM){
            this.assemblyCodeComp = new EntryPointAssembly();
        } else if(aastNode.getAASTNodeType() == ASTNodeType.FUNCTION){
            this.assemblyCodeComp = new FunctionAssembly();
        } else if (aastNode.getAASTNodeType() == ASTNodeType.RETURN) {
            this.assemblyCodeComp = new ReturnAssembly(aastNode);
        }
    } 

    public AssemblyCodeComp getassemblyCodeComp() {
        return assemblyCodeComp;
    }

    
    
}
