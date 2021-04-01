package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;

import java.util.List;


public class Replacer implements ReplaceInterface{

    private final Input myInput;

    public Replacer(Input input) {
        this.myInput = input;
    }

    public Replacer(List<String> input) {
        this.myInput = new Input(input);
    }

    public Replacer(String input) {
        this.myInput = new Input(input);
    }

    @Override
    public Input replace() {
        return this.myInput;
    }
}
