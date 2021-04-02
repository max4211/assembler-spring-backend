package com.ece350.assembler;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.exceptions.GeneralParserException;
import com.ece350.assembler.exceptions.ValidatorException;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.model.filter.Filter;
import com.ece350.assembler.model.filter.Replacer;
import com.ece350.assembler.model.filter.ValidationErrorList;
import com.ece350.assembler.model.filter.Validator;
import com.ece350.assembler.utility.resource.ConfigData;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface MainAssembler {

    static ByteArrayResource assemble(String fileString, String fileType, String outputBase, Optional<MultipartFile> xmlISA) throws ValidatorException {
        String digits = parseDigits(outputBase);
        ISA myISA = ConfigData.getISAData();
        // TODO: Append ISA with more information
        Assembler myAssembler = new Assembler(myISA);
        // XMLReader xmlReader = new XMLReader();
        // ISA myISA = reader.getISA();

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
