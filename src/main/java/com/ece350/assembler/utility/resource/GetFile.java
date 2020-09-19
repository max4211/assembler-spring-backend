package com.ece350.assembler.utility.resource;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public interface GetFile {

    static File getISAFile() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:config/ece350ISA.xml");
    }

    static File getRegisterFile() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:config/register.properties");
    }

}
