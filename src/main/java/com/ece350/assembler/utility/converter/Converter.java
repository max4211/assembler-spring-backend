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
            /* Separate pipelines for negative and positive numbers */
            char extendChar = '0'; String bin;
            if (dec < 0) {
                dec *= -1;
                bin = Integer.toString(dec, this.myOutputBase.getBase());
                bin = twosCompliment(bin);
                extendChar = '1';
            } else {
                bin = Integer.toString(dec, this.myOutputBase.getBase());
                extendChar = '0';
            }
            return signExtend(bin, this.myDigits, extendChar);
        } catch (NumberFormatException e) {
            return this.myInputValue;
        }
    }

    private String onesCompliment(String bin) {
        StringBuilder ones = new StringBuilder();

        for (int i = 0; i < bin.length(); i++) {
            ones.append(flip(bin.charAt(i)));
        }

        return ones.toString();
    }

    private String twosCompliment(String bin) {
        String ones = onesCompliment(bin);
        String twos = "";

        int i = ones.length()-1;
        for (i = ones.length() - 1; i >= 0; i--) {
            if (ones.charAt(i) == '1') {
                twos = "0" + twos;
            } else {
                twos = ones.substring(0, i) + "1" + twos;
                break;
            }
        }
//        twos = flip(bin.charAt(i));

        return twos;
    }

    // Returns '0' for '1' and '1' for '0'
    private char flip(char c) {
        return (c == '0') ? '1' : '0';
    }

    private String signExtend(String input, int digits, char extendChar) {
        StringBuilder temp = new StringBuilder();
        int pad = digits - input.length();
        for (int i = 0; i < pad; i ++)
            temp.append(extendChar);
        return temp.toString() + input;
    }

}
