package com.project.tikiriCi.parser;

import java.util.List;

public class NonTerminal extends GrammerElement{

    public NonTerminal(String name, List<Derivation> derivations) {
        super(name, derivations,false);
        
    }

   
}
