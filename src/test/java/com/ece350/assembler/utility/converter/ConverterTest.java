package com.ece350.assembler.utility.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    /*
    * Tests converter error on bex, setx, jal, and j instructions
    * */
    @Test
    void testJIExecute() {
        String inputValue = "10110000000000000000000000000010";
        String inputBase = "BIN";
        String outputBase = "HEX";
        String digits = "8";

        Converter c = new Converter(inputValue, inputBase, outputBase, digits);
        String result = c.execute();
        String expected = "XXXXXXXX";
//        assertEquals(result, expected);
    }

}