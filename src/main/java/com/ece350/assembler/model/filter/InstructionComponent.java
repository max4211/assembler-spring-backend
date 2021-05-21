package com.ece350.assembler.model.filter;

import java.util.Arrays;

/**
 * Class is used to help validate
 * Input is instruction, gives interface for getters various instruction fields
 */
public class InstructionComponent {

    private static final String COLON = ":";
    private static final String COMMA = ",";
    private static final String EMPTY = "";
    private static final String OPEN = "(";
    private static final String CLOSE = ")";
    private static final String SPACE = " ";

    private final String myCode;
    private String myInstruction;
    private String[] myOperands;
    private String myLabel;
    private String myImmediate;

    public InstructionComponent(String code) {
        this.myCode = code;
        code = code.trim();
        code = code.replace(COMMA, EMPTY);

        if (code.contains(COLON)) {
            int index = code.indexOf(COLON);
            String label = code.substring(0, index);
            this.myLabel = label;
            code = code.substring(index+1).trim();
        }

        code = code.replace(OPEN, SPACE).replace(CLOSE, SPACE);
        if (code != null && code.length() > 0) {
            String[] split = code.split("\\s+");
            this.myInstruction = split[0];
            this.myOperands = Arrays.copyOfRange(split, 1, split.length);
        }
    }
    
    public String getCode() {
        return this.myCode;
    }
    
    public String getInstruction() {
        return this.myInstruction;
    }

    
    public String getLabel() {
        return this.myLabel;
    }

    
    public String[] getOperands() {
        return this.myOperands;
    }

    
    public String getImmediate() {
        return this.myImmediate;
    }

    public boolean isOnlyLabel() {
        return this.myLabel != null && this.myLabel.length() > 0 && this.myInstruction == null;
    }
}
