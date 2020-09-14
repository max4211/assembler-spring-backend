package com.ece350.assembler.utility.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public interface BundleInterface {

    static ResourceBundle createResourceBundle(File filename) throws IOException {
        return new PropertyResourceBundle(new FileInputStream(filename));
    }
}
