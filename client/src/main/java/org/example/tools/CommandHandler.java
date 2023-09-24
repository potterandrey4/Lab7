package org.example.tools;

import org.example.RequestReader;
import org.example.ResponseSender;
import org.example.commads.Command;
import org.example.io.OutputHandler;

import java.util.LinkedHashMap;

public class CommandHandler {

    public static void handle (LinkedHashMap<String, Command> commands, CommandManager commandManager, String[] consoleArgs) {
        if (commands.containsKey(consoleArgs[0])) {
            if (consoleArgs.length == 1) {
                commandManager.execute(consoleArgs[0]);
            } else if (consoleArgs.length == 2) {
                commandManager.execute(consoleArgs[0], consoleArgs[1]);
            }
        }
        else {
            if (consoleArgs.length == 1) {
                ResponseSender.sendMsg(consoleArgs[0]);
            } else if (consoleArgs.length == 2) {
                ResponseSender.sendMsgWithArg(consoleArgs[0], consoleArgs[1]);
            }
            OutputHandler.println(RequestReader.read());
        }
    }

}
