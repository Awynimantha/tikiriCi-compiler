package com.project.tikiriCi.main;

public class TokenValue {
    private int intValue;
    private String stringValue;
    private float floatValue; 
    private String type;

    public TokenValue(String type, int intValue) {
        this.type = type;
        this.intValue = intValue;
    }
    
    public TokenValue(String type, String stringValue) {
        this.type = type;
        this.stringValue = stringValue;
    }

    public TokenValue(String type, float floatValue) {
        this.type = type;
        this.floatValue = floatValue;
    }


    
}
