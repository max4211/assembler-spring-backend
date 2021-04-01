package com.ece350.assembler.model.filter;

public interface ValidationErrorListInterface {

    /**
     * Appends an error to the list of validation errors
     * @param validationError specific error message
     */
    void add(ValidationError validationError);

    /**
     * Gets size of validation errors
     * @return size of list of errors
     */
    int size();
}
