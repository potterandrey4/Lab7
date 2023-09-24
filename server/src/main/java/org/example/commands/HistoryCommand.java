package org.example.commands;

import org.example.tools.CommandExecutor;

public class HistoryCommand extends Command{
    public static int number = 13;

    public HistoryCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute() {
        commandExecutor.history();
    }
}
