package com.ece350.assembler.model.instruction;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;
import com.ece350.assembler.utility.resource.ConfigData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testJPositive() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "j 42";
        Input input = new Input(userInput);
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00001000000000000000000000101010"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testJNegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "j -8";
        Input input = new Input(userInput);
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00001111111111111111111111111000"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testJALPositive() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "jal 42";
        Input input = new Input(userInput);
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00011000000000000000000000101010"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testJALNegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "jal -289";
        Input input = new Input(userInput);
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00011111111111111111111011011111"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

}