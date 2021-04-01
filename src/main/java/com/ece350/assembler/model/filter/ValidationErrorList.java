package com.ece350.assembler.model.filter;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorList implements ValidationErrorListInterface {

    private List<ValidationError> myErrors;

    public ValidationErrorList() {
        this.myErrors = new ArrayList<>();
    }


    @Override
    public void add(ValidationError validationError) {
        this.myErrors.add(validationError);
    }

    @Override
    public int size() {
        return this.myErrors.size();
    }
}
