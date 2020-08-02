package com.ece350.assembler;

import com.ece350.assembler.utility.converter.Converter;

public class MainConverter {

    public static void main(String[] args) {
        String inputValue = "1010101010101010101";
        String inputBase = "BIN";
        String outputBase = "HEX";
        String digits = "7";

        Converter c = new Converter(inputValue, inputBase, outputBase, digits);
        String output = c.execute();

        System.out.println(output);
    }
}
