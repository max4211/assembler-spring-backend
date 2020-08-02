package com.ece350.assembler.model.instruction;

import com.ece350.assembler.utility.converter.Digits;

public class JIType extends Instruction {

    private static final int T = 1;

    public JIType(String s, String opcode) {
        super(s, opcode);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.myOpcode);
        sb.append(convertIndex(T, Digits.T));
        return sb.toString();
    }
}

