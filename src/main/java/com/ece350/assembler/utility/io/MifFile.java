package com.ece350.assembler.utility.io;

import utility.converter.SignExtend;

import java.util.ArrayList;
import java.util.List;

public class MifFile extends OutputFile {

    private static final String SEPARATOR = " : ";
    private static final String LINE_END = ";";
    private static final String FILE_CLOSE = "END;";
    private static final String LINE_GAP = "";
    private static final int NUM_BITS = 4;
    private static final int FINAL_LINE = 4095;

    public MifFile(String path, String outputBase, String digits, Output data) {
        super(path, data);
        this.myExtension = ".mif";
        this.myHeader = new ArrayList<>(List.of(
                "DEPTH = 4096;",
                "WIDTH = " + digits + ";",
                "ADDRESS_RADIX = DEC;",
                "DATA_RADIX = " + outputBase + ";",
                "CONTENT",
                "BEGIN"
        ));
        convertOutputToBase(outputBase, digits);
        addPrefix();
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public MifFile(String outputBase, String digits, Output data) {
        super(data);
        this.myExtension = ".mif";
        this.myHeader = new ArrayList<>(List.of(
                "DEPTH = 4096;",
                "WIDTH = " + digits + ";",
                "ADDRESS_RADIX = DEC;",
                "DATA_RADIX = " + outputBase + ";",
                "CONTENT",
                "BEGIN"
        ));
        convertOutputToBase(outputBase, digits);
        addPrefix();
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    public MifFile(String outputBase, Output data) {
        super(data);
        this.myExtension = ".mif";
        String digits = "32";
        this.myHeader = new ArrayList<>(List.of(
                "DEPTH = 4096;",
                "WIDTH = " + digits + ";",
                "ADDRESS_RADIX = DEC;",
                "DATA_RADIX = " + outputBase + ";",
                "CONTENT",
                "BEGIN"
        ));
        convertOutputToBase(outputBase);
        addPrefix();
        insertHeader(this.myHeader, this.myOutput.getList());
    }

    private void addPrefix() {
        Output output = new Output();
        List<String> list = this.myOutput.getList();
        for (int i = 0; i < list.size(); i ++) {
            String bits = list.get(i);
            String prefix = SignExtend.extend(Integer.toString(i), NUM_BITS);
            String line = createLine(prefix, bits);
            output.add(line);
            output.add(LINE_GAP);
        }
        output = endFile(output, list.size());
        this.myOutput = output;
    }

    private Output endFile(Output output, int size) {
        output.add(insertNops(size));
        output.add(FILE_CLOSE);
        return output;
    }

    private String insertNops(int size) {
        final String OPEN = "[";
        final String MID = "..";
        final String CLOSE = "]";
        String bits = SignExtend.extend("0", 32);
        String start = SignExtend.extend(Integer.toString(size), NUM_BITS);
        String prefix = OPEN + start + MID + FINAL_LINE + CLOSE;
        return createLine(prefix, bits);
    }

    private String createLine(String prefix, String bits) {
        return prefix + SEPARATOR + bits + LINE_END;
    }

}
