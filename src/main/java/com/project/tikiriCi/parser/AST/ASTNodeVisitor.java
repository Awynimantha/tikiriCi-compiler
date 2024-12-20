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
    private int labelVariable;

    public ASTNodeVisitor() {
        this.tmpVarible = 0;
        this.labelVariable = 0;
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

    private AASTNode getLabelVariable() {
        String keyword = "label.";
        String ret = keyword + (this.labelVariable);
        this.labelVariable = this.labelVariable + 1;
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(ret);
        AASTNode var = new AASTNode(grammerElement, AASTNodeType.LABEL);
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

    public AASTNode createJumpIfZeroNode(AASTNode value, AASTNode label) {
        AASTNode jumpNode = new AASTNode(AASTNodeType.JUMPIFZERO);
        jumpNode.addChildren(value);
        jumpNode.addChildren(label);
        return jumpNode;
    }

    public AASTNode createJumpNode(AASTNode label) {
        AASTNode jumpNode = new AASTNode(AASTNodeType.JUMP);
        jumpNode.addChildren(label);
        return jumpNode;
    }
    
    public AASTNode createCopyNode(AASTNode src, AASTNode dst) {
        AASTNode copyNode = new AASTNode(AASTNodeType.COPY);
        copyNode.addChildren(src);
        copyNode.addChildren(dst);
        return copyNode;
    }

    public AASTNode createValNode(String value) {
        GrammerElement grammerElement = new GrammerElement();
        grammerElement.setValue(value);
        AASTNode valNode = new AASTNode(grammerElement, AASTNodeType.CONSTANCE);
        return valNode;
    }

    public AASTNode createMovNode(AASTNode src, AASTNode dst) {
        AASTNode moveNode = new AASTNode(AASTNodeType.MOV);
        moveNode.addChildren(src, dst);
        return moveNode;
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
            GrammerElement opeGrammerElement = operator.getGrammerElement();
            unop = new AASTNode(opeGrammerElement, opeGrammerElement.getTokenType());
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
            ASTNode binary_operator = expression.getChild(1);
            ASTNode operator = binary_operator.getChild(0);
            AASTNode binary_node = new AASTNode(AASTNodeType.BINARY);
            if(operator.getTokenType() == TokenType.AND){
                AASTNode label = getLabelVariable();
                AASTNode endLabel = getLabelVariable();
                AASTNode jumpNode1 = createJumpIfZeroNode(v1, label);
                AASTNode jumpNode2 = createJumpIfZeroNode(v2, label);
                AASTNode jumpNode3 = createJumpNode(endLabel);
                AASTNode resultOne = createValNode("1");
                AASTNode resultZero = createValNode("0");
                AASTNode moveOne = createMovNode(resultOne, dst);
                AASTNode moveZero = createMovNode(resultZero, dst);
                instructionNode.addChildren(jumpNode1, jumpNode2, moveOne, jumpNode3, label, moveZero, endLabel);
                return dst;       
            }
            GrammerElement operatorGrammerElement = operator.getGrammerElement();
            binop = new AASTNode(operatorGrammerElement, operatorGrammerElement.getTokenType());
            binary_node.addChildren(binop);
            binary_node.addChildren(v1);
            binary_node.addChildren(v2);
            binary_node.addChildren(dst);
            instructionNode.addChildren(binary_node);
            return dst;

        } else if(firstNode.getASTNodeType() == ASTNodeType.VAR) {
            instructionNode.addChildren();

        } else if(firstNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            return expressionToAAST(instructionNode, firstNode);
        }       
        return null;
    }



}
