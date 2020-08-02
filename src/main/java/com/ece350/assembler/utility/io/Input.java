package com.ece350.assembler.utility.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Input implements Iterable, CustomList{

    private List<String> myInput;

    public Input(String s) {
        this.myInput = new ArrayList<>(List.of(s));
    }

    public Input(List<String> input) {
        this.myInput = input;
    }

    // TODO - implement input construction from a file
    public Input(File file) {
        this.myInput = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine())
                this.myInput.add(scan.nextLine());
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator iterator() {
        return this.myInput.iterator();
    }

    @Override
    public List<String> getList() {
        return this.myInput;
    }
}
