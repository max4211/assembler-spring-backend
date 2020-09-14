package com.ece350.assembler.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class Controller {

    private final Service myService;

    @Autowired
    public Controller(Service service) {
        this.myService = service;
    }

    @GetMapping("/test")
    @ResponseBody
    public String helloWorld() {
        return "HelloWorld!";
    }

    @PostMapping(
            path = "assemble/{type}/{base}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Resource> generateAssembledOutput(
            @PathVariable("type") String type, @PathVariable("base") String base,
            @RequestParam("file") MultipartFile file) {
        try {
            ByteArrayResource resource = myService.assembleUserInput(file, type, base);

            String fileName = createFileName(file.getOriginalFilename(), type);
//            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
//                    .filename(fileName)
//                    .build();

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));
            header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            header.add(HttpHeaders.PRAGMA, fileName);
//           header.add(HttpHeaders.PRAGMA, "no-cache");
            header.add(HttpHeaders.EXPIRES, "0");
//            header.add("Custom-Filename", fileName);

            
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String createFileName(String originalFileName, String desiredFileType) {
        String filePrefix = originalFileName.substring(0, originalFileName.indexOf(".s"));
        StringBuilder sb = new StringBuilder();
        sb.append(filePrefix);
        sb.append(".");
        sb.append(desiredFileType.toLowerCase());
        return sb.toString();
    }

}
