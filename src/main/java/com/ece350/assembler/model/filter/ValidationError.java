package com.ece350.assembler.model.filter;

public class ValidationError {

    private String myCode;
    private String myError;

    public ValidationError(String code, String error) {
        this.myCode = code;
        this.myError = error;
    }


}
