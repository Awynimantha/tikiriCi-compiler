package com.project.tikiriCi.assembly_gen.assembly_script;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.project.tikiriCi.assembly_gen.AAST;
import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.assembly_gen.AssemblyCodeBuilder;
import com.project.tikiriCi.main.File;

public class AssemblyScript {
    private String name;
    private String location;
    private String content;
    private AAST aAST;

    public AssemblyScript(String name, String location, AAST aAST) {
        this.name = name;
        this.location = location;
    }

    public void addContent(String content) {
        content.concat(content);
    }

    public void writeToScript() throws FileNotFoundException{
        File file = new File(this.location);
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
        List<AASTNode> children = aastNode.getChildren();
        for (AASTNode node : children) {
            traverseNode(node);
            AssemblyCodeComp assemblyCodeComp = new AssemblyCodeBuilder(node).getassemblyCodeComp();
            content.concat(assemblyCodeComp.generateAssembly());

        }
    }

    
    
    
}
