package com.project.tikiriCi.assembly_gen.assembly_script;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.assembly_gen.elements.AssExpression;
import com.project.tikiriCi.assembly_gen.elements.AssInteger;
import com.project.tikiriCi.config.TreeNodeType;

public class ReturnAssembly extends AssemblyCodeComp {
    private AASTNode astNode;

    public ReturnAssembly(AASTNode astNode) {
        super(TreeNodeType.RETURN);
        this.astNode = astNode;
    }

    public String generateAssembly() {
        AssInteger assInteger = (AssInteger) astNode;
        if(astNode.getGrammerElement().getName() == TreeNodeType.INTEGER) {
            return String.format("  mov rax, %d \n mov rdi, rax \n mov rax, 60 \n syscall \n", assInteger.getGrammerElement().getValue());
        }
        return "";
    }
    
    
}
