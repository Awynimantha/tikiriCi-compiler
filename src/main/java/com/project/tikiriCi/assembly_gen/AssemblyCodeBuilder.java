package com.project.tikiriCi.assembly_gen;

import com.project.tikiriCi.assembly_gen.assembly_script.AssemblyCodeComp;
import com.project.tikiriCi.assembly_gen.assembly_script.EntryPointAssembly;
import com.project.tikiriCi.assembly_gen.assembly_script.FunctionAssembly;
import com.project.tikiriCi.assembly_gen.assembly_script.ReturnAssembly;
import com.project.tikiriCi.config.TreeNodeType;

public class AssemblyCodeBuilder {
    public AssemblyCodeComp assemblyCodeComp;
    public AssemblyCodeBuilder(AASTNode aastNode) {
        if(aastNode.getAASTNodeType() == TreeNodeType.PROGRAM){
            this.assemblyCodeComp = new EntryPointAssembly();
        } else if(aastNode.getAASTNodeType() == TreeNodeType.FUNCTION){
            this.assemblyCodeComp = new FunctionAssembly();
        } else if (aastNode.getAASTNodeType() == TreeNodeType.RETURN) {
            this.assemblyCodeComp = new ReturnAssembly(aastNode);
        }
    } 

    public AssemblyCodeComp getassemblyCodeComp() {
        return assemblyCodeComp;
    }

    
    
}