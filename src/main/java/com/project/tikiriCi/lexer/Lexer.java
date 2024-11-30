package com.project.tikiriCi.lexer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.project.tikiriCi.exception.LexerException;
import com.project.tikiriCi.main.File;
import com.project.tikiriCi.main.Token;
// 
public class Lexer {
    private String fileLocation;
    private Tokenizer tokenizer;

    public Lexer(String fileLocation) {
        this.fileLocation = fileLocation;
        this.tokenizer = new Tokenizer();
    }

    public ArrayList<Token> doLex() throws LexerException{
        ArrayList<Token> tokens = null;
        FileInputStream fileInputStream;
        try{
            File file = new File(fileLocation);
            file.readFile();
            fileInputStream = file.getContent();
            tokenizer.setInputStream(fileInputStream);
        } catch(FileNotFoundException e) {
            throw new LexerException(e);
        }
        try {
            tokens = tokenizer.tokenize();           
        } catch (Exception e) {
            throw new LexerException(e);
        }    
        return tokens;
    }


    
}
