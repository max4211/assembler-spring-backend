package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    @Test
    void filterTestBasic() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop:  # this is a comment",
                "add $t0, $t1, $t2 # hahaha comments",
                "sub $t1, $t2, $t3",
                "beq $t0, $t1, loop"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "beq 8 9 -3"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void filterJILabel() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop:  # this is a comment",
                "add $t0, $t1, $t2 # hahaha comments",
                "else: sub $t1, $t2, $t3",
                "jal loop",
                "j else"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "jal 0",
                "j 1"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void filterTestInline() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop: add $t0, $t1, $t2",
                "sub $t1, $t2, $t3",
                "blt $t0, $t1, loop"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "blt 8 9 -3"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void filterTestForward() {
        List<String> inputList = new ArrayList<>(List.of(
                "beq $t0, $t1, loop",
                "sub $t1, $t2, $t3",
                "loop:",
                "add $t0, $t1, $t2"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "beq 8 9 1",
                "sub 9 10 11",
                "add 8 9 10"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void testFilterEmptyLines() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3",
                "sub $2, $4, $5",
                "",
                "\n",
                "add $5, $7, $5"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "add 5 7 5"));
        Filter filter = new Filter(input);
        List<String> result = filter.filter().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testFilterComments() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3 #Hello world",
                "sub $2, $4, $5 #Testing comments",
                "#This is a comment",
                "\n #Please delete me",
                "bne $5, $7, $5 #Please delete me too"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "bne 5 7 5"));
        Filter filter = new Filter(input);
        List<String> result = filter.filter().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testFilterDollar() {
        List<String> input = new ArrayList<>(List.of(
                "add $1, $2, $3 #Hello world",
                "sub $2, $4, $5 #Testing comments",
                "#This is a comment",
                "\n #Please delete me",
                "bne $5, $7, $5 #Please delete me too"));
        List<String> expected = new ArrayList<>(List.of(
                "add 1 2 3",
                "sub 2 4 5",
                "bne 5 7 5"));
        Filter filter = new Filter(input);
        List<String> result = filter.filter().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testFilterRegisterNames() {
        List<String> input = new ArrayList<>(List.of(
                "add $t9, $t2, $t1 #Hello world",
                "sub $a0, $a1, $a2 #Testing comments",
                "#This is a comment",
                "\n #Please delete me",
                "bne $ra, $gp, $fp #Please delete me too"));
        List<String> expected = new ArrayList<>(List.of(
                "add 25 10 9",
                "sub 4 5 6",
                "bne 31 28 30"));
        Filter filter = new Filter(input);
        List<String> result = filter.filter().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testFilterImmediate() {
        List<String> input = new ArrayList<>(List.of(
                "sw $0, 14($1)",
                "lw $0, 19($5)"));
        List<String> expected = new ArrayList<>(List.of(
                "sw 0 1 14",
                "lw 0 5 19"));
        Filter filter = new Filter(input);
        List<String> result = filter.filter().getList();
        for (int i = 0; i < result.size(); i ++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testTabFilter() {
        String input = "addi    $1, $0, 1";
        String expected = "addi 1 0 1";
        Filter filter = new Filter(input);
        String result = filter.filter().getList().get(0);
        assertEquals(expected, result);
    }

    @Test
    void testNOPFilter() {
        String input = "nop";
        String expected = "nop";
        Filter filter = new Filter(input);
        String result = filter.filter().getList().get(0);
        assertEquals(expected, result);
    }

}