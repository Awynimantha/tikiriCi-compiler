package com.project.tikiriCi.parser;

import java.util.List;

public abstract  class GrammerElement {
    private String name;
    private List<Derivation> derivations;
    private Boolean isTerminal; 
    
    public GrammerElement(String name, List<Derivation> derivations, boolean isTerminal) {
        this.name = name;
        this.derivations = derivations;
        this.isTerminal = isTerminal;
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
