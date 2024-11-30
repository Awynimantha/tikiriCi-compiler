package com.project.tikiriCi.exception;

public class LexerException extends Exception {
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexerException(String message) {
        super(message);
    }

    public LexerException(Throwable e){
        super(e.toString());
    }
    
}
