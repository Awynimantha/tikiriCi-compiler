package com.project.tikiriCi.parser;

import java.util.List;

public class Terminal extends GrammerElement{

    private String tokenType;
    public Terminal(String name, List<Derivation> derivations,  String tokenType) {
        super(name, derivations, true, tokenType);
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    


    
    
}
