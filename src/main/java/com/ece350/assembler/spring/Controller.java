package com.ece350.assembler.spring;

import com.ece350.assembler.exceptions.GeneralParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class Controller {

    private final Service myService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String ERROR_EXTENSION = "err";

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
        String filePrefix = getFilePrefix(file.getOriginalFilename());

        try {
            LOGGER.info("Inside Controller.java, attempting to assemble file");
            ByteArrayResource resource = myService.assembleUserInput(file, type, base);
            HttpHeaders header = createHeaders(filePrefix, type);

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (GeneralParserException | IOException e) {
            LOGGER.error("Failed to assemble file, returning bad request");
            String errorMessage = "Testing error message";
            byte[] errorMessageBytes = errorMessage.getBytes();
            ByteArrayResource resource = new ByteArrayResource(errorMessageBytes);
            HttpHeaders header = createHeaders(filePrefix, ERROR_EXTENSION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(header)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
    }

    private static String getFilePrefix(String originalFileName) {
        return originalFileName.substring(0, originalFileName.indexOf("."));
    }

    private static String createFileName(String filePrefix, String extension) {
        StringBuilder sb = new StringBuilder(filePrefix);
        sb.append(".");
        sb.append(extension.toLowerCase());
        return sb.toString();
    }

    private static HttpHeaders createHeaders(String filePrefix, String extension) {
        String fileName = createFileName(filePrefix, extension);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));
        header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        header.add(HttpHeaders.PRAGMA, fileName);
        header.add(HttpHeaders.EXPIRES, "0");
        return header;
    }

}
