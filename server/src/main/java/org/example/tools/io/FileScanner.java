package org.example.tools.io;

import org.example.exceptions.ReadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileScanner {
    public static ArrayList<String> scan(String filePath) throws ReadException {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            OutputHandler.printErr("Файл не найден.");
        }
        return list;
    }
}