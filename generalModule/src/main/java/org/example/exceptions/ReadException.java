package org.example.exceptions;

import java.io.FileNotFoundException;

public class ReadException extends FileNotFoundException {
    public ReadException() {
        super("Не удалось прочитать файл");
    }
}
