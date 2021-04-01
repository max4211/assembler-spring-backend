package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

import java.util.List;

public interface ValidatorInterface {

    /**
     * Called to validate filtered input for errors
     * @return any validation errors
     */
    List<String> validateFile();

}
