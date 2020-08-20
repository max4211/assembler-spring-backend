package com.ece350.assembler.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(
            path = "assemble/{type}/{base}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void generateAssembledOutput(@PathVariable("type") String type, @PathVariable("base") String base,
                                       @RequestParam("file") MultipartFile file) {
        myService.assembleUserInput(file, type, base);
    }

}
