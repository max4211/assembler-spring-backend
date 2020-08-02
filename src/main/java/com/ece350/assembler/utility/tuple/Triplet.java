package com.ece350.assembler.utility.tuple;

public class Triplet implements PairInterface, NameInterface {

    private final String myName;
    private final String myType;
    private final String myCode;

    /**
     * Create a triplet directly from the given values
     */
    public Triplet(String name, String type, String code) {
        this.myName = name;
        this.myType = type;
        this.myCode=  code;
    }

    @Override
    public String getName() {
        return this.myName;
    }

    @Override
    public String getType() {
        return this.myType;
    }

    @Override
    public String getCode() {
        return this.myCode;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "(" + this.myName + ", " + this.myType + ", " + this.myCode + ")";
    }
}
