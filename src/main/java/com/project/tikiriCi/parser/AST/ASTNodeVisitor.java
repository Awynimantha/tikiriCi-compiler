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

    public ASTNodeVisitor(ASTNode astNode) {
        this.astNode = astNode;
    }

    // Implement 
    public AASTNode createProgramNode() {
        return new AASTNode(Grammar.PROGRAM, AASTNodeType.PROGRAM);
    }
    
    public AASTNode createFunctionNode() {
        return new AASTNode(astNode.getGrammerElement(), 
                    AASTNodeType.FUNCTION);
    }

    public AASTNode createInstructionNode() {
        AASTNode aastNode = new AASTNode();
        List<ASTNode> nodes = astNode.getChildren();
        GrammerElement grammerElement = nodes.get(0).getGrammerElement();
        if(grammerElement.getTokenType() == ASTNodeType.RETURN) {
            ASTNode astNode = nodes.get(0);
        }
    }

    private void expressionToAAST(AASTNode aastNode, ASTNode expression) {
        ASTNode firstNode = expression.getChild(0);
        if(firstNode.getASTNodeType() == ASTNodeType.INTEGER) {
            aastNode.addChildren(new AASTNode(firstNode.getGrammerElement(), AASTNodeType.CONSTANCE));

        } else if(firstNode.getASTNodeType() == ASTNodeType.UNOP){
            ASTNode unary_operator = expression.getChild(0);
            ASTNode operator = unary_operator.getChild(0);
            if(operator.getTokenType() == TokenType.HYPHONE) {
                aastNode.addChildren(new AASTNode(operator.getGrammerElement(), AASTNodeType.COMPLEMENT));
            } else if(operator.getTokenType() == TokenType.TILDE) {
                aastNode.addChildren(new AASTNode(operator.getGrammerElement(), AASTNodeType.NEGATE));
            }
            expressionToAAST(aastNode, expression.getChild(1));

        }
    }



}
