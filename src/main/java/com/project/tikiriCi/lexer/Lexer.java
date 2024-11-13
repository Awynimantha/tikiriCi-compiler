package com.project.tikiriCi.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.project.tikiriCi.main.Token;

public class Lexer {
    private InputStream inputStream;
    
    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<Token> tokenize() throws IOException{
        try {
            int i;
            do{
                i = inputStream.read();
                    

            } while(i != -1);
        } catch(IOException e) {
            throw e;
        }
    }
}
