package com.ece350.assembler.spring;

import com.ece350.assembler.bucket.BucketName;
import com.ece350.assembler.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    public void assembleUserInput(MultipartFile file,
                                String base,
                                String type) {
        System.out.print("GOT A FILE, BASE AND TYPE IN THE SERVICE");
    }

}
