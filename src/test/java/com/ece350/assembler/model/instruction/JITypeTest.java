package com.ece350.assembler.model.instruction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JITypeTest {

    @Test
    void testJAL() {
        String inst = "jal 5";
        Instruction jalInst = new JIType(inst, "00011");
        String result = jalInst.execute();
        String expected = "00011000000000000000000000000101";
//        System.out.printf("expected.length() = %d\n", expected.length());
        assertEquals(expected, result);
    }

    @Test
    void testSETX() {
        String inst = "setx 8";
        Instruction instruction = new JIType(inst, "10101");
        String result = instruction.execute();
        String expected = "10101000000000000000000000001000";
//        System.out.printf("expected.length() = %d\n", expected.length());
        assertEquals(expected, result);
    }

    @Test
    void testBEX() {
        String inst = "bex 16";
        Instruction instruction = new JIType(inst, "10110");
        String result = instruction.execute();
        String expected = "10110000000000000000000000010000";
//        System.out.printf("expected.length() = %d\n", expected.length());
        assertEquals(expected, result);
    }

}