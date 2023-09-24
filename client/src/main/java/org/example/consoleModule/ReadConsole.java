package org.example.consoleModule;

import org.example.io.InputHandler;
import org.example.io.OutputHandler;


public class ReadConsole {

    static String command;

    public static String read() {
        OutputHandler.println("Введите команду (чтобы увидеть справку по командам введите help)");
        command = InputHandler.get().trim();
        return command;
    }
}
