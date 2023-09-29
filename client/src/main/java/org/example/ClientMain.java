package org.example;

import org.example.commads.Command;
import org.example.consoleModule.ReadConsole;
import org.example.tools.CommandHandler;
import org.example.tools.CommandManager;

import java.util.LinkedHashMap;

import static org.example.authModule.EntryHandler.auth;


public class ClientMain {
    public static void main(String[] args) {

        CommandManager commandManager = new CommandManager();
        ClientConnect.connect();
        LinkedHashMap<String, Command> commands = commandManager.getCommands();

        auth();

        while (true) {

            String consoleString = ReadConsole.read();
            String[] consoleArgs = consoleString.split(" ");

            CommandHandler.handle (commands, commandManager, consoleArgs);

        }
    }
}
