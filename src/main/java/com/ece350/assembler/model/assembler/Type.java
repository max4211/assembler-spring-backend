package com.ece350.assembler.model.assembler;

public enum Type {
    R("R"),
    I("I"),
    JI("JI"),
    JII("JII");

    private final String myType;

    private Type(String type) {
        this.myType = type;
    }
}
