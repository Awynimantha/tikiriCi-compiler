package com.project.tikiriCi.main;

public class TokenValue {
    private String stringValue;
    private String type;
  
    public TokenValue(String type, String stringValue) {
        this.type = type;
        this.stringValue = stringValue;
    }
    
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
}
