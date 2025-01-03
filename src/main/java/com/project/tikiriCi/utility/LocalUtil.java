package com.project.tikiriCi.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.project.tikiriCi.config.ASMTreeType;
import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.main.TokenValue;
import com.project.tikiriCi.parser.GrammerElement;
import com.project.tikiriCi.parser.ASMT.ASMTNode;
import com.project.tikiriCi.parser.AST.ASTNode;
import com.project.tikiriCi.parser.semantic_analyser.SemanticVariable;

public class LocalUtil {
    public static boolean regexpMatch(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            return true;
        }
        return false;
    }

    public static Token matchAllRegex(String string) {
        Token token = new Token(TokenType.NULL, new TokenValue(TokenType.NULL, string) );
        for (String type: TokenType.TOKENS) {
            if(regexpMatch(type, string)) {
                token = new Token(type, new TokenValue(type, string));
                break;
            }
        }
        return token;
    }

    public static Boolean isSymbol(String string) {
        if(regexpMatch(TokenType.SYMBOL, string)) {
            return true;
        }
        return false;
    }

    public static Token isToken(String currentString ,String nextString) {
        Token token = new Token(TokenType.NULL, new TokenValue(TokenType.NULL, currentString) );
        String type = TokenType.TOKEN;
        String newRegex = "\\b"+currentString+"\\b";
        if(regexpMatch(newRegex, nextString)) {
            token = new Token(type, new TokenValue(type, currentString));
        }
        return token;
    }

    public static Boolean ignoreChar(char i) {
        for (char charVal : TokenType.IGNORE_TOKENS) {
            if(charVal == i) {
                return true;
            }
        }
        return false;
    }

    public static Token peekTokenList(List<Token> tokenList) {
        return tokenList.get(0);
    }

    /**
     * Create assembly statment
     * @param assemblyComps
     * @return
     */
    public static String createAssemblyStatment(String... assemblyComps) {
        String asm = "";
        for(String assComp: assemblyComps) {
            asm = asm.concat(assComp).concat(" ");
        }
        //asm = asm.strip();
        asm = asm.concat("\n");
        return asm;
    }
   
    public static Boolean isInvalidMov(ASMTNode asmtNode){
        ASMTNode firstOp = asmtNode.getChild(0);
        ASMTNode secondOp = asmtNode.getChild(1);
        if(firstOp.getASMTreeType() == ASMTreeType.PSEUDO && 
            secondOp.getASMTreeType() == ASMTreeType.PSEUDO) {
                return true;
        }
        return false;
    }

    public static Boolean isBinaryOp(Token token) {
        String[] tokens = TokenType.BINARY_OPS;
        for (String string : tokens) {
        if(string == token.getTokenType()) {
            return true;
        }
        }
        return false;
    }

    public static ASTNode addChildASTNode(ASTNode astNode, GrammerElement grammerElement) {
        ASTNode newNode = new ASTNode(grammerElement.clone());
        astNode.addChild(newNode);
        return newNode;
    }

    public static HashMap<String, SemanticVariable> removeVariableFromBlock(HashMap<String, 
       SemanticVariable> hashMap) {
       HashMap<String, SemanticVariable> newHashMap = new HashMap<>();
       for (Map.Entry<String, SemanticVariable> entry: hashMap.entrySet()) {
          newHashMap.put(entry.getKey(), entry.getValue().makeNonCurrentBlock()) ;
       }  
       return newHashMap;
    }   
}
