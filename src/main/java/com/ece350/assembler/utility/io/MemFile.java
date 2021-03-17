package com.ece350.assembler.utility.io;

import com.ece350.assembler.utility.converter.SignExtend;

import java.util.ArrayList;
import java.util.List;

public class MemFile extends OutputFile {

    private static final int TOTAL_LINES = 4096;

    public MemFile(String path, String outputBase, String digits, Output data) {
        super(path, data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
        endFile();
    }

    public MemFile(String outputBase, String digits, Output data) {
        super(data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase, digits);
        insertHeader(this.myHeader, this.myOutput.getList());
        endFile();
    }

    public MemFile(String outputBase, Output data) {
        super(data);
        this.myExtension = ".mem";
        this.myHeader = new ArrayList<>();
        convertOutputToBase(outputBase);
        insertHeader(this.myHeader, this.myOutput.getList());
        endFile();
    }

    private void endFile() {
        int extendLines = TOTAL_LINES - this.myOutput.getList().size();
        String bits = SignExtend.extend("0", 32);
        for (int i = 0; i < Math.max(extendLines, 0); i ++) {
            this.myOutput.add(bits);
        }
    }

}
