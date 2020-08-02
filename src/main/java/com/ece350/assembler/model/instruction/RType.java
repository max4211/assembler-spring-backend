package com.ece350.assembler.model.instruction;

import utility.converter.Digits;

public class RType extends Instruction {

    private static final String OPCODE = "00000";
    private static final int $rd = 1;
    private static final int $rs = 2;
    private static final int $rt = 3;

    private static final String SLL = "sll";
    private static final String SRA = "sra";

    private static final String ZEROES = "00";
    private static final String EMPTY = "00000";

    public RType(String s, String opcode) {
        super(s, opcode);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPCODE);
        sb.append(convertIndex($rd, Digits.REGISTER));
        sb.append(convertIndex($rs, Digits.REGISTER));
        if (shiftInstruction()) {
            sb.append(translateShift());
        } else {
            sb.append(translateRegular());
        }
        sb.append(this.myOpcode);
        sb.append(ZEROES);
        return sb.toString();
    }

    private String translateRegular() {
        StringBuilder sb = new StringBuilder();
        sb.append(convertIndex($rt, Digits.REGISTER));
        sb.append(EMPTY);
        return sb.toString();
    }

    private String translateShift() {
        StringBuilder sb = new StringBuilder();
        sb.append(EMPTY);
        sb.append(convertIndex($rt, Digits.REGISTER));
        return sb.toString();
    }

    private boolean shiftInstruction() {
        String inst = this.myString[0];
        return inst.equals(SLL) || inst.equals(SRA);
    }

}
