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
        AASTNode functioNode =  new AASTNode(grammerElement, 
                    AASTNodeType.FUNCTION);
        AASTNode instructionNode =  new AASTNode(AASTNodeType.INSTRUCTION);
        functioNode.addChildren(instructionNode);
        List<ASTNode> astNodes = astNode.getChildren();
        for (ASTNode node : astNodes) {
            if(node.getASTNodeType() == ASTNodeType.BLOCKITEM) {
                ASTNode child = node.getChild(0);
                if(child.getASTNodeType() == ASTNodeType.STATEMENT) {
                    AASTNode outputNode = createStatementNode(child);
                    instructionNode.passChildren(outputNode);
                } else if(child.getASTNodeType() == ASTNodeType.DECLARATION) {
                    if(child.getChildren().size()==2){
                        continue;
                    }
                    AASTNode outputNode = createDeclarationNode(child);
                    instructionNode.passChildren(outputNode);
                }
            }
        }
        return functioNode;
    }

    public AASTNode createStatementNode(ASTNode statementNode) {
        ASTNode firstNode = statementNode.getChild(0);
        AASTNode returnNode = new AASTNode(AASTNodeType.INSTRUCTION);
        if(firstNode.getASTNodeType() == ASTNodeType.RETURN) {
            AASTNode instructioNode = createReturnNode(statementNode);
            returnNode.passChildren(instructioNode);
        } else if(firstNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            expressionToAAST(returnNode, firstNode);
        } else if(firstNode.getASTNodeType() == ASTNodeType.CONDITIONAL) {
            AASTNode instructioNode = conditionalToAAST(firstNode);
            returnNode.passChildren(instructioNode); 
        }
        return returnNode;
    }

    public AASTNode createDeclarationNode(ASTNode declarationNode) {
        AASTNode instructionNode = new AASTNode();
        ASTNode identifierNode = declarationNode.getTerminalChildByASTNodeType(TokenType.IDENTIFIER);
        ASTNode expressionNode = declarationNode.getNonTerminalChildByASTNodeType(ASTNodeType.EXPRESSION);
        AASTNode var = new AASTNode(identifierNode.getGrammerElement(), AASTNodeType.VAR);
        AASTNode resultNode = expressionToAAST(instructionNode, expressionNode);
        AASTNode copyNode = createCopyNode(resultNode, var);
        instructionNode.addChildren(copyNode);
        return instructionNode;
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
        AASTNode var = new AASTNode(grammerElement, AASTNodeType.LABEL_NAME);
        return var;
        
    }
    
    public AASTNode createReturnNode(ASTNode statementNode) {
        AASTNode aastNode = new AASTNode(AASTNodeType.INSTRUCTION);
        List<ASTNode> nodes = statementNode.getChildren();
        GrammerElement grammerElement = nodes.get(0).getGrammerElement();
        if(grammerElement.getName() == ASTNodeType.RETURN) {
            ASTNode expressionNode = statementNode.getChild(1); //expression
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

    public AASTNode createLabelNode(AASTNode labelNameNode) {
        AASTNode labelNode = new AASTNode(AASTNodeType.LABEL);
        labelNode.addChildren(labelNameNode);
        return labelNode;
    }

    private AASTNode conditionalToAAST(ASTNode conditional) {
        AASTNode instructionNode =  new AASTNode(AASTNodeType.INSTRUCTION);
        ASTNode expressionNode = conditional.getChild(0);
        ASTNode statmentNode  = conditional.getChild(1);

        //check if expression
        AASTNode resultNode = expressionToAAST(instructionNode, expressionNode);
        AASTNode elseLabel = getLabelVariable();
        AASTNode labelNode = createLabelNode(elseLabel);
        AASTNode ifJumpNode = createJumpIfZeroNode(resultNode, elseLabel);
        instructionNode.addChildren(ifJumpNode);
        
        //did not jump perform if
        AASTNode statementAASTNode = createStatementNode(statmentNode);
        instructionNode.passChildren(statementAASTNode);
        AASTNode endLabel = getLabelVariable();
        AASTNode jumpNode = createJumpNode(endLabel);
        instructionNode.addChildren(jumpNode, labelNode);

        if(conditional.getChildren().size()>2) {
            ASTNode elseNode  = conditional.getChild(2);
            ASTNode elseStatementNode = elseNode.getChild(0);
            statementAASTNode = createStatementNode(elseStatementNode);
            instructionNode.passChildren(statementAASTNode);
        }
        instructionNode.addChildren(createLabelNode(endLabel));
        return instructionNode;
    }

    private AASTNode expressionToAAST(AASTNode instructionNode,ASTNode expression) {
        ASTNode firstNode = expression.getChild(0);
        if(expression.getChildren().size()==1 && firstNode.getASTNodeType() == ASTNodeType.INTEGER) {
            AASTNode constance = new AASTNode(firstNode.getGrammerElement(), AASTNodeType.CONSTANCE);
            return constance;

        } else if(firstNode.getASTNodeType() == ASTNodeType.ASSIGNMENT) { 
            //extract the var in expression
            ASTNode identifierNode = firstNode.getChild(0);
            ASTNode varNode = identifierNode.getTerminalChildByASTNodeType(TokenType.IDENTIFIER);
            ASTNode expressionNode = firstNode.getChild(1);
            AASTNode var = new AASTNode(varNode.getGrammerElement(), AASTNodeType.VAR);
            AASTNode resultNode = expressionToAAST(instructionNode, expressionNode);
            AASTNode copyNode = createCopyNode(resultNode, var);
            instructionNode.addChildren(copyNode);
            return instructionNode;


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
                AASTNode labelName = getLabelVariable();
                AASTNode endLabelName = getLabelVariable();
                AASTNode jumpNode1 = createJumpIfZeroNode(v1, labelName);
                AASTNode jumpNode2 = createJumpIfZeroNode(v2, labelName);
                AASTNode jumpNode3 = createJumpNode(endLabelName);
                AASTNode resultOne = createValNode("1");
                AASTNode resultZero = createValNode("0");
                AASTNode moveOne = createMovNode(resultOne, dst);
                AASTNode moveZero = createMovNode(resultZero, dst);
                //for short circuiting
                instructionNode.getChildren().add(1, jumpNode1);
                instructionNode.addChildren(jumpNode2, moveOne, jumpNode3, createLabelNode(labelName),
                    moveZero, createLabelNode(endLabelName));
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
            AASTNode varNode = new AASTNode(firstNode.getGrammerElement(), AASTNodeType.VAR);
            return varNode; 

        } else if(firstNode.getASTNodeType() == ASTNodeType.EXPRESSION) {
            return expressionToAAST(instructionNode, firstNode);
        }       
        return null;
    }

    



}
