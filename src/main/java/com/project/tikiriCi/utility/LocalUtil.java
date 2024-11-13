package com.project.tikiriCi.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalUtil {

    public static boolean regexpmatch(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            return true;
        }
        return false;
    }
    
}
