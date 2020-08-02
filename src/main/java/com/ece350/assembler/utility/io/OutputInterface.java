package com.ece350.assembler.utility.io;

public interface OutputInterface {

    /**
     * Appends string to output
     * @param s string to append
     */
    void add(String s);

    /**
     * Create file type based on specification
     * @param type is the type of file
     * @param path is the path to save the file to
     */
    void write(FileType type, String path);

    /**
     * Create a file based on specification (to match web)
     * @param fileType string formatted file type
     * @param outputBase base of digits in the output
     * @param digits digits per entry in output
     * @param outputPath the path of the file to write to
     */
    void write(String fileType, String outputBase, String digits, String outputPath);

    /**
     * Write all lines in output to console
     * @return String of all lines
     */
    String consoleOut();
}
