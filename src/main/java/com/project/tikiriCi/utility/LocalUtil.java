package com.project.tikiriCi.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.tikiriCi.config.TokenType;
import com.project.tikiriCi.main.Token;
import com.project.tikiriCi.main.TokenValue;

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
    
}
