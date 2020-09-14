package com.ece350.assembler;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.model.assembler.Assembler;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.io.FileType;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface MainAssembler {

    static ByteArrayResource assemble(String fileString, String fileType, String outputBase)  {
        try {
            // CODE WHICH WILL BE USER INPUT PARAMETERS
            String ISAfile = "src/main/java/com/ece350/assembler/data/MIPS/ece350ISA.xml";
            String digits = parseDigits(outputBase);

            // MODEL CODE TO GET TO OUTPUT
            XMLReader reader = new XMLReader(ISAfile);
            ISA myISA = reader.getISA();
            Assembler myAssembler = new Assembler(myISA);
            Input input = new Input(fileString);

            // FINAL OUTPUT CONSTRUCTION AND WRITE
            Output output = myAssembler.assemble(input);
            return output.writeToFile(fileType, outputBase, digits);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Could not assemble file");
            e.printStackTrace();
            return null;
        }
    }

    private static String parseDigits(String outputBase) {
        switch(outputBase) {
            case "HEX": return "8";
            case "BIN": return "32";
            case "DEC": return "16";
            default: return "32";
        }
    }

}
