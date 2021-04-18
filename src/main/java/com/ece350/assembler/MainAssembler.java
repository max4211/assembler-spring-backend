package com.ece350.assembler;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.exceptions.GeneralParserException;
import com.ece350.assembler.exceptions.ValidatorException;
import com.ece350.assembler.exceptions.XMLConfigException;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.model.filter.Filter;
import com.ece350.assembler.model.filter.Replacer;
import com.ece350.assembler.model.filter.ValidationErrorList;
import com.ece350.assembler.model.filter.Validator;
import com.ece350.assembler.spring.Controller;
import com.ece350.assembler.utility.resource.ConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Optional;

public interface MainAssembler {

    Logger LOGGER = LoggerFactory.getLogger(MainAssembler.class);

    static XMLReader createXMLReader(MultipartFile xmlISA) throws GeneralParserException {
        try {
            String fileString = new String(xmlISA.getBytes());
            return new XMLReader(fileString);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new XMLConfigException(e);
        }
    }

    static ByteArrayResource assemble(String fileString, String fileType, String outputBase, Optional<MultipartFile> xmlISA) throws ValidatorException, XMLConfigException {
        String digits = parseDigits(outputBase);
        ISA myISA = ConfigData.getISAData();
        if (xmlISA.isPresent()) {
            MultipartFile xmlFile = xmlISA.get();
            XMLReader xmlReader = createXMLReader(xmlFile);
            ISA extendedISA = xmlReader.getISA();
            myISA.append(extendedISA);
            LOGGER.info(String.format("Loaded %d custom instructions", extendedISA.getISA().size()));
        }
        Assembler myAssembler = new Assembler(myISA);

        // Filter and validate input data
        Input input = new Input(fileString);
        Filter filter = new Filter(input);
        input = filter.filter();

        // Validate file, throw exception if needed
        Validator validator = new Validator(input, myISA);
        validator.validateFile();
        if (validator.hasErrors()) {
            throw new ValidatorException(validator.getErrors());
        }
        Replacer replacer = new Replacer(input);
        input = replacer.replace();
        LOGGER.info(String.format("Assembling file of length %d", input.getList().size()));

        // File validated, final output construction
        Output output = myAssembler.assemble(input);
        return output.writeToFile(fileType, outputBase, digits);
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
