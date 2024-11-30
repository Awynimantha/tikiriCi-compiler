package com.project.tikiriCi.assembly_gen.elements;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.parser.GrammerElement;

public class AssExpression extends AASTNode{
    public String astNodeType;

    public AssExpression(String astNodeType, GrammerElement grammerElement) {
        super(grammerElement);
        this.astNodeType = astNodeType;
    }
    public String getASTNodeType() {
        return astNodeType;
    }

    public void setASTNodeType(String ASTNodeType) {
        this.astNodeType = ASTNodeType;
    } 

    
}
