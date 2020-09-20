package com.ece350.assembler;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.exceptions.GeneralParserException;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.utility.resource.ConfigData;
import org.springframework.core.io.ByteArrayResource;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface MainAssembler {

    static ByteArrayResource assemble(String fileString, String fileType, String outputBase) throws GeneralParserException {
        try {
            // CODE WHICH WILL BE USER INPUT PARAMETERS
            String digits = parseDigits(outputBase);

            // MODEL CODE TO GET TO OUTPUT
            XMLReader xmlReader = new XMLReader();
            ISA myISA = ConfigData.getISAData();
            // ISA myISA = reader.getISA();
            Assembler myAssembler = new Assembler(myISA);
            Input input = new Input(fileString);

            // FINAL OUTPUT CONSTRUCTION AND WRITE
            Output output = myAssembler.assemble(input);
            return output.writeToFile(fileType, outputBase, digits);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new GeneralParserException(e);
        }
    }

    static String parseDigits(String outputBase) {
        switch(outputBase) {
            case "HEX": return "8";
            case "BIN": return "32";
            case "DEC": return "16";
            default: return "32";
        }
    }

}
