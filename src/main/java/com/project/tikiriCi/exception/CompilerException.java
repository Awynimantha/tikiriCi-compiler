package com.project.tikiriCi.exception;


public class CompilerException extends Exception {

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(Throwable e){
        super(e.toString());
    }

}