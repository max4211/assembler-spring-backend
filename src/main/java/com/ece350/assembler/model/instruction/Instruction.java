package com.ece350.assembler.model.instruction;

import utility.converter.Converter;
import utility.converter.Digits;

public abstract class Instruction implements InstructionInterface {

    protected final String[] myString;
    protected final String myOpcode;

    private static final String SPACE = " ";
    protected static final int $rd = 1;
    protected static final int $rs = 2;
    protected static final int $rt = 3;

    protected static final String ZEROES = "00";
    protected static final String EMPTY = "00000";

    public Instruction(String s, String opcode) {
        this.myString = s.split("\\s+");
        this.myOpcode = opcode;
    }

    public Instruction(String[] s, String opcode) {
        this.myString = s;
        this.myOpcode = opcode;
    }

    protected String convertIndex(int index, Digits digits) {
        final String inputBase = "DEC";
        final String outputBase = "BIN";
        Converter c = new Converter(this.myString[index], inputBase, outputBase, Integer.toString(digits.getDigits()));
        return c.execute(); // ConverterInterface.intToBinary(this.myString[index], digits);
    }
}
