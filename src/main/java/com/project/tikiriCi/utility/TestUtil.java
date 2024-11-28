package com.project.tikiriCi.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.project.tikiriCi.main.Token;

public class TestUtil {
    /**
     * Get all the files in a directory
     * @param folderLocation
     * @return
     */
    public static File[] getFiles(String folderLocation) {
        File directory = new File(folderLocation);
        File[] fileList = directory.listFiles();
        return fileList;
    }

    public String tokensToString(List<Token> tokenList) {
        String tokensString = "|";
        for (Token token : tokenList) {
            tokensString = tokensString + token.getTokenType()+"|";
        }
        return tokensString;

    }


}
