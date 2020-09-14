package com.ece350.assembler.spring;

import com.amazonaws.util.IOUtils;
import com.ece350.assembler.MainAssembler;
import com.ece350.assembler.exceptions.GeneralParserException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


@org.springframework.stereotype.Service
public class Service {

    public ByteArrayResource assembleUserInput(MultipartFile file, String type, String base) throws IOException, GeneralParserException {
        String content = null;
        try {
            content = new String(file.getBytes());
            return MainAssembler.assemble(content, type, base);
        } catch (GeneralParserException e) {
            throw new GeneralParserException(e);
        } catch (IOException e) {
            throw e;
        }
    }

}
