package com.ece350.assembler.exceptions;

public class XMLConfigException extends RuntimeException {

    public XMLConfigException(Exception e) {
        super(e.getMessage());
    }

    public XMLConfigException() {
        super();
    }

    @Override
    public String toString() {
        return String.format("Unable to parse XML Configuration due to\n%s", this.getMessage());
    }
}
