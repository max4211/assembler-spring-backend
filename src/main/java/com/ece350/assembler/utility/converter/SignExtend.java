package com.ece350.assembler.utility.converter;

public interface SignExtend {

    static final String ZERO = "0";

    static String extend(String input, int digits) {
        StringBuilder temp = new StringBuilder();
        int pad = digits - input.length();
        for (int i = 0; i < pad; i ++) {
            temp.append(ZERO);
        }
        return temp.toString() + input;
    }
}
