package com.ece350.assembler.utility.io;

import java.util.ArrayList;
import java.util.List;

public class LogismFile extends OutputFile {

    public LogismFile(String path, String outputBase, String digits, Output data) {
        super(path, data);
        this.myExtension = ".lgsim";
        this.myHeader = new ArrayList<>(List.of("v2.0 raw"));
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public LogismFile(String outputBase, String digits, Output data) {
        super(data);
        this.myExtension = ".lgsim";
        this.myHeader = new ArrayList<>(List.of("v2.0 raw"));
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public LogismFile(String outputBase, Output data) {
        super(data);
        this.myExtension = ".lgsim";
        this.myHeader = new ArrayList<>(List.of("v2.0 raw"));
        convertOutputToBase(outputBase);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

}
