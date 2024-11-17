package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.config.TokenType;

public class NonTerminal extends GrammerElement{

    public NonTerminal(String name, List<Derivation> derivations, String tokenType) {
        super(name, derivations,false, tokenType);
        
    }

   
}
