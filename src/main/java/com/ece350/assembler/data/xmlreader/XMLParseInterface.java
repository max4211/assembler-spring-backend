package com.ece350.assembler.data.xmlreader;

import com.ece350.assembler.ISA.ISA;

/**
 * Module that translates XML-defined ISA into Java friendly structures
 */
public interface XMLParseInterface {

    /**
     * Called by main to get ISA
     * @return the ISA (encapsulated List of Triplets)
     */
    ISA getISA();

}
