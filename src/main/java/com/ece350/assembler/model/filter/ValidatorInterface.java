package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

import java.util.List;

public interface ValidatorInterface {

    /**
     * Called to validate filtered input for errors
     * @return any validation errors
     */
    void validateFile();

    /**
     * Check if the file validated has errors
     * @return true if errors exist
     */
    boolean hasErrors();

    /**
     * Fetch error list from the validator
     * @return error list for returning to user
     */
    ValidationErrorList getErrors();

}
