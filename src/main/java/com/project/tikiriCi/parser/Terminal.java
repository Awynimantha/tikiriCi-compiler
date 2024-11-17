package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.config.TokenType;

public class Terminal extends GrammerElement{

    private TokenType tokenType;
    public Terminal(String name, List<Derivation> derivations, boolean isTerminal, TokenType tokenType) {
        super(name, derivations, isTerminal);
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
    


    
    
}
