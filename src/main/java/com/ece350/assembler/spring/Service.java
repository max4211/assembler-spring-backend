package com.ece350.assembler.spring;

import com.amazonaws.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@org.springframework.stereotype.Service
public class Service {

    public void assembleUserInput(MultipartFile file) {
        System.out.print("GOT A FILE, BASE AND TYPE IN THE SERVICE");
        String content = null;
        try {
            content = new String(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("File content: %s%n", content);
    }

}
