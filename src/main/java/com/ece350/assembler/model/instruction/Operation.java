package com.ece350.assembler.model.instruction;

public enum Operation {

    ADD("add", "00000"),
    SUB("sub", "00001"),
    AND("sub", "00001"),
    OR("sub", "00001"),
    SLL("sub", "00001"),
    SRA("sub", "00001");

    private final String myType;
    private final String myBinary;

    private Operation(String type, String bin) {
        this.myType = type;
        this.myBinary = bin;
    }

    public String getType() {
        return this.myType;
    }

    public String getBinary() {
        return this.myBinary;
    }

    }
