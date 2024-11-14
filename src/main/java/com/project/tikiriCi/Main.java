package com.project.tikiriCi;

import java.util.ArrayList;

import com.project.tikiriCi.lexer.Lexer;
import com.project.tikiriCi.main.Token;

public class Main 
{
    public static void main( String[] args )
    {
       Lexer lexer = new Lexer("/media/yasiru/New Volume/PROJECTS/java/tikirCi-compile/tikiriCi-compiler/src/main/java/com/project/tikiriCi/lexer/test_scripts/script1.tikc") ;
       try {
        ArrayList<Token> tokens = lexer.doLex();
        for (Token token : tokens) {
            System.out.println(token.getTokenValue().getStringValue()+"--"+token.getTokenType());
        } 
       } catch (Exception e) {
            e.printStackTrace();
       }
    }
}
