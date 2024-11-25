package com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree.elements;

import com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree.BASTNode;
import com.project.tikiriCi.parser.GrammerElement;

public class Function extends BASTNode{
    private String functionName;

    public Function(GrammerElement grammerElement, String functionName) {
        super(grammerElement);
        this.functionName = functionName;
    }

    
}
