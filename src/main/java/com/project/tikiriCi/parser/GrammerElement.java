package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.config.TokenType;

public abstract  class GrammerElement {
    private String name;
    private List<Derivation> derivations;
    private String tokenType;  
    private Boolean isTerminal; 
    
    public GrammerElement(String name, List<Derivation> derivations, boolean isTerminal, String tokenType) {
        this.name = name;
        this.derivations = derivations;
        this.isTerminal = isTerminal;
        this.tokenType = tokenType;
    } 
    
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getIsTerminal() {
        return isTerminal;
    }

    public void setIsTerminal(Boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDerivation(Derivation derivation) {
        this.derivations.add(derivation);
    }

    public List<Derivation> getDerivation() {
        return this.derivations;
    }
}
