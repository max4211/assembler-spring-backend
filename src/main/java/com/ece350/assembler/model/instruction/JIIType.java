package com.ece350.assembler.model.instruction;

import com.ece350.assembler.utility.converter.Digits;

public class JIIType extends Instruction {

    private static final String JIIZeroes = "0000000000000000000000";

    public JIIType(String s, String opcode) {
        super(s, opcode);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.myOpcode);
        sb.append(convertIndex($rd, Digits.REGISTER));
        sb.append(JIIZeroes);
        return sb.toString();
    }
}

