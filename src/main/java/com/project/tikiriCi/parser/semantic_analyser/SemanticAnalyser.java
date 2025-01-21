package com.project.tikiriCi.parser.semantic_analyser;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.exception.CompilerException;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.utility.LocalUtil;

public class SemanticAnalyser {
    private List<String> parsedVariables;
    private int uniqueIdentifer;

    public SemanticAnalyser() {
        parsedVariables = new ArrayList<String>();
        uniqueIdentifer = 0;
    }

    //create variable <var_name>.<identifier>
    public String createUniqueVar(String value) {
        value = value.split("\\.")[0];
        return value + "." + uniqueIdentifer;
    }
    
    public String getBaseName(String value) {
      value = value.split("\\.")[0];
      return value;     
    }

    public void statementAnalyser(ASTNode semanticNode, HashMap<String, SemanticVariable> variableMap)
    throws CompilerException{
        ASTNode firstChild = semanticNode.getChild(0);
        if(firstChild.getASTNodeType() == ASTNodeType.RETURN) {
            expressionAnalyser(semanticNode.getChild(1), variableMap);     
        } else if(firstChild.getASTNodeType() == ASTNodeType.BLOCK) {
            blockAnalyser(firstChild, LocalUtil.removeVariableFromBlock(variableMap));
        } else if(firstChild.getASTNodeType() == ASTNodeType.CONDITIONAL) {
            expressionAnalyser(firstChild.getChild(0), variableMap);       
            statementAnalyser(firstChild.getChild(1), variableMap);
            if(firstChild.getChildren().size() != 2) {
                System.out.print("----------------------------in--------------------");
                statementAnalyser(firstChild.getChild(2).getChild(0), variableMap);
            }
        } else if(firstChild.getASTNodeType().equals(ASTNodeType.FORLOOP)){


        } else if(firstChild.getASTNodeType().equals(ASTNodeType.WHILELOOP)){
            expressionAnalyser(firstChild.getChild(0), variableMap);    
            statementAnalyser(firstChild.getChild(1), variableMap);
        } else if(firstChild.getASTNodeType().equals(ASTNodeType.EXPRESSION)) {
            expressionAnalyser(firstChild, variableMap);
        }

    }
    
    public void blockAnalyser(ASTNode blockNode, HashMap<String, SemanticVariable> variableMap) 
    throws CompilerException {
        uniqueIdentifer++;
        List<ASTNode> children = blockNode.getChildren();
        for (ASTNode astNode : children) {
            if(astNode.getChild(0).getASTNodeType().equals(ASTNodeType.DECLARATION)) {
                declarationAnalyser(astNode.getChild(0), variableMap);
            } else{
                statementAnalyser(astNode.getChild(0), variableMap);
            }
        }      
    }
    
    public void declarationAnalyser(ASTNode declAstNode, HashMap<String, SemanticVariable> variableMap)
    throws CompilerException{
        //identifier node is the second child
        ASTNode identifierNode =  declAstNode.getTerminalChildByASTNodeType(TokenType.IDENTIFIER);
        String value = identifierNode.getValue();
        if(variableMap.containsKey(value) && variableMap.get(value).isInCurrentBlock()) {
            throw new CompilerException("Error: Duplicate variable declaration of "+value);
        }
        //check if there is expression
        parsedVariables.add(value);
        String newValue = createUniqueVar(value);
        variableMap.put(value, new SemanticVariable(newValue));
        identifierNode.setValue(newValue);
        if(declAstNode.getChildren().size()>3) {
            expressionAnalyser(declAstNode.getNonTerminalChildByASTNodeType(ASTNodeType.EXPRESSION), variableMap);
        } else {
            ASTNode expressionNode = new ASTNode(Grammar.EXP.clone());
            ASTNode integerNode = new ASTNode(Grammar.INT.clone());
            integerNode.setValue("0");
            expressionNode.addChild(integerNode);
            declAstNode.addChild(expressionNode);
        }
    }

    public void expressionAnalyser(ASTNode expNode, HashMap<String, SemanticVariable> variableMap)
    throws CompilerException {
        ASTNode astNode = expNode.getChild(0);
        if(astNode.getASTNodeType() == ASTNodeType.ASSIGNMENT){
            ASTNode leftNode = astNode.getChild(0).getChild(0);
            ASTNode rightNode = astNode.getChild(1).getChild(0);
            //left hand should always be a var
            if(leftNode.getASTNodeType() != ASTNodeType.VAR) {
                throw new CompilerException("Error: Wrong left value");
            }
            //if(!variableMap.containsKey(leftNode.getValue()) && !variableMap.containsValue(new SemanticVariable(leftNode.getValue()))) {
             //       String newValue = createUniqueVar(leftNode.getValue());
              //      variableMap.put(leftNode.getValue(), new SemanticVariable(newValue));
               //     leftNode.setValue(newValue);
            //}
            //m.0 comes here, fix 
            if(variableMap.containsKey(getBaseName(leftNode.getValue()))){
                leftNode.setValue(variableMap.get(leftNode.getValue()).getValue());
            } else {
                throw new CompilerException("Error: Undefined Varibale");
            }
            expressionAnalyser(rightNode, variableMap);
        } else if(astNode.getASTNodeType().equals(ASTNodeType.VAR)) {
            if(variableMap.containsKey(getBaseName(astNode.getValue()))){
                    astNode.setValue(variableMap.get(getBaseName(astNode.getValue())).getValue());
            } else {
              throw new CompilerException("Error: Unidentified Variable named " + astNode.getValue());
            }
        } else if(expNode.getChildren().size() > 1 && expNode.getChild(1).getASTNodeType() == ASTNodeType.BINOP) {
            expressionAnalyser(expNode.getChild(0), variableMap);  
            expressionAnalyser(expNode.getChild(2), variableMap);  
        } else if(astNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            expressionAnalyser(astNode, variableMap);
        }

    }
}
