package com.ece350.assembler.model.filter;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.utility.io.Input;

import java.util.List;

public class Validator implements ValidatorInterface {

    private final Input myInput;
    private final ISA myISA;
    private ValidationErrorList myErrorList;
    private static final String REGISTER_ERROR = "Invalid register";
    private static final String INSTRUCTION_ERROR = "Instruction does not exist";
    private static final String FORMAT_ERROR = "Instruction format is invalid";
    private static final String LOOP_ERROR = "Loop does not exist";

    public Validator(Input input, ISA isa) {
        this.myInput = input;
        this.myISA = isa;
    }

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

    private boolean isEmpty(String s) {
        return (s == null || s.length() == 0 || s.matches("\\s+") || s.matches("\n"));
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
            // TODO: Implement label checks
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
