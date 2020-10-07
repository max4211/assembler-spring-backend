package com.ece350.assembler.model.instruction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ITypeTest {

    @Test
    void testExecute() {
        String inst = "addi 0 1 21";
        Instruction addInst = new IType(inst, "00001");
        String result = addInst.execute();
        String expected = "00001000000000100000000000010101";
//        System.out.printf("expected.length() = %d\n", expected.length());
        assertEquals(expected, result);
    }

}