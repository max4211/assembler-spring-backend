package com.ece350.assembler.model.instruction;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.exceptions.ReflectionException;
import com.ece350.assembler.utility.tuple.Pair;

import java.lang.reflect.Constructor;

public class InstructionFactory implements FactoryInterface {

    private static final String INSTRUCTION_PATH = "com.ece350.assembler.model.instruction";
    private static final String TYPE_SUFFX = "Type";
    private static final String SPACE = " ";
    private static final int ZERO = 0;
    private final ISA myISA;

    public InstructionFactory(ISA isa) {
        this.myISA = isa;
    }

    @Override
    public Instruction createInstruction(String input) {
        try {
            String inst = input.split("\\s+")[ZERO];
            Pair pair = this.myISA.getPair(inst);
            Class clazz = Class.forName(createInstructionPath(pair.getType() + TYPE_SUFFX));
            Constructor ctor = clazz.getConstructor(String.class, String.class);
            return (Instruction) ctor.newInstance(input, pair.getCode());
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    private String createInstructionPath(String instruction) {
        String path = String.format("%s.%s", INSTRUCTION_PATH, instruction);
        System.out.printf("instruction path: %s%n", path);
        return path;
    }
}
