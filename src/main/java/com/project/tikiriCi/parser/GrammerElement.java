package com.project.tikiriCi.parser;

import java.util.List;

public class GrammerElement {
    private String name;
    private List<Derivation> derivations;
    private String tokenType;  
    private Boolean isTerminal; 
    private boolean isASTNode;
    private String value;
    
    public GrammerElement(){
    } 

    public GrammerElement(String name, List<Derivation> derivations, boolean isTerminal, String tokenType, Boolean isASTNode) {
        this.name = name;
        this.derivations = derivations;
        this.isTerminal = isTerminal;
        this.tokenType = tokenType;
        this.isASTNode = isASTNode;
    } 
    
    public boolean isASTNode() {
        return isASTNode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
