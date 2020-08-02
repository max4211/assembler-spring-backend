package com.ece350.assembler.utility.converter;

public enum Digits {

    REGISTER(5),
    N(17),
    T(27),
    LGSIM(8);


    private final int myDigits;

    private Digits(int digits) {
        this.myDigits = digits;
    }

    public int getDigits() {
        return this.myDigits;
    }

}
