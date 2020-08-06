package com.ece350.assembler.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            path = "assemble",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, "text/plain"}
            )
    public void assembleUserInput(@RequestParam("file") MultipartFile file,
                                       @RequestParam("base") String base,
                                       @RequestParam("type") String type) {
        System.out.println("ARRIVED AT POST REQUEST!!");
        myService.assembleUserInput(file, base, type);
    }

}
