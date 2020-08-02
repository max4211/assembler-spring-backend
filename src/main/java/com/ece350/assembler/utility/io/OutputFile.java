package com.ece350.assembler.utility.io;

import utility.converter.Converter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class OutputFile implements Save {

    protected final String INPUT_BASE = "BIN";
    protected String myPath;
    protected Output myOutput;
    protected String myExtension;
    protected List<String> myHeader;

    public OutputFile(String path, Output data) {
        this.myPath = path;
        this.myOutput = data;
    }

    public OutputFile(Output data) {
        this.myOutput = data;
    }

    @Override
    public void save() {
        try {
            String path = this.myPath + this.myExtension;
            try {
                Files.delete(Paths.get(path));
            } catch (NoSuchFileException e) {
                ;
            }
            Files.write(Paths.get(path),
                    this.myOutput.getList(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> saveFile() {
        return this.myOutput.getList();
    }

    protected void convertOutputToBase(String outputBase, String digits) {
        Output output = new Output();
        for (String s: this.myOutput.getList()) {
            Converter c = new Converter(s, INPUT_BASE, outputBase, digits);
            output.add(c.execute());
        }
        this.myOutput = output;
    }

    protected void convertOutputToBase(String outputBase) {
        Output output = new Output();
        for (String s: this.myOutput.getList()) {
            Converter c = new Converter(s, INPUT_BASE, outputBase);
            output.add(c.execute());
        }
        this.myOutput = output;
    }

    protected void insertHeader(List<String> header, List<String> data) {
        Output output = new Output();
        for (String s: header)
            output.add(s);
        for (String s: data)
            output.add(s);
        this.myOutput = output;
    }

}
