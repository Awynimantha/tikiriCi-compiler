package com.project.tikiriCi.main;

import com.project.tikiriCi.config.TokenType;

public class Token {
    private String tokenType;
    private TokenValue tokenValue;
        
    public Token(String tokenType, TokenValue tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
    
    public Token(String tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = new TokenValue(tokenType, tokenValue);
    }
    
    public TokenValue getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(TokenValue tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getPrecedence() {
        if(tokenType == TokenType.LEFT_CHEVRON) {
            return 35;
        } else if(tokenType == TokenType.EQUAL_LEFT_CHEVRON) {
            return 35;
        } else if(tokenType == TokenType.RIGHT_CHEVRON) {
            return 35;
        } else if(tokenType == TokenType.EQUAL_RIGHT_CHEVRON) {
            return 35;
        } else if(tokenType == TokenType.EQUAL){
            return 30;
        } else if(tokenType == TokenType.NOTEQUAL){
            return 30;
        }else if(tokenType == TokenType.AND){
            return 10; 
        }else if(tokenType == TokenType.OR){
            return 5; 
        }else if(tokenType == TokenType.PLUS) {
            return 45;
        } else if(tokenType == TokenType.SUB) {
            return 45;
        } else if(tokenType == TokenType.MUL) {
            return 50;
        } else if(tokenType == TokenType.DIV) {
            return 50;
        } else {
            return 50;
        }
    }

    
    
}
