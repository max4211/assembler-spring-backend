package com.ece350.assembler.model.filter;

public class ValidationError {

    private int myLine;
    private String myCode;
    private String myError;

    public ValidationError(int line, String code, String error) {
        this.myCode = code;
        this.myError = error;
        this.myLine = line;
    }

    @Override
    public String toString() {
        return String.format("Line #%d (%s) - %s", this.myLine, this.myCode, this.myError);
    }

}
