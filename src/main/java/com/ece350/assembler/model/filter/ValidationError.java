package com.ece350.assembler.model.filter;

public class ValidationError {

    private int myLine;
    private String myCode;
    private String myError;
    private String myDisplay;

    public ValidationError(int line, String code, String error, String display) {
        this.myCode = code;
        this.myError = error;
        this.myLine = line;
        this.myDisplay = display;
    }

    @Override
    public String toString() {
        return String.format("#%d [%s] - %s (%s)", this.myLine, this.myCode, this.myError, this.myDisplay);
    }

}
