package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

import java.util.List;

public class Validator implements ValidatorInterface {

    private final Input myInput;
    private ValidationErrorList myErrorList;
    private static final String REGISTER_ERROR = "Invalid register";
    private static final String INSTRUCTION_ERROR = "Instruction does not exist";
    private static final String FORMAT_ERROR = "Instruction format is invalid";
    private static final String LOOP_ERROR = "Loop does not exist";

    public Validator(Input input) {
        this.myInput = input;
    }

    public Validator(List<String> input) {
        this.myInput = new Input(input);
    }

    public Validator(String input) {
        this.myInput = new Input(input);
    }

    // TODO: Implement validation checks
    private boolean validInstruction(String code) {
        return false;
    }

    private boolean validInstructionFormat(String code) {
        return true;
    }

    private boolean validRegisters(String code) {
        return true;
    }

    private boolean validLabel(String code) {
        return true;
    }

    @Override
    public void validateFile() {
        this.myErrorList = new ValidationErrorList();
        List<String> input = this.myInput.getList();
        for (int index = 0; index < input.size(); index ++) {
            String code = input.get(index);

            // Check individual error conditions
            if (!validInstruction(code))
                this.myErrorList.add(new ValidationError(index, code, INSTRUCTION_ERROR));
            if (!validInstructionFormat(code))
                this.myErrorList.add(new ValidationError(index, code, FORMAT_ERROR));
            if (!validRegisters(code))
                this.myErrorList.add(new ValidationError(index, code, REGISTER_ERROR));
            if (!validLabel(code)) {
                this.myErrorList.add(new ValidationError(index, code, LOOP_ERROR));
            }
        }
    }

    @Override
    public boolean hasErrors() {
        return this.myErrorList.size() > 0;
    }

    @Override
    public ValidationErrorList getErrors() {
        return this.myErrorList;
    }
}
