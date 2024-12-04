package com.project.tikiriCi;

import java.util.ArrayList;

import com.project.tikiriCi.lexer.Lexer;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.Parser;
import com.project.tikiriCi.parser.AST.AST;
import com.project.tikiriCi.parser.assembly_gen.AAST;
import com.project.tikiriCi.parser.assembly_gen.ASMT.ASMT;
import com.project.tikiriCi.parser.assembly_gen.assembly_script.AssemblyScript;

public class Main 
{
    public static void main( String[] args )
    {
       
       Lexer lexer = new Lexer("/media/yasiru/New Volume/PROJECTS/java/tikirCi-compile/tikiriCi-compiler/src/main/java/com/project/tikiriCi/lexer/test_scripts/script1.tikc") ;
        try {
            ArrayList<Token> tokens = lexer.doLex();
            // for (Token token : tokens) {
            //     System.out.println(token.getTokenValue().getStringValue()+"--"+token.getTokenType());
            // } 
            Parser parser = new Parser(tokens);
            parser.parse();
            AST ast = parser.getAST();
           // ast.traverse();
            AAST aast =  new AAST();
            aast.createAAST(ast);
            //aast.traverseTree();
            ASMT asmt = new ASMT();
            asmt.createASMT(aast);
            asmt.replaceRegister();
            asmt.traverseTree();
            // AssemblyScript assemblyScript = new AssemblyScript(null, "/home/yasiru/Desktop/test/ass.asm", aast);
            // assemblyScript.writeToScript();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


