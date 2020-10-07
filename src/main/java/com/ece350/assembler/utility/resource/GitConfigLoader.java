package com.ece350.assembler.utility.resource;

import com.ece350.assembler.ISA.ISA;
import com.ece350.assembler.data.xmlreader.XMLReader;
import com.ece350.assembler.exceptions.GeneralParserException;
import com.ece350.assembler.model.assembler.Assembler;
import com.ece350.assembler.utility.io.FileType;
import com.ece350.assembler.utility.io.Input;
import com.ece350.assembler.utility.io.Output;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public interface GitConfigLoader {

    static final String LINK = "https://raw.githubusercontent.com/max4211/assembler-spring-backend/master/src/main/resources/config/ece350ISA.xml";

    static ISA getISA() throws Throwable {
        String link = LINK;
        URL crunchifyUrl = new URL(link);
        HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
        Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();

        // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
        // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
        for (String header : crunchifyHeader.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                link = crunchifyHeader.get("Location").get(0);
                crunchifyUrl = new URL(link);
                crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
                crunchifyHeader = crunchifyHttp.getHeaderFields();
            }
        }
        InputStream crunchifyStream = crunchifyHttp.getInputStream();
        String crunchifyResponse = crunchifyGetStringFromStream(crunchifyStream);
//        System.out.println(crunchifyResponse);

//        testFile(crunchifyResponse);

        return parseISAFromResponse(crunchifyResponse);
    }

    static ISA parseISAFromResponse(String crunchifyResponse) throws GeneralParserException {
        try {
            XMLReader reader = new XMLReader(crunchifyResponse);
            ISA myISA = reader.getISA();
            return myISA;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            throw new GeneralParserException();
        }
    }

    private static void testFile(String crunchifyResponse) {
        String fileString = "add $1, $2, $3\nsub $3, $4, $5";
        try {
            XMLReader reader = new XMLReader(crunchifyResponse);
            ISA myISA = reader.getISA();
            Assembler myAssembler = new Assembler(myISA);
            Input input = new Input(fileString);

            // FINAL OUTPUT CONSTRUCTION AND WRITE
            Output output = myAssembler.assemble(input);
            output.write(FileType.LOGISM, "src/data");
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    // ConvertStreamToString() Utility - we name it as crunchifyGetStringFromStream()
    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }
}
