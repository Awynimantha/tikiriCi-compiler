package com.project.tikiriCi.assembly_gen.elements;

import com.project.tikiriCi.config.TreeNodeType;
import com.project.tikiriCi.parser.GrammerElement;

public class AssReturn extends AssInstruction{
   private String treeNodeType;
   private AssExpression assExpression;

   public AssReturn(AssExpression assExpression, GrammerElement grammerElement) {
       this.treeNodeType = grammerElement.getName();
       this.assExpression = assExpression;
    }
    
    public String getAASTNodeType() {
        return treeNodeType;
    }
 
    public void setTreeNodeType(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    }


    
}
