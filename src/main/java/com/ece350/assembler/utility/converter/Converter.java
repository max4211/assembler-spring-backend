package com.ece350.assembler.utility.converter;

public class Converter implements ConverterInterface {

    private static final int ZERO = 0;

    private final String myInputValue;
    private final Base myInputBase;
    private final Base myOutputBase;
    private int myDigits;

    public Converter(String inputValue, String inputBase, String outputBase, String digits) {
        this.myInputValue = inputValue;
        this.myInputBase = Base.valueOf(inputBase);
        this.myOutputBase = Base.valueOf(outputBase);
        this.myDigits = Integer.parseInt(digits);
    }

    public Converter(String inputValue, String inputBase, String outputBase) {
        this.myInputValue = inputValue;
        this.myInputBase = Base.valueOf(inputBase);
        this.myOutputBase = Base.valueOf(outputBase);
        this.myDigits = 1;
    }

    @Override
    public String execute() {
        try {
            int dec = Integer.parseInt(this.myInputValue, this.myInputBase.getBase());
            String bin = Integer.toString(dec, this.myOutputBase.getBase());
            return signExtend(bin, this.myDigits);
        } catch (NumberFormatException e) {
            return this.myInputValue;
        }
    }

    private String signExtend(String input, int digits) {
        StringBuilder temp = new StringBuilder();
        int pad = digits - input.length();
        for (int i = 0; i < pad; i ++)
            temp.append(ZERO);
        return temp.toString() + input;
    }

}
