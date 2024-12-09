package com.project.tikiriCi.config;

import java.util.HashMap;

import com.project.tikiriCi.main.Token;

public class Precedence {
    private HashMap<Token, Integer> mapping;

    public Precedence() {
        mapping = new HashMap<Token,Integer>();
    }

    public void setPrecedence(Token token, int precedence) {
        mapping.put(token, precedence);
    }


}
