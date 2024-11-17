package com.project.tikiriCi.parser;

import java.util.ArrayList;
import java.util.List;

public class Derivation {
    private List<GrammerElement> grammerElements;

    public Derivation(List<GrammerElement> grammerElements) {
        this.grammerElements = grammerElements;
    }
    public Derivation(GrammerElement... grammerElements){
        List<GrammerElement> list = new ArrayList<GrammerElement>();
        for (GrammerElement grammerElement : grammerElements) {
            list.add(grammerElement);
        }
        this.grammerElements = list;
    }

    public Derivation() {
        this.grammerElements = new ArrayList<GrammerElement>();
    }

    public void addGrammarElement(GrammerElement grammerElement) {
        grammerElements.add(grammerElement);
    }

    public List<GrammerElement> getGrammarElements() {
        return this.grammerElements;
    }


}
