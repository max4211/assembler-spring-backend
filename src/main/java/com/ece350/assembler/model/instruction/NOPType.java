package com.ece350.assembler.model.instruction;

import com.ece350.assembler.utility.converter.Digits;

public class NOPType extends Instruction {

    private static final String ZERO = "0";

    public NOPType(String s, String opcode) {
        super(s, opcode);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i ++)
            sb.append(ZERO);
        return sb.toString();
    }
}

