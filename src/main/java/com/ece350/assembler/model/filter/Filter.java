package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.resource.ConfigData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter implements FilterInterface {

    private final Input myInput;
    private Map<String, Integer> myLabelMap;
    private static final int WORD_SIZE = 1;

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
        List<String> partialFilteredInput = filterFormatting();

        for (String s: partialFilteredInput) {
            s = filterRegisters(s);
            s = filterImmediate(s);
            s = appendLabels(s, output.size());
            output.add(s);
        }
        output = filterEmptyLines(output);
        return new Input(output);
    }

    /*
    Filter input comments, whitespace, tabs, and commas for proper formatting for next stage
    Prepare input to be formatted with reigsters, immediates, and labels
    Build out label map as well for final insertion

    Map labels built out from invariant of properly formatted String
    Look for special character (:) and filter out based on this
     */
    private List<String> filterFormatting() {
        List<String> partialFilteredInput = new ArrayList<>();
        List<String> input = this.myInput.getList();
        this.myLabelMap = new HashMap<>();

        for (String s: input) {
            s = filterComments(s);
            s = filterWhitespace(s);
            s = filterTabs(s);
            s = filterCommas(s);
            s = filterLabels(s, partialFilteredInput.size());
//            s = filterRegisters(s);
//            s = filterImmediate(s);
            if (notEmpty(s)) {
                partialFilteredInput.add(s);
            }
        }
        return partialFilteredInput;
    }

    private String appendLabels(String s, int lineCount) {
        String[] sarr = s.split("\\s+");
        String last = sarr[sarr.length-1];
        boolean inMap = this.myLabelMap.containsKey(last);
        if (inMap) {
            int labelLine = this.myLabelMap.get(last);
            int absoluteOffset = labelLine - lineCount - 1;
            int PCOffset = absoluteOffset * WORD_SIZE;
            StringBuilder sb = new StringBuilder(s);
            sb.replace(s.indexOf(last), s.length(), String.valueOf(PCOffset));

//            System.out.printf("appendLabels(%s, %d)\tlast: (%s)\n", s, lineCount, last);
//            System.out.printf("labelLine: (%d)\tabsoluteOffset: (%d)\tPCOffset: (%d)\n", labelLine, absoluteOffset, PCOffset);
//            System.out.printf("updated string: (%s)\n", sb.toString());

            s = sb.toString();
        }

        return s;
    }

    /*
    Filter labels, from built up label map, out of string (if they exist)

    Map labels built out from invariant of properly formatted String
    Look for special character (:) and filter out based on this
    Two cases:
    1. Inline (e.g. "loop: add $r1, $r2, $r3")
    2. Preline (e.g. "loop:\n add $r1, $r2, $r3)
     */
    private String filterLabels(String s, int lineCount) {
        final char SEMICOLON = ':';
        int index = s.indexOf(SEMICOLON);
        if (index > 0) {
            String label = s.substring(0, index);
            label = label.substring(0, label.length());
            this.myLabelMap.put(label, lineCount);
            String sNew = s.substring(index+1);

//            System.out.printf("filterLabls(%s, %d)\t@index: (%d)\tlabel: (%s)\tsNew: (%s)\n", s, lineCount, index, label, sNew);

            s = sNew.trim();
        }

        return s;
    }

    private boolean notEmpty(String s) {
        return (s != null && s.length() > 0);
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

    /*
    * Expected behavior - replace all register names with int value of register
    * */
    private String filterRegisterNames(String input) {
        final String SPACE = " ";
        final Map<String, String> resourceBundle = ConfigData.getRegisterMap();
        StringBuilder sb = new StringBuilder(input);
        String[] split = input.split("\\s+");
        for (String s: split) {
            if (resourceBundle.containsKey(s)) {
                int start = sb.indexOf(s);
                int end = start + s.length();
                sb.replace(start, end, resourceBundle.get(s));
            }
        }
        return sb.toString();
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
