package com.project.tikiriCi.parser.semantic_analyser;

public class SemanticVariable {
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

  public SemanticVariable makeNonCurrentBlock() {
    this.isInCurrentVariableMap = false;
    return this;
  }

  public SemanticVariable makeCurrentBlock() {
    this.isInCurrentVariableMap = true;
    return this;
  }
  
  @Override
  public boolean equals(Object newVar) {
    if(this.value == ((SemanticVariable)newVar).value) return true;
    else return false;
  }


}
