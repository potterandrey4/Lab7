package org.example.tools.io;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.collection.classes.Worker;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class JacksonXmlWriter {
    public static void write(XmlMapper xmlMapper, String xmlFile, LinkedList<Worker> collection) {
        try {
            xmlMapper.writeValue(new File(xmlFile), collection);
        } catch(IOException e) {
            OutputHandler.printErr("Проверьте наличие и права файла");
        }
    }
}
