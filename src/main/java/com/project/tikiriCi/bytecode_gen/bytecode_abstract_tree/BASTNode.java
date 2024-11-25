package com.project.tikiriCi.bytecode_gen.bytecode_abstract_tree;

import java.util.ArrayList;
import java.util.List;
import com.project.tikiriCi.parser.GrammerElement;

public class BASTNode {
    private GrammerElement grammerElement;
    private List<BASTNode> children;

    public BASTNode() {
        this.children = new ArrayList<BASTNode>();
    }

    public BASTNode(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
        this.children = new ArrayList<BASTNode>();
    }
    
    public GrammerElement getGrammerElement() {
        return grammerElement;
    }

    public void setGrammerElement(GrammerElement grammerElement) {
        this.grammerElement = grammerElement;
    }

    public List<BASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<BASTNode> children) {
        this.children = children;
    }
    
}
