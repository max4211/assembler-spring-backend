package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReplacerTest {

    @Test
    void replacerTestBasic() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop:",
                "add $t0, $t1, $t2",
                "sub $t1, $t2, $t3",
                "beq $t0, $t1, loop"
        ));
        Input rawInput = new Input(inputList);
        Replacer replacer = new Replacer(rawInput);
        Input filteredInput = replacer.replace();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "beq 8 9 -3"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void replaceJILabel() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop:",
                "add $t0, $t1, $t2",
                "else: sub $t1, $t2, $t3",
                "jal loop",
                "j else"
        ));
        Input rawInput = new Input(inputList);
        Replacer replacer = new Replacer(rawInput);
        Input filteredInput = replacer.replace();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "jal 0",
                "j 1"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void replacerTestInline() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop: add $t0, $t1, $t2",
                "sub $t1, $t2, $t3",
                "blt $t0, $t1, loop"
        ));
        Input rawInput = new Input(inputList);
        Replacer replacer = new Replacer(rawInput);
        Input filteredInput = replacer.replace();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "blt 8 9 -3"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void replacerTestForward() {
        List<String> inputList = new ArrayList<>(List.of(
                "beq $t0, $t1, loop",
                "sub $t1, $t2, $t3",
                "loop:",
                "add $t0, $t1, $t2"
        ));
        Input rawInput = new Input(inputList);
        Replacer replacer = new Replacer(rawInput);
        Input filteredInput = replacer.replace();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "beq 8 9 1",
                "sub 9 10 11",
                "add 8 9 10"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void replacetestReplacerEmptyLines() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3",
                "sub $2, $4, $5",
                "add $5, $7, $5"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "add 5 7 5"));
        Replacer replacer = new Replacer(input);
        List<String> result = replacer.replace().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void replacetestReplacerComments() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3",
                "sub $2, $4, $5",
                "bne $5, $7, $5"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "bne 5 7 5"));
        Replacer replacer = new Replacer(input);
        List<String> result = replacer.replace().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void replacetestReplacerDollar() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3",
                "sub $2, $4, $5",
                "bne $5, $7, $5"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "bne 5 7 5"));
        Replacer replacer = new Replacer(input);
        List<String> result = replacer.replace().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void replacetestReplacerRegisterNames() {
        List<String> input = new ArrayList<>(List.of(
                "add $t9, $t2, $t1",
                "sub $a0, $a1, $a2",
                "bne $ra, $gp, $fp"));
        List<String> expected = new ArrayList<>(List.of(
                "add 25 10 9",
                "sub 4 5 6",
                "bne 31 28 30"));
        Replacer replacer = new Replacer(input);
        List<String> result = replacer.replace().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void replacetestReplacerImmediate() {
        List<String> input = new ArrayList<>(List.of(
                "sw $0, 14($1)",
                "lw $0, 19($5)"));
        List<String> expected = new ArrayList<>(List.of(
                "sw 0 1 14",
                "lw 0 5 19"));
        Replacer replacer = new Replacer(input);
        List<String> result = replacer.replace().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testTabFilter() {
        String input = "addi $1, $0, 1";
        String expected = "addi 1 0 1";
        Replacer replacer = new Replacer(input);
        String result = replacer.replace().getList().get(0);
        assertEquals(expected, result);
    }

    @Test
    void testNOPFilter() {
        String input = "nop";
        String expected = "nop";
        Replacer replacer = new Replacer(input);
        String result = replacer.replace().getList().get(0);
        assertEquals(expected, result);
    }

    @Test
    void testSpecialImmediate() {
        String input = "lw $r3, 3($r0)";
        String expected = "lw 3 0 3";
        Replacer replacer = new Replacer(input);
        String result = replacer.replace().getList().get(0);
        assertEquals(expected, result);
    }
}