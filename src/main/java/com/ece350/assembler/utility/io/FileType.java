package com.ece350.assembler.utility.io;

public enum FileType {

    MIF("Mif"),
    LOGISM("Logism"),
    TEXT("Text"),
    MEM("Mem");

    private final String myType;

    private FileType(String type) {
        this.myType = type;
    }

    @Override
    public String toString() {
        return this.myType;
    }

}
