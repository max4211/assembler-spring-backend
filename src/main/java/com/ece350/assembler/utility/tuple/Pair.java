package com.ece350.assembler.utility.tuple;

public class Pair implements PairInterface {

    private final String myType;
    private final String myCode;

    public Pair(String type, String code) {
        this.myType = type;
        this.myCode=  code;
    }

    public Pair(Triplet triplet) {
        this.myType = triplet.getType();
        this.myCode=  triplet.getCode();
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
        return "(" + this.myType + ", " + this.myCode + ")";
    }
}
