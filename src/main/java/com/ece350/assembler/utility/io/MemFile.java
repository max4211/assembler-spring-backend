package com.ece350.assembler.utility.io;

import java.util.ArrayList;
import java.util.List;

public class MemFile extends OutputFile {

    public MemFile(String path, String outputBase, String digits, Output data) {
        super(path, data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public MemFile(String outputBase, String digits, Output data) {
        super(data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public MemFile(String outputBase, Output data) {
        super(data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase);
        insertHeader(this.myHeader, this.myOutput.getList());
    }

}
