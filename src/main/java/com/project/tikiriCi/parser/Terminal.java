package com.project.tikiriCi.parser;

public class Terminal extends GrammerElement{

    private String tokenType;
    public Terminal(String name, String tokenType) {
        super(name ,null, true, tokenType);
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    


    
    
}
