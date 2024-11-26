package com.project.tikiriCi.assembly_gen.elements;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.parser.GrammerElement;

public class AssExpression extends AASTNode{
    public String treeNodeType;

    public AssExpression(String treeNodeType, GrammerElement grammerElement) {
        super(grammerElement);
        this.treeNodeType = treeNodeType;
    }
    public String getTreeNodeType() {
        return treeNodeType;
    }

    public void setTreeNodeType(String treeNodeType) {
        this.treeNodeType = treeNodeType;
    } 
    
}
