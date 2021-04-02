package com.ece350.assembler.model.instruction;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.model.filter.Filter;
import com.ece350.assembler.model.filter.Replacer;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;
import com.ece350.assembler.utility.resource.ConfigData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ITypeTest {

    @Test
    void testADDIPositive() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "addi $0, $21, 42";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00101000001010100000000000101010"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testADDINegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "addi $0, $21, -2";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00101000001010111111111111111110"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testBNEPositive() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "bne $12, $21, 42";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00010011001010100000000000101010"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testBNENegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "bne $12, $21, -32";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00010011001010111111111111100000"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testBLTPositive() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "blt $12, $21, 42";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00110011001010100000000000101010"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testBLTNegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "blt $12, $21, -32";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("00110011001010111111111111100000"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testBEXNegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "bex -8";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("10110111111111111111111111111000"));
        assertEquals(32, expected.get(0).length());
        assertEquals(expected, result);
    }

    @Test
    void testSETXNegative() {
        ISA myISA = ConfigData.getISAData();
        Assembler myAssembler = new Assembler(myISA);

        String userInput = "setx -24\nsetx -21";
        Input input = new Input(userInput);
        Filter filter = new Filter(input);
        input = filter.filter();
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        Output output = myAssembler.assemble(input);

        List<String> result = output.getList();
        List<String> expected = new ArrayList<String>(List.of("10101111111111111111111111101000", "10101111111111111111111111101011"));
        for (String s: expected) {
            assertEquals(32, s.length());
        }
        assertEquals(expected, result);
    }

}