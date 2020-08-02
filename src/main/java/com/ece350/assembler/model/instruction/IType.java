package com.ece350.assembler.model.instruction;

import utility.converter.Digits;

public class IType extends Instruction {

    private static final int N = 3;

    public IType(String s, String opcode) {
        super(s, opcode);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.myOpcode);
        sb.append(convertIndex($rd, Digits.REGISTER));
        sb.append(convertIndex($rs, Digits.REGISTER));
        sb.append(convertIndex(N, Digits.N));
        return sb.toString();
    }
}
