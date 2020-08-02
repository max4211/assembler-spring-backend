package com.ece350.assembler.exceptions;

public class InstructionException extends RuntimeException {

    public InstructionException(Exception e) {
        super(e.getMessage());
    }

    public InstructionException() {
        super();
    }

}
