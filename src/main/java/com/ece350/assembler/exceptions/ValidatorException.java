package com.ece350.assembler.exceptions;

import com.ece350.assembler.model.filter.ValidationErrorList;

/**
 * Exception is thrown in response to a validator error
 * Signals to service to return correct type of response
 */
public class ValidatorException extends RuntimeException {

    private final ValidationErrorList myErrorList;

    public ValidatorException(ValidationErrorList validationErrorList) {
        super();
        this.myErrorList = validationErrorList;
    }
}
