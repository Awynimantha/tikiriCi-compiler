package com.project.tikiriCi.assembly_gen.elements;

import com.project.tikiriCi.assembly_gen.AASTNode;

public class AssInteger extends AASTNode implements AssExpression{
    private String  value;
    
    public AssInteger(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
