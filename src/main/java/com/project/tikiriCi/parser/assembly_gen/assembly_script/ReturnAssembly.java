package com.project.tikiriCi.parser.assembly_gen.assembly_script;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.AAST.AASTNode;

public class ReturnAssembly extends AssemblyCodeComp {
    private AASTNode astNode;

    public ReturnAssembly(AASTNode astNode) {
        super(ASTNodeType.RETURN);
        this.astNode = astNode;
    }

    public String generateAssembly() {
        // AssReturn assReturn = (AssReturn) astNode;
        // AssExpression assExpression = assReturn.getAssExpression();
        // if(assExpression.getASTNodeType() == ASTNodeType.INTEGER) {
        //     AssInteger assInteger =  (AssInteger)assExpression;
        //     return String.format("  mov rax, %s \n mov rdi, rax \n mov rax, 60 \n syscall \n", assInteger.getGrammerElement().getValue());
        // }
        return "";
    }
    
    
}
