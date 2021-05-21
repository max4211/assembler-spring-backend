package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.resource.ConfigData;

import java.util.*;

/*
* The purpose of this class is to filter the user inputted instructions of formatting
* Filters applied will affect whitespace, comments, and newlines
* The output of this class will feed into a validator to perform error checking on input
* */
public class Filter implements FilterInterface {

    private final Input myInput;

    public Filter(Input input) {
        this.myInput = input;
    }

    public Filter(List<String> input) {
        this.myInput = new Input(input);
    }

    public Filter(String input) {
        this.myInput = new Input(input);
    }

    @Override
    public Input filter() {
        List<String> output = new ArrayList<>();

        for (String s: this.myInput.getList()) {
            s = filterComments(s);
            s = filterWhitespace(s);
            s = filterTabs(s);
            if (notEmpty(s)) {
                output.add(s);
            }
        }
        return new Input(output);
    }

    private boolean notEmpty(String s) {
        return (s != null && s.length() > 0);
    }

    private String filterComments(String input) {
        final String COMMENT = "#";
        int end = input.indexOf(COMMENT);
        if (end > -1)
            input = input.substring(0, end);
        return input;
    }

    private String filterWhitespace(String input) {
        return input.trim();
    }

    private String filterTabs(String input) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (char c: input.toCharArray()) {
            if (c == ' ') {
                count ++;
                if (count == 1)
                    sb.append(c);
            }
            else {
                sb.append(c);
                count = 0;
            }
        }
//        input.replace("\t", "");
        return sb.toString();
    }

}
