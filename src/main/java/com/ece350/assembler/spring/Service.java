package com.ece350.assembler.spring;

import com.amazonaws.util.IOUtils;
import com.ece350.assembler.MainAssembler;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


@org.springframework.stereotype.Service
public class Service {

    public ByteArrayResource assembleUserInput(MultipartFile file, String type, String base) {
        String content = null;
        try {
            content = new String(file.getBytes());
            return MainAssembler.assemble(content, type, base);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
