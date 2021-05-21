package com.ece350.assembler.ISA;

import com.ece350.assembler.utility.tuple.Pair;
import com.ece350.assembler.utility.tuple.Triplet;

import java.util.List;

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

    /**
     * Fetch type of instruction
     * @param inst instruction to get type of
     * @return String representing type
     */
    String getType(String inst);

    /**
     * Optionally augment ISA with a new one
     * @param extendedISA
     */
    void append(ISA extendedISA);

    /**
     * Get full list of triplets for augmenting ISAs
     * @return
     */
    List<Triplet> getISA();

}
