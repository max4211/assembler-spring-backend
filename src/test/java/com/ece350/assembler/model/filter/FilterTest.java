package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    @Test
    void filterTestFull() {
        List<String> inputList = new ArrayList<>(List.of(
                "loop:          ",
                "\n    ",
                "add $t0, $t1,        $t2",
                "      sub $t1, $t2,    $t3",
                "# This is a full line comment",
                "beq $t0, $t1, loop # This is an end of line comment"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "loop:",
                "add $t0, $t1, $t2",
                "sub $t1, $t2, $t3",
                "beq $t0, $t1, loop"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

    @Test
    void filterTestWhitespace() {
        List<String> inputList = new ArrayList<>(List.of(
                "       loop:          "
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "loop:"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

}