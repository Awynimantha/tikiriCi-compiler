package com.project.tikiriCi.parser;

import java.util.ArrayList;
import java.util.List;

public class Derivation {
    private List<GrammerElement> grammerElements;

    public Derivation(List<GrammerElement> grammerElements) {
        this.grammerElements = grammerElements;
    }

    public Derivation() {
        this.grammerElements = new ArrayList<GrammerElement>();
    }

    public void addElement(GrammerElement grammerElement) {
        grammerElements.add(grammerElement);
    }

    public List<GrammerElement> getGrammarElements() {
        return this.grammerElements;
    }


}
