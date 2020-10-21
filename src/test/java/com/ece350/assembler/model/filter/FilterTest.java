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
                "loop:",
                "add $t0, $t1, $t2",
                "sub $t1, $t2, $t3",
                "beq $t0, $t1, loop"
        ));
        Input rawInput = new Input(inputList);
        Filter filter = new Filter(rawInput);
        Input filteredInput = filter.filter();

        List<String> expectedOutput = new ArrayList<>(List.of(
                "add 8 9 10",
                "sub 9 10 11",
                "beq 8 9 -8"
        ));

        assertEquals(expectedOutput, filteredInput.getList());
    }

}