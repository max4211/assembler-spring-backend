package com.ece350.assembler.spring;

import com.amazonaws.util.IOUtils;
import com.ece350.assembler.MainAssembler;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@org.springframework.stereotype.Service
public class Service {

    public void assembleUserInput(MultipartFile file, String type, String base) {
        System.out.printf("Recieved file in Spring Boot Service %n");
        System.out.printf("File type: %s%nDesired base: %s%n", type, base);
        String content = null;
        try {
            content = new String(file.getBytes());
            MainAssembler.assemble(content, type, base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("SUCCESSFULLY ASSEMBLED FILE IN SERVICE !!! :)");
    }

}
