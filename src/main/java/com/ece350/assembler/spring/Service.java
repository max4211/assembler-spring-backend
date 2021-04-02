package com.ece350.assembler.spring;

import com.amazonaws.util.IOUtils;
import com.ece350.assembler.MainAssembler;
import com.ece350.assembler.exceptions.GeneralParserException;
import com.ece350.assembler.exceptions.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


@org.springframework.stereotype.Service
public class Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

    public ByteArrayResource assembleUserInput(MultipartFile file, Optional<MultipartFile> xmlISA, String type, String base) throws IOException, GeneralParserException, ValidatorException {
        String content = null;
        LOGGER.info("Attempting to assemble user output from service");

        try {
            content = new String(file.getBytes());
            return MainAssembler.assemble(content, type, base, xmlISA);
        } catch (GeneralParserException | IOException | ValidatorException e) {
            LOGGER.debug(String.format("Failed to assemble user output from service due to %s", e.getClass()));
            throw e;
        }
    }

}
