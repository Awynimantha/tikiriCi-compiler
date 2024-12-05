package com.project.tikiriCi.parser.assembly_gen.assembly_script;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.project.tikiriCi.main.File;
import com.project.tikiriCi.parser.assembly_gen.AAST;
import com.project.tikiriCi.parser.assembly_gen.AASTNode;
import com.project.tikiriCi.parser.assembly_gen.AssemblyCodeBuilder;

public class AssemblyScript {
    private String name;
    private String location;
    private String content;
    private AAST aAST;

    public AssemblyScript(String name, String location, AAST aAST) {
        this.name = name;
        this.location = location;
        this.aAST = aAST;
        this.content = "";
    }

    public void addContent(String content) {
        content.concat(content);
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
        traverseNode(aAST.getRoot());
    }

    public void traverseNode(AASTNode aastNode){
        AssemblyCodeComp assemblyCodeComp = new AssemblyCodeBuilder(aastNode).getassemblyCodeComp();
        content = content + assemblyCodeComp.generateAssembly();
        List<AASTNode> children = aastNode.getChildren();
        for (AASTNode node : children) {
            traverseNode(node);

        }
    }

    
    
    
}