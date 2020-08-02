package com.ece350.assembler.utility.io;

import exceptions.ReflectionException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Output implements OutputInterface, CustomList {

    private List<String> myOutput;
    private static final String NEWLINE = "\n";
    private static final String OUTPUTFILE_PATH = "utility.io";
    private static final String FILE_TAG = "File";

    public Output() {
        this.myOutput = new ArrayList<>();
    }


    @Override
    public void add(String s) {
        this.myOutput.add(s);
    }

    @Override
    public void write(FileType type, String path) {
        try {
            Class clazz = Class.forName(createOutputFilePath(type.toString()));
            Constructor ctor = clazz.getConstructor(String.class, Output.class);
            OutputFile file = (OutputFile) ctor.newInstance(path, this);
            file.save();
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    @Override
    public void write(String fileType, String outputBase, String digits, String outputPath) {
        try {
            Class clazz = Class.forName(createOutputFilePath(fileType));
            Constructor ctor = clazz.getConstructor(String.class, String.class, String.class, Output.class);
            OutputFile file = (OutputFile) ctor.newInstance(outputPath, outputBase, digits, this);
            file.save();
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public List<String> write(String fileType, String outputBase, String digits) {
        try {
            Class clazz = Class.forName(createOutputFilePath(fileType));
            Constructor ctor = clazz.getConstructor(String.class, String.class, Output.class);
            OutputFile file = (OutputFile) ctor.newInstance(outputBase, digits, this);
            return file.saveFile();
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public List<String> write(String fileType, String outputBase) {
        try {
            Class clazz = Class.forName(createOutputFilePath(fileType));
            Constructor ctor = clazz.getConstructor(String.class, Output.class);
            OutputFile file = (OutputFile) ctor.newInstance(outputBase, this);
            return file.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReflectionException(e);
        }
    }

    @Override
    public String consoleOut() {
        StringBuilder sb = new StringBuilder();
        for (String s: this.myOutput) {
            sb.append(s + NEWLINE);
        }
        return sb.toString();
    }

    @Override
    public List<String> getList() {
        return this.myOutput;
    }

    private String createOutputFilePath(String fileType) {
        return String.format("%s.%s%s", OUTPUTFILE_PATH, fileType, FILE_TAG);
    }
}
