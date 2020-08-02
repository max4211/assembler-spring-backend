package com.ece350.assembler.data.xmlreader;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface XMLGeneratorInterface {

    /**
     * Called within the data portion to create XML DOM from file
     * @param file is the file path to create the game
     * @return a Docuemnt that can be read from directly
     */
    static Document createDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        return doc;
    }

}
