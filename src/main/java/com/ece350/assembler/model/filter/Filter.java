package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.resource.BundleInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        List<String> input = this.myInput.getList();
        for (String s: input) {
            s = filterComments(s);
            s = filterWhitespace(s);
            s = filterTabs(s);
            s = filterCommas(s);
            s = filterRegisters(s);
            s = filterImmediate(s);
            output.add(s);
        }
        output = filterEmptyLines(output);
        return new Input(output);
    }

    private List<String> filterEmptyLines(List<String> input) {
        List<String> output = new ArrayList<>();
        for (String s: input) {
            if (!s.isBlank())
                output.add(s);
        }
        return output;
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

    private String filterCommas(String input) {
        final char COMMA = ',';
        StringBuilder sb = new StringBuilder();
        for (char c: input.toCharArray()) {
            if (!(c == COMMA))
                sb.append(c);
        }
        return sb.toString();
    }

    private String filterRegisters(String input) {
        input = filterRegisterNames(input);
        input = filterDollarSign(input);
        return input;
    }

    private String filterDollarSign(String input) {
        final char DOLLAR_SIGN = '$';
        StringBuilder sb = new StringBuilder();
        for (char c: input.toCharArray()) {
            if (!(c == DOLLAR_SIGN))
                sb.append(c);
        }
        return sb.toString();
    }

    private String filterRegisterNames(String input) {
        try {
            final String SPACE = " ";
            final String registerNames = "src/data/MIPS/register.properties";
            final ResourceBundle resourceBundle = BundleInterface.createResourceBundle(registerNames);
            StringBuilder sb = new StringBuilder(input);
            String[] split = input.split(SPACE);
            for (String s: split) {
                if (resourceBundle.containsKey(s)) {
                    int start = sb.indexOf(s);
                    int end = start + s.length();
                    sb.replace(start, end, resourceBundle.getString(s));
                }
            }
            return sb.toString();
        } catch (IOException e) {
            return input;
        }
    }

    // TODO - refactor to regex
    private String filterImmediate(String input) {
//        final String regex = "^[\\w+][\\(][\\w+][\\)]";
        final String SPACE = " ";
        StringBuilder sb = new StringBuilder();
        for (String s: input.split(SPACE)) {
            String[] split = splitAroundParentheses(s);
            for (int i = split.length - 1; i >= 0; i --) {
                sb.append(split[i] + SPACE);
            }
        }
        return sb.substring(0, sb.length()-1);
    }

    private String[] splitAroundParentheses(String input) {
        final String OPEN = "\\(";
        final char CLOSE = ')';
        String[] split = input.split(OPEN);
        try {
            for (int i = 0; i < split.length; i ++) {
                String s = split[i];
                if (s.charAt(s.length()-1) == CLOSE)
                    split[i] = s.substring(0, s.length()-1);
            }
        } catch (Exception e) {
            return split;
        }
        return split;
    }

}
