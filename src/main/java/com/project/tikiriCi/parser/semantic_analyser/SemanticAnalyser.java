package com.project.tikiriCi.parser.semantic_analyser;

import java.util.HashMap;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AST.ASTNode;

public class SemanticAnalyser {
    private HashMap<String, String> variableMap;
    private int uniqueIdentifer;

    public SemanticAnalyser() {
        variableMap = new HashMap<String,String>();
        uniqueIdentifer = 0;
    }

    public String createUniqueVar(String value) {
        return uniqueIdentifer + value;
    }

    public void declarationAnalyser(ASTNode declAstNode) throws CompilerException{
        //identifier node is the second child
        ASTNode identifierNode =  declAstNode.getTerminalChildByASTNodeType(TokenType.IDENTIFIER);
        String value = identifierNode.getValue();
        if(variableMap.containsKey(value)) {
            throw new CompilerException("Error: Duplicate variable declaration of "+value);
        }
        String newValue = createUniqueVar(value);
        variableMap.put(value, newValue);
        identifierNode.setValue(newValue);
        //check if there is expression
        if(declAstNode.getChildren().size()>3) {
           expressionAnalyser(declAstNode.getNonTerminalChildByASTNodeType(ASTNodeType.EXPRESSION));
        }
    }

    public void expressionAnalyser(ASTNode expNode) throws CompilerException {
        ASTNode astNode = expNode.getChild(0);
        if(astNode.getASTNodeType() == ASTNodeType.ASSIGNMENT){
            ASTNode leftNode = astNode.getChild(0).getChild(0);
            ASTNode rightNode = astNode.getChild(1);
            //left hand should always be a var
            if(leftNode.getASTNodeType() != ASTNodeType.VAR) {
                throw new CompilerException("Error: Wrong left value");
            }
            if(!variableMap.containsKey(leftNode.getValue())) {
                throw new CompilerException("Error: Unidentified Variable named " + astNode.getValue());
            }
            leftNode.setValue(variableMap.get(leftNode.getValue()));
            expressionAnalyser(rightNode);
        } else if(astNode.getGrammerElement().getTokenType() == TokenType.IDENTIFIER) {
            if(!variableMap.containsValue(astNode.getValue())){
                if(variableMap.containsKey(astNode.getValue())){
                    astNode.setValue(variableMap.get(astNode.getValue()));
                } else {
                    throw new CompilerException("Error: Unidentified Variable named " + astNode.getValue());
                }

            }
        } 
        else if(astNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            expressionAnalyser(astNode);
        }

    }
}
