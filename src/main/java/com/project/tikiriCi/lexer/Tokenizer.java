package com.project.tikiriCi.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.exception.LexerException;
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
    /**
     * Pick the longest charcter string and tokenize
     * @return
     * @throws IOException
     * @throws LexerException
     */ 
    public ArrayList<Token> tokenize() throws IOException, LexerException{
        try {
            ArrayList<Token> tokens = new ArrayList<Token>();
            int i = inputStream.read();
            StringBuilder currentString = new StringBuilder();
            StringBuilder nextStringAdd = new StringBuilder();
            Token token;
            while(i!=-1){
                if(LocalUtil.ignoreChar((char) i)){
                    i = inputStream.read();
                    continue;
                }
                currentString.append((char) i);
                i = inputStream.read();
                if(i == -1) {
                    token = LocalUtil.matchAllRegex(currentString.toString());
                    if(token.getTokenType() != TokenType.NULL){
                        tokens.add(token);
                    } else {
                        throw new LexerException("Invalid Token: "+token.getTokenValue().getStringValue());
                    }
                } else {
                    //assum maximum length of the symbol is 2
                    nextStringAdd.append((char) i);
                    String str = currentString.toString() + nextStringAdd.toString();
                    if(LocalUtil.isSymbol(currentString.toString())){
                        token = LocalUtil.matchAllRegex(str);
                        if(LocalUtil.isSymbol(nextStringAdd.toString()) && LocalUtil.isSymbol(currentString.toString()) && token.getTokenType() != TokenType.NULL){
                            tokens.add(token);
                            currentString = new StringBuilder();
                            nextStringAdd = new StringBuilder();
                            i= inputStream.read();
                            continue;  
                            
                        } else if(LocalUtil.isSymbol(currentString.toString())) {
                            token = LocalUtil.matchAllRegex(currentString.toString());
                            if(token.getTokenType() == TokenType.NULL) {
                                //error
                                throw new LexerException("Invalid Symbol Token: "+token.getTokenValue().getStringValue());
                            }
                            tokens.add(token);
                            currentString = new StringBuilder();
                            nextStringAdd = new StringBuilder();
                            continue;
                        }
                    } 
                    token = LocalUtil.isToken(currentString.toString(), currentString.toString()+nextStringAdd.toString());
                    if(token.getTokenType() == TokenType.TOKEN) {
                        token = LocalUtil.matchAllRegex(currentString.toString());
                        if(token.getTokenType() != TokenType.NULL){
                            tokens.add(token);
                        } else {
                            //error
                            throw new LexerException("Invalid Token: "+token.getTokenValue().getStringValue());
                        }
                        currentString = new StringBuilder();

                    }
                    nextStringAdd = new StringBuilder();
                }
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
