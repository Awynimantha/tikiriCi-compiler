package com.project.tikiriCi.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.utility.LocalUtil;

public class Tokenizer {
    private InputStream inputStream;
    
    public Tokenizer(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    public Tokenizer() {
        this.inputStream = null;
    }
    
    public ArrayList<Token> tokenize() throws IOException{
        try {
            ArrayList<Token> tokens = new ArrayList<Token>();
            int i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            Token prev = new Token(TokenType.NULL, "null");
            Token token;
            while(i!=-1){
                stringBuilder.append((char) i);
                token = LocalUtil.matchAllRegex(stringBuilder.toString());
                if(token.getTokenType() == TokenType.NULL) {
                    stringBuilder = new StringBuilder();
                    if(((char)i) != TokenType.WHITESPACE){
                        stringBuilder.append((char)i);
                        token = LocalUtil.matchAllRegex(stringBuilder.toString());
                    }
                    if(prev.getTokenType() != TokenType.NULL ){
                        tokens.add(prev);    
                    }
                } 
                prev = token;                
                i = inputStream.read();
            }
            token = LocalUtil.matchAllRegex(stringBuilder.toString());
            if(token.getTokenType() != TokenType.NULL) {
                tokens.add(token);
            }
            return tokens;
        } catch(IOException e) {
            throw e;
        }
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    
}
