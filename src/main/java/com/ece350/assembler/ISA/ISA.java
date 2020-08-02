package com.ece350.assembler.ISA;

import com.ece350.assembler.utility.tuple.Pair;
import com.ece350.assembler.utility.tuple.Triplet;

import java.util.List;

public class ISA implements ISAInterface {

    private final List<Triplet> myISA;

    public ISA (List<Triplet> list) {
        this.myISA = list;
    }

    @Override
    public Pair getPair(String inst) {
        for (Triplet t: this.myISA) {
            if (t.getName().equals(inst)) {
                return new Pair(t.getType(), t.getCode());
            }
        }
        return null;
    }

    @Override
    public Triplet get(int index) {
        return this.myISA.get(index);
    }
}
