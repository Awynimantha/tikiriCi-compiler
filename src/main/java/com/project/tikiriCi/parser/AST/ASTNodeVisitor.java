package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.AAST.AASTNode;

public class ASTNodeVisitor {
    private int tmpVarible;

    public ASTNodeVisitor() {
        this.tmpVarible = 0;
    }

    // Implement 
    public AASTNode createProgramNode(ASTNode astNode) {
        return new AASTNode(Grammar.PROGRAM, AASTNodeType.PROGRAM);
    }
    
    public AASTNode createFunctionNode(ASTNode astNode) {
        String functionName = astNode.getChild(0).getValue();
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(functionName);
        return new AASTNode(grammerElement, 
                    AASTNodeType.FUNCTION);
    }

    private AASTNode getTmpVariable() {
        String keyword = "tmp.";
        String ret = keyword + (this.tmpVarible);
        this.tmpVarible = this.tmpVarible + 1;
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(ret);
        AASTNode var = new AASTNode(grammerElement, AASTNodeType.VAR);
        return var;
        
    }

    public AASTNode createInstructionNode(ASTNode astNode) {
        AASTNode aastNode = new AASTNode(AASTNodeType.INSTRUCTION);
        List<ASTNode> nodes = astNode.getChildren();
        GrammerElement grammerElement = nodes.get(0).getGrammerElement();
        if(grammerElement.getName() == ASTNodeType.RETURN) {
            ASTNode expressionNode = astNode.getChild(1); //expression
            AASTNode returnValNode = expressionToAAST(aastNode,expressionNode);
            AASTNode returnNode = new AASTNode(AASTNodeType.RETURN);
            returnNode.addChildren(returnValNode);
            aastNode.addChildren(returnNode);
        }
        return aastNode;
    }

    private AASTNode expressionToAAST(AASTNode instructionNode,ASTNode expression) {
        ASTNode firstNode = expression.getChild(0);
        if(expression.getChildren().size()==1 && firstNode.getASTNodeType() == ASTNodeType.INTEGER) {
            AASTNode constance = new AASTNode(firstNode.getGrammerElement(), AASTNodeType.CONSTANCE);
            return constance;

        } else if(firstNode.getASTNodeType() == ASTNodeType.UNOP){    
            AASTNode src = expressionToAAST(instructionNode, expression.getChild(1));
            AASTNode dst = getTmpVariable();
            ASTNode unary_operator = expression.getChild(0);
            ASTNode operator = unary_operator.getChild(0);
            AASTNode unop = new AASTNode();
            if(operator.getTokenType() == TokenType.SUB) {
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.NEGATE);
            } else if(operator.getTokenType() == TokenType.COMPLEMENT) {
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.COMPLEMENT);
            }
            AASTNode unary_node = new AASTNode(AASTNodeType.UNARY);
            unary_node.addChildren(unop);
            unary_node.addChildren(src);
            unary_node.addChildren(dst);
            instructionNode.addChildren(unary_node);
            return dst;

        } else if(expression.getChildren().size()>1 && expression.getChild(1).getASTNodeType() == ASTNodeType.BINOP){
            AASTNode v1 = expressionToAAST(instructionNode, expression.getChild(0));
            AASTNode v2 = expressionToAAST(instructionNode, expression.getChild(2));
            AASTNode dst = getTmpVariable();
            AASTNode binop = new AASTNode();
            ASTNode unary_operator = expression.getChild(1);
            ASTNode operator = unary_operator.getChild(0);
            if(operator.getTokenType() == TokenType.PLUS) {
                binop = new AASTNode(operator.getGrammerElement(), AASTNodeType.PLUS);
            } else if(operator.getTokenType() == TokenType.MUL) {
                binop = new AASTNode(operator.getGrammerElement(), AASTNodeType.MUL);
            }
            AASTNode binary_node = new AASTNode(AASTNodeType.BINARY);
            binary_node.addChildren(binop);
            binary_node.addChildren(v1);
            binary_node.addChildren(v2);
            binary_node.addChildren(dst);
            instructionNode.addChildren(binary_node);
            return dst;
        } else if(firstNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            return expressionToAAST(instructionNode, firstNode);
        }
        
        
        return null;
    }



}
