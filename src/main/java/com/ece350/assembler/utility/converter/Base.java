package com.ece350.assembler.utility.converter;

public enum Base {

    DEC(10),
    BIN(2),
    HEX(16);

    private final int myBase;

    private Base(int base) {
        this.myBase = base;
    }

    public int getBase() {
        return this.myBase;
    }

}
