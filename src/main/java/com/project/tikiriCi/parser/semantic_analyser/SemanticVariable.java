package com.project.tikiriCi.parser.semantic_analyser;

class SemanticVariable {
  public String value;
  public boolean isInCurrentVariableMap;
  
  public SemanticVariable(String value, boolean isCurrent) {
    this.value = value;
    this.isInCurrentVariableMap = isCurrent;
  }

  public SemanticVariable(String value) {
    this.value = value;
    this.isInCurrentVariableMap = true;
  }

  public String getValue() {
    return this.value;
  }

  public boolean isInCurrentBlock() {
    return this.isInCurrentVariableMap;
  }

  public void makeNonCurrentBlock() {
    this.isInCurrentVariableMap = false;
  }

  public void makeCurrentBlock() {
    this.isInCurrentVariableMap = true;
  }
  
  @Override
  public boolean equals(Object newVar) {
    if(this.value == ((SemanticVariable)newVar).value) return true;
    else return false;
  }


}
