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
        ValidationErrorList errorList = new ValidationErrorList();
        List<String> input = this.myInput.getList();
        for (int index = 0; index < input.size(); index ++) {
            String s = input.get(index);

        }
        return null;
    }

    private boolean checkRegisters(String s) {
        return false;
    }

    private boolean checkInstructionExists(String s) {
        return false;
    }

    private boolean checkInstructionFormat(String s) {
        return false;
    }

    private boolean checkLabelExists(String s) {
        return false;
    }

}
