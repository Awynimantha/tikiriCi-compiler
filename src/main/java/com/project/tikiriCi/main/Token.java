package com.project.tikiriCi.main;

public class Token {
    private String tokenType;
    private TokenValue tokenValue;
    
    
    public Token(String tokenType, TokenValue tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
    
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
}
