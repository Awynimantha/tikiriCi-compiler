package com.project.tikiriCi.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.utility.LocalUtil;

public class Lexer {
    private InputStream inputStream;
    
    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<Token> tokenize() throws IOException{
        try {
            ArrayList<Token> tokens = new ArrayList<Token>();
            int i;
            StringBuilder stringBuilder = new StringBuilder();
            Token prev = new Token(TokenType.NULL, "null");
            Token token;
            do{
                i = inputStream.read();
                stringBuilder.append((char) i);
                token = LocalUtil.matchAllRegex(stringBuilder.toString());
                if(token.getTokenType() == TokenType.NULL) {
                    stringBuilder = new StringBuilder();
                    tokens.add(token);    
                } else {
                    prev = token;
                }
            } while(i != -1);
            return tokens;
        } catch(IOException e) {
            throw e;
        }
    }
}
