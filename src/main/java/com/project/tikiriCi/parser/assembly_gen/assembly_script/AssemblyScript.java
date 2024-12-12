package com.project.tikiriCi.parser.assembly_gen.assembly_script;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.project.tikiriCi.main.File;
import com.project.tikiriCi.parser.ASMT.ASMT;
import com.project.tikiriCi.parser.ASMT.ASMTNode;
import com.project.tikiriCi.parser.ASMT.ASMTNodeVisitor;
import com.project.tikiriCi.parser.assembly_gen.AssemblyCodeBuilder;

public class AssemblyScript {
    private String name;
    private String location;
    private String content;
    private ASMT ASMT;

    public AssemblyScript(String name, String location, ASMT ASMT) {
        this.name = name;
        this.location = location;
        this.ASMT = ASMT;
        this.content = "";
    }

    public void addContent(String content) {
        this.content += content;
    }

    public void writeToScript() throws FileNotFoundException{
        File file = new File(this.location);
        traverse();
        file.setWriteContent(content);
        try {
            file.writeToFile();
        } catch (IOException e) {
           
        }
    }

    public void traverse(){
        ASMTNodeVisitor asmtNodeVisitor = new ASMTNodeVisitor();
        addContent(ASMT.getRoot().accept(asmtNodeVisitor));
        traverseNode(ASMT.getRoot());
    }

    public void traverseNode(ASMTNode ASMTNode){
        List<ASMTNode> children = ASMTNode.getChildren();
        ASMTNodeVisitor asmtNodeVisitor = new ASMTNodeVisitor();
        for (ASMTNode node : children) {
            addContent(node.accept(asmtNodeVisitor));
            traverseNode(node);

        }
    }

    
    
    
}
