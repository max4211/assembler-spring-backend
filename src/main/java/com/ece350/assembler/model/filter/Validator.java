package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

import java.util.List;

public class Validator implements ValidatorInterface {

    private final Input myInput;

    public Validator(Input input) {
        this.myInput = input;
    }

    public Validator(List<String> input) {
        this.myInput = new Input(input);
    }

    public Validator(String input) {
        this.myInput = new Input(input);
    }

    @Override
    public List<String> validateFile() {
        return null;
    }
}
