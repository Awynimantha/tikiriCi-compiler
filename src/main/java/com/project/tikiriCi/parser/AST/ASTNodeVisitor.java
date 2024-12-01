package com.project.tikiriCi.parser.AST;

import java.util.List;

import com.project.tikiriCi.assembly_gen.AASTNode;
import com.project.tikiriCi.assembly_gen.elements.AssFunction;
import com.project.tikiriCi.config.AASTNodeType;
import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.config.Grammar;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.parser.GrammerElement;

public class ASTNodeVisitor {
    private ASTNode astNode;
    private int tmpVarible;

    public ASTNodeVisitor(ASTNode astNode) {
        this.astNode = astNode;
        this.tmpVarible = 0;
    }

    // Implement 
    public AASTNode createProgramNode() {
        return new AASTNode(Grammar.PROGRAM, AASTNodeType.PROGRAM);
    }
    
    public AASTNode createFunctionNode() {
        return new AASTNode(astNode.getGrammerElement(), 
                    AASTNodeType.FUNCTION);
    }

    private AASTNode getTmpVariable() {
        String keyword = "tmp";
        String ret = keyword+(this.tmpVarible);
        this.tmpVarible = this.tmpVarible + 1;
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(ret);
        AASTNode var = new AASTNode(grammerElement, AASTNodeType.VAR);
        return var;
        
    }

    public AASTNode createInstructionNode() {
        AASTNode aastNode = new AASTNode();
        List<ASTNode> nodes = astNode.getChildren();
        GrammerElement grammerElement = nodes.get(0).getGrammerElement();
        if(grammerElement.getTokenType() == ASTNodeType.RETURN) {
            ASTNode astNode = nodes.get(0);
            AASTNode returnValNode = expressionToAAST(aastNode, astNode);
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
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.COMPLEMENT);
            } else if(operator.getTokenType() == TokenType.TILDE) {
                unop = new AASTNode(operator.getGrammerElement(), AASTNodeType.NEGATE);
            }
            AASTNode unary_node = new AASTNode(AASTNodeType.UNARY);
            unary_node.addChildren(unop);
            unary_node.addChildren(src);
            unary_node.addChildren(dst);
            instructionNode.addChildren(unary_node);
            return dst;
        }
        return null;
    }



}
