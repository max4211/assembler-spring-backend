package com.ece350.assembler.exceptions;

public class GeneralParserException extends RuntimeException {

    public GeneralParserException(Exception e) {
        super(e.getMessage());
    }

    public GeneralParserException() {
        super();
    }
}
