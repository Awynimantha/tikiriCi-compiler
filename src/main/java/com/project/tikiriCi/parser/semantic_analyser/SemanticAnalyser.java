package com.project.tikiriCi.parser.semantic_analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.parser.AST.ASTNode;

public class SemanticAnalyser {
    private HashMap<String, SemanticVariable> variableMap;
    private List<String> parsedVariables;
    private int uniqueIdentifer;

    public SemanticAnalyser() {
        variableMap = new HashMap<String,SemanticVariable>();
        parsedVariables = new ArrayList<String>();
        uniqueIdentifer = 0;
    }

    //create variable <var_name>.<identifier>
    public String createUniqueVar(String value) {
        return value + "." + uniqueIdentifer;
    }

    public void semanticAnalyser(ASTNode semanticNode) throws CompilerException{
        ASTNode firstChild = semanticNode.getChild(0);
        HashMap<String, SemanticVariable> variableMap =  new HashMap<String, SemanticVariable>();
        if(firstChild.getASTNodeType() == ASTNodeType.RETURN) {
            expressionAnalyser(semanticNode.getChild(1));     
        } else if(firstChild.getASTNodeType() == ASTNodeType.BLOCK) {
            blockAnalyser(firstChild, variableMap);
        }
    }
    
    public void blockAnalyser(ASTNode blockNode, HashMap<String, SemanticVariable> variableMap) throws CompilerException {
        
    }
    
    public void declarationAnalyser(ASTNode declAstNode) throws CompilerException{
        //identifier node is the second child
        ASTNode identifierNode =  declAstNode.getTerminalChildByASTNodeType(TokenType.IDENTIFIER);
        String value = identifierNode.getValue();
        if(variableMap.containsKey(value) && variableMap.get(value).isInCurrentBlock()) {
            throw new CompilerException("Error: Duplicate variable declaration of "+value);
        }
        //check if there is expression
        parsedVariables.add(value);
        if(declAstNode.getChildren().size()>3) {
            //variable is declared
            String newValue = createUniqueVar(value);
            variableMap.put(value, new SemanticVariable(newValue));
            identifierNode.setValue(newValue);
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
            if(!variableMap.containsKey(leftNode.getValue()) && !variableMap.containsValue(new SemanticVariable(leftNode.getValue()))) {
                if(parsedVariables.contains(leftNode.getValue())) {
                    String newValue = createUniqueVar(leftNode.getValue());
                    variableMap.put(leftNode.getValue(), new SemanticVariable(newValue));
                    leftNode.setValue(newValue);
                } else {
                    throw new CompilerException("Error: Unidentified Variable this named " + astNode.getValue());
                }
            }
            //m.0 comes here, fix 
            if(!variableMap.containsValue(new SemanticVariable(leftNode.getValue()))){
                leftNode.setValue(variableMap.get(leftNode.getValue()).getValue());
            }
            expressionAnalyser(rightNode);
        } else if(astNode.getGrammerElement().getTokenType() == TokenType.IDENTIFIER) {
            if(!variableMap.containsValue(new SemanticVariable(astNode.getValue()))){
                if(variableMap.containsKey(astNode.getValue())){
                    astNode.setValue(variableMap.get(astNode.getValue()).getValue());
                } else {
                    if(!parsedVariables.contains(astNode.getValue())) {
                        throw new CompilerException("Error: Unidentified Variable named " + astNode.getValue());
                    } else {
                        throw new CompilerException("Error: Variable " + astNode.getValue() + " is not initialized");
                    }
                }

            }
        } 
        else if(astNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            expressionAnalyser(astNode);
        }

    }
}
