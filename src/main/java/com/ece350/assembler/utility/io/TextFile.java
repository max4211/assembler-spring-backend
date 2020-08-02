package com.ece350.assembler.utility.io;

import java.util.ArrayList;

public class TextFile extends OutputFile {

    private static final String EXTENSION = ".txt";

    public TextFile(String outputBase, String digits, Output data) {
        super(data);
        this.myExtension = EXTENSION;
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
    }

    public TextFile(String outputBase, Output data) {
        super(data);
        this.myExtension = EXTENSION;
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase);
    }

}
