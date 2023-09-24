package org.example.tools;

mport org.example.exceptions.ReadException;
import org.example.commads.*;
import org.example.io.OutputHandler;

import java.util.LinkedHashMap;


public class CommandManager {
    private final LinkedHashMap<String, Command> commands;

    public CommandManager() {
        this.commands = new LinkedHashMap<>();
        commands.put("add", new AddCommand("add"));
        commands.put("update_by_id", new UpdateCommand("update_by_id"));
        commands.put("execute_script", new ExecuteScript("execute_script"));
        commands.put("exit", new ExitCommand("exit"));
    }

    public void execute(String name) {
        try {
            commands.get(name).execute();
        } catch (NullPointerException e) {
            OutputHandler.printErr("Данной команды не найдено");
        }
    }

    public void execute(String name, String arg) {
        try {
            commands.get(name).execute(arg);
        } catch (NullPointerException | ReadException e) {
            OutputHandler.printErr("Данной команды не найдено");
        }
    }

    public LinkedHashMap<String, Command> getCommands() {
        return commands;
    }
}
