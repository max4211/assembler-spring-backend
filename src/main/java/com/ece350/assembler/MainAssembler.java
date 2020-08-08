package com.ece350.assembler;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.model.assembler.Assembler;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.io.FileType;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface MainAssembler {

    static void assemble(String fileString, String fileType, String outputBase)  {
        try {
            // CODE WHICH WILL BE USER INPUT PARAMETERS
            String ISAfile = "src/main/java/com/ece350/assembler/data/MIPS/ece350ISA.xml";
            String outputPath = "src/data/test";
            String digits = "8";

            // MODEL CODE TO GET TO OUTPUT
            XMLReader reader = new XMLReader(ISAfile);
            ISA myISA = reader.getISA();
            Assembler myAssembler = new Assembler(myISA);
            Input input = new Input(fileString);

            // FINAL OUTPUT CONSTRUCTION AND WRITE
            Output output = myAssembler.assemble(input);
            output.write(fileType, outputBase, digits, outputPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Could not assemble file");
            e.printStackTrace();
        }

    }

}
