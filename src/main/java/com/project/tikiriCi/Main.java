package com.project.tikiriCi;

import com.project.tikiriCi.lexer.Lexer;

public class Main 
{
    public static void main( String[] args )
    {
       Lexer lexer = new Lexer("/media/yasiru/New Volume/PROJECTS/java/tikirCi-compile/tikiriCi-compiler/src/main/java/com/project/tikiriCi/lexer/test_scripts/script1.tikc") ;
       try {
            System.out.println(lexer.doLex().get(0).getTokenValue().getStringValue());
        
       } catch (Exception e) {
            e.printStackTrace();
       }
    }
}
