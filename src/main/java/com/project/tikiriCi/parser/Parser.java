package com.project.tikiriCi.parser;

import java.util.List;

import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.parser.AST.AST;

public class Parser {
    private AST ast;

    public Parser(List<Token> tokens) {
        this.ast = new AST(tokens);
    }

    public void parse() {
        ast.createAST();
    }
    public AST getAST() {
        return this.ast;
    }
}
