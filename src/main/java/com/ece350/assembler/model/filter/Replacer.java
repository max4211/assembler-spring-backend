package com.ece350.assembler.model.filter;

import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.resource.ConfigData;

import java.util.*;


public class Replacer implements ReplaceInterface{

    private final Input myInput;
    private Map<String, Integer> myLabelMap;
    private static final int WORD_SIZE = 1;
    private static final Set<String> OFFSET_INSN =  new HashSet<String>(List.of("bne", "blt", "beq", "bge"));

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
        List<String> output = new ArrayList<>();
        List<String> inputWithFilteredLabels = createLabelMap();
        for (String s: inputWithFilteredLabels) {
            s = filterCommas(s);
            s = filterImmediate(s);
            s = filterRegisters(s);
            s = appendLabels(s, output.size());
            if (notEmpty(s)) {
                output.add(s);
            }
        }
        return new Input(output);
    }

    private List<String> createLabelMap() {
        this.myLabelMap = new HashMap<>();
        List<String> input = this.myInput.getList();
        List<String> inputWithFilteredLabels = new ArrayList<>();
        for (int index = 0; index < input.size(); index ++) {
            String s = input.get(index);
            s = filterLabels(s, inputWithFilteredLabels.size());
            if (notEmpty(s)) {
                inputWithFilteredLabels.add(s);
            }
        }
        return inputWithFilteredLabels;
    }

    private boolean notEmpty(String s) {
        return (s != null && s.length() > 0);
    }

    private String appendLabels(String s, int lineCount) {
        String[] sarr = s.split("\\s+");
        String instruction = sarr[0];
        String last = sarr[sarr.length-1];
        boolean inMap = this.myLabelMap.containsKey(last);
        if (inMap) {
            int labelLine = this.myLabelMap.get(last);
            int immediateValue = labelLine;
            if (OFFSET_INSN.contains(instruction)) {
                int absoluteOffset = labelLine - lineCount - 1;
                immediateValue = absoluteOffset * WORD_SIZE;
            }

            StringBuilder sb = new StringBuilder(s);
            sb.replace(s.indexOf(last), s.length(), String.valueOf(immediateValue));

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
