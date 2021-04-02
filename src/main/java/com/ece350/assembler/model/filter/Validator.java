package com.ece350.assembler.model.filter;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.resource.ConfigData;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Validator implements ValidatorInterface {

    private final Input myInput;
    private final ISA myISA;
    private Map<String, String> registerBundle;
    private Set<String> labelMap;
    private ValidationErrorList myErrorList;
    private static final String COLON = ":";
    private static final String IMMEDIATE_REGEX = "-?\\d+";
    private static final String REGISTER_ERROR = "Invalid register";
    private static final String INSTRUCTION_ERROR = "Instruction does not exist";
    private static final String FORMAT_ERROR = "Instruction format is invalid";
    private static final String LOOP_ERROR = "Loop does not exist";

    public Validator(Input input, ISA isa) {
        this.myInput = input;
        this.myISA = isa;
        this.registerBundle = ConfigData.getRegisterMap();
        this.labelMap = createLabelMap();
    }

    private Set<String> createLabelMap() {
        Set<String> set = new HashSet<>();
        for (String s: this.myInput.getList()) {
            if (s.contains(COLON)) {
                String label = s.substring(0, s.indexOf(COLON));
                set.add(label);
            }
        }
        return set;
    }

    /**
     * Verify instruction exists inside of ISA
     * @param instruction parsed instruction from component
     * @return true if it exists in the ISA
     */
    private boolean validInstruction(String instruction) {
        return this.myISA.getPair(instruction) != null;
    }

    // TODO: Improve robustness (currently operand count check)
    private boolean validInstructionFormat(InstructionComponent instructionComponent) {
        String type = this.myISA.getType(instructionComponent.getInstruction());
        String[] operands = instructionComponent.getOperands();
        int totalOperands = operands.length;

        switch(type) {
            case "R":
            case "I":
                return totalOperands == 3;
            case "JI":
            case "JII":
                return totalOperands == 1;
            default:
                return true;
        }
    }

    /**
     * Verify operands are valid (registers, immediate values, label)
     * @param operands list of operands from instruction
     * @return true if all operands are valid
     */
    private boolean validOperands(String[] operands) {
        for (String operand: operands) {
            if (!isImmediate(operand) || !isRegister(operand) || !isLabel(operand)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(String s) {
        return (s == null || s.length() == 0 || s.matches("\\s+") || s.matches("\n"));
    }

    private boolean isImmediate(String operand) {
        return operand.matches(IMMEDIATE_REGEX);
    }

    private boolean isRegister(String operand) {
        return this.registerBundle.containsKey(operand);
    }

    private boolean isLabel(String operand) {
        return this.labelMap.contains(operand);
    }

    @Override
    public void validateFile() {
        this.myErrorList = new ValidationErrorList();
        List<String> input = this.myInput.getList();
        for (int index = 0; index < input.size(); index ++) {
            String code = input.get(index);
            if (!(isEmpty(code))) {
                InstructionComponent instructionComponent = new InstructionComponent(code);

                if (!validInstruction(instructionComponent.getInstruction()))
                    this.myErrorList.add(new ValidationError(index, code, INSTRUCTION_ERROR));
                else { // Instruction has been validated, can check other fields
                    if (!validInstructionFormat(instructionComponent))
                        this.myErrorList.add(new ValidationError(index, code, FORMAT_ERROR));
                    if (!validOperands(instructionComponent.getOperands()))
                        this.myErrorList.add(new ValidationError(index, code, REGISTER_ERROR));
                }
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
