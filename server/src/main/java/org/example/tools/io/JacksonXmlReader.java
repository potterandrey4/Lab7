package org.example.tools.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.collection.classes.Worker;
import org.example.tools.CheckSameId;
import org.example.tools.GenerateBoolean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class JacksonXmlReader {
    public static LinkedList<Worker> read(XmlMapper xmlMapper, String xmlFile) {
        try {
            File file = new File(xmlFile);
            if (!file.exists()) {
                throw new FileNotFoundException("Файл не найден: " + xmlFile);
            } else {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                if (content.isBlank()) {
                    return new LinkedList<>();
                }
            }
            LinkedList<Worker> workers = xmlMapper.readValue(file, new TypeReference<LinkedList<Worker>>() {});
            if (CheckSameId.check(workers)) {
                return workers;
            }
            else {
                OutputHandler.printErr("В файле есть элементы с одинаковым ID. Поправьте его так, чтобы все ID были уникальны");
                System.exit(-1);
            }

        } catch(IOException e){
            OutputHandler.printErr("Не удалось открыть файл. Проверьте корректность пути, права доступа, корректность содержимого файла");
            OutputHandler.printErr("Вы можете запустить программу с новой коллекцией (введите yes) или выйти и исправить это (введите no)");
            boolean yn = GenerateBoolean.yesNo();
            if (!yn) {
                System.exit(0);
            } else {
                return new LinkedList<>();
            }
        }
        return new LinkedList<>();
    }
}
