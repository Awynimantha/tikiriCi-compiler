package com.project.tikiriCi.parser.semantic_analyser;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.project.tikiriCi.config.ASTNodeType;
import com.project.tikiriCi.parser.AST.ASTNode;

public class LoopMarker {
  private int currentLoop;
  private ASTNode root; 

  public LoopMarker( ASTNode root) {
    this.currentLoop = 0;
    this.root = root;
  }

  public String generateLoopLabel() {
    return "loop."+currentLoop;
  }

  public void process() {
    traverseTree();
  }

  public void traverseTree() {
    Queue<ASTNode> queue = new LinkedList<>();
    ASTNode astNode = new ASTNode();
    queue.offer(this.root);
    while (!queue.isEmpty()) {
      astNode = queue.poll();
      if(astNode.getASTNodeType() == ASTNodeType.WHILELOOP) {
        this.currentLoop = this.currentLoop + 1; 
        astNode.setValue(generateLoopLabel());
      } else if(astNode.getASTNodeType() == ASTNodeType.CONTINUE ||
      astNode.getASTNodeType() == ASTNodeType.BREAK) {
        ASTNode labelNode = new ASTNode(ASTNodeType.LABEL, true);    
        String label = generateLoopLabel();
        labelNode.setValue(label);
        astNode.addChild(labelNode);
      }
      for (ASTNode node : astNode.getChildren()) {
        queue.offer(node);              
      }
    }
  }
}
