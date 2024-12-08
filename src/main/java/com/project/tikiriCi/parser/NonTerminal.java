package com.project.tikiriCi.parser;

import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.config.TokenType;

public class NonTerminal extends GrammerElement{

    public NonTerminal(String name, List<Derivation> list, String tokenType) {
        super(name, list,false, tokenType, true);
        
    }

   
}
