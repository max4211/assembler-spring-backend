package com.ece350.assembler.data.xmlreader;

import com.ece350.assembler.ISA.ISA;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.ece350.assembler.utility.tuple.Triplet;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReader implements XMLGeneratorInterface, XMLParseInterface {

    private static Document myDocument;
    private static final int ZERO = 0;
    private static final String XML_EXTENSION = ".xml";

    private static final String INSTRUCTION_TAG = "Instruction";
    private static final String NAME_TAG = "Name";
    private static final String TYPE_TAG = "Type";
    private static final String CODE_TAG = "Code";

    public XMLReader(File file) throws IOException, SAXException, ParserConfigurationException {
        myDocument = XMLGeneratorInterface.createDocument(file);
    }

    public XMLReader(String rawFile) throws IOException, SAXException, ParserConfigurationException {
        myDocument = XMLGeneratorInterface.createDocument(rawFile);
    }

//    public XMLReader(String file) throws IOException, SAXException, ParserConfigurationException {
//        myDocument = XMLGeneratorInterface.createDocument(new File(file));
//    }

    // Empty constructor for mirroring function in Main Assembler
    public XMLReader() throws IOException, SAXException, ParserConfigurationException{

    }

    @Override
    public ISA getISA() {
        List<Triplet> myList = new ArrayList<>();
        NodeList nodeList = myDocument.getElementsByTagName(INSTRUCTION_TAG);
        for (int index = 0; index < nodeList.getLength(); index ++) {
            Node node = nodeList.item(index);
            Element element = (Element) node;
            String name = getElement(element, NAME_TAG);
            String type = getElement(element, TYPE_TAG);
            String code = getElement(element, CODE_TAG);
            myList.add(new Triplet(name, type, code));
        }
        return new ISA(myList);
    }

    private String getElement(Element e, String tag) {
        return e.getElementsByTagName(tag).item(ZERO).getTextContent();
    }
}
