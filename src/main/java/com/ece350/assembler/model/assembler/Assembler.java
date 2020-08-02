package com.ece350.assembler.model.assembler;

import ISA.ISA;
import model.filter.Filter;
import model.instruction.Instruction;
import model.instruction.InstructionFactory;
import utility.io.Input;
import utility.io.Output;

import java.util.Iterator;

public class Assembler implements AssemblerInterface {

    private final ISA myISA;
    private final InstructionFactory myFactory;

    public Assembler(ISA isa) {
        this.myISA = isa;
        this.myFactory = new InstructionFactory(this.myISA);
    }

    @Override
    public Output assemble(Input input) {
        input = new Filter(input).filter();
        Iterator iter = input.iterator();
        Output output = new Output();
        while (iter.hasNext()) {
            String text = (String) iter.next();
            Instruction instruction = this.myFactory.createInstruction(text);
            output.add(instruction.execute());
        }
        return output;
    }
}
