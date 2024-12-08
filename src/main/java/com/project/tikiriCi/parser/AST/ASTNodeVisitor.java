package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.assembly_gen.AASTNode;

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
        if(firstNode.getASTNodeType() == ASTNodeType.INTEGER) {
            AASTNode constance = new AASTNode(firstNode.getGrammerElement(), AASTNodeType.CONSTANCE);
            return constance;

        } else if(firstNode.getASTNodeType() == ASTNodeType.UNOP){    
            AASTNode src = expressionToAAST(instructionNode, expression.getChild(1));
            AASTNode dst = getTmpVariable();
            ASTNode unary_operator = expression.getChild(0);
            ASTNode operator = unary_operator.getChild(0);
            AASTNode unop = new AASTNode();
            if(operator.getTokenType() == TokenType.HYPHONE) {
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.NEGATE);
            } else if(operator.getTokenType() == TokenType.TILDE) {
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.COMPLEMENT);
            }
            AASTNode unary_node = new AASTNode(AASTNodeType.UNARY);
            unary_node.addChildren(unop);
            unary_node.addChildren(src);
            unary_node.addChildren(dst);
            instructionNode.addChildren(unary_node);
            return dst;

        } else if(firstNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            return expressionToAAST(instructionNode, firstNode);
        }
        
        return null;
    }



}
