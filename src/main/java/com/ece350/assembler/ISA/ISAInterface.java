package com.ece350.assembler.ISA;

import utility.tuple.Pair;
import utility.tuple.Triplet;

public interface ISAInterface {

    /**
     * Get pair of instruction (type and code) for instruction factory
     * @param inst is the instruction to search for
     * @return
     */
    Pair getPair(String inst);

    /**
     * List functionality, get elements at an index
     * @param index is the point of element
     * @return triplet corresponding to that element
     */
    Triplet get(int index);

}
