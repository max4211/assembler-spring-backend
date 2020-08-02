package com.ece350.assembler.model.assembler;

import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

public interface AssemblerInterface {

    /**
     * Called by main after Assembler has been created
     * @param input file formatted list/string to assemble
     * @return output in curated format (ready to write)
     */
    Output assemble(Input input);

}
