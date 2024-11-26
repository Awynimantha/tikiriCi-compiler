package com.project.tikiriCi.assembly_gen.elements;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.config.TreeNodeType;
import com.project.tikiriCi.parser.GrammerElement;

public class AssInteger extends AssExpression{
    private String  value;
    
    public AssInteger(GrammerElement grammerElement, String value) {
        super(TreeNodeType.INTEGER,grammerElement);
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
