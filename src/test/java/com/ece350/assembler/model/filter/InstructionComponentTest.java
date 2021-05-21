package com.ece350.assembler.model.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionComponentTest {

    @Test
    void testLoopFetch() {
        String instruction = "loop: ";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual = instructionComponent.getLabel();
        String expected = "loop";
        assertEquals(actual, expected);
    }

    @Test
    void testRType() {
        String instruction = "add $r1, $r2, $r3";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "add";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r1", "$r2", "$r3"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testIType() {
        String instruction = "addi $r1, $r2, 12";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "addi";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r1", "$r2", "12"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testJIType() {
        String instruction = "j 42";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "j";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"42"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testJIIType() {
        String instruction = "jr $r12";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "jr";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r12"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testJIITypeLabel() {
        String instruction = "    loop:  jr $r12";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "jr";
        assertEquals(actual, expected);

        actual = instructionComponent.getLabel();
        expected = "loop";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r12"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testRTypeLabel() {
        String instruction = "board:  add $r1, $r2, $r3";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getLabel();
        expected = "board";
        assertEquals(actual, expected);

        actual = instructionComponent.getInstruction();
        expected = "add";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r1", "$r2", "$r3"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testITypeLabel() {
        String instruction = "guy:    addi $r1, $r2, 12";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getLabel();
        expected = "guy";
        assertEquals(actual, expected);

        actual = instructionComponent.getInstruction();
        expected = "addi";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r1", "$r2", "12"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testLoadStoreType() {
        String instruction = "lw $r2, 8($r4)";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getInstruction();
        expected = "lw";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r2", "8", "$r4"};
        assertArrayEquals(actualOperands, expectedOperands);
    }

    @Test
    void testLoadStoreTypeLabel() {
        String instruction = "legend: lw $r2, 8($r4)";
        InstructionComponent instructionComponent = new InstructionComponent(instruction);
        String actual, expected;

        actual = instructionComponent.getLabel();
        expected = "legend";
        assertEquals(actual, expected);

        actual = instructionComponent.getInstruction();
        expected = "lw";
        assertEquals(actual, expected);

        String[] actualOperands = instructionComponent.getOperands();
        String[] expectedOperands = new String[]{"$r2", "8", "$r4"};
        assertArrayEquals(actualOperands, expectedOperands);
    }


}