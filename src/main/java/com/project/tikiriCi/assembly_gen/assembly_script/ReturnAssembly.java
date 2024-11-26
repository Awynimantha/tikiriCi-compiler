package com.project.tikiriCi.assembly_gen.assembly_script;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.assembly_gen.elements.AssExpression;
import com.project.tikiriCi.assembly_gen.elements.AssInteger;
import com.project.tikiriCi.assembly_gen.elements.AssReturn;
import com.project.tikiriCi.config.TreeNodeType;

public class ReturnAssembly extends AssemblyCodeComp {
    private AASTNode astNode;

    public ReturnAssembly(AASTNode astNode) {
        super(TreeNodeType.RETURN);
        this.astNode = astNode;
    }

    public String generateAssembly() {
        AssReturn assReturn = (AssReturn) astNode;
        AssExpression assExpression = assReturn.getAssExpression();
        if(assExpression.getTreeNodeType() == TreeNodeType.INTEGER) {
            AssInteger assInteger =  (AssInteger)assExpression;
            return String.format("  mov rax, %s \n mov rdi, rax \n mov rax, 60 \n syscall \n", assInteger.getGrammerElement().getValue());
        }
        return "";
    }
    
    
}
