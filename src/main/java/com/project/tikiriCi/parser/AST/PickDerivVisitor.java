// package com.project.tikiriCi.parser.AST;

// import java.util.List;

// import com.project.tikiriCi.config.TokenType;
// import com.project.tikiriCi.main.Token;
// import com.project.tikiriCi.parser.Derivation;

// public class PickDerivVisitor {
//     private Token nextToken;
    
//     public PickDerivVisitor(Token nextToken) {
//         this.nextToken = nextToken;
//     }

//     public Derivation pickExpressionDerivation(ASTNode astNode) {
//         Derivation returnDerivation = new Derivation();
//         List<Derivation> nodeDerivations = astNode.getGrammerElement().getDerivation();
//         if(nextToken.getTokenType() == TokenType.INTEGER) {
//             return nodeDerivations.get(0);
//         } else if(nextToken.getTokenType() == TokenType.)
//     }
    
// }
