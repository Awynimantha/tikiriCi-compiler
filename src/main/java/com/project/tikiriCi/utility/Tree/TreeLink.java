package com.project.tikiriCi.utility.Tree;

import com.project.tikiriCi.parser.ASMT.ASMTNode;

/**Represent how parent node and child node are linked **/
public class TreeLink {
    private ASMTNode parentNode;
    private ASMTNode child;
    private int childPosition;

    

    public TreeLink(ASMTNode parentNode) {
        this.parentNode = parentNode;
    }

    public TreeLink(ASMTNode parentNode, ASMTNode child, int childPosition) {
        this.parentNode = parentNode;
        this.child = child;
        this.childPosition = childPosition;
    }

    public ASMTNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(ASMTNode parentNode) {
        this.parentNode = parentNode;
    }

    public ASMTNode getChild() {
        return child;
    }

    public void setChild(ASMTNode child) {
        this.child = child;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }

    
}
