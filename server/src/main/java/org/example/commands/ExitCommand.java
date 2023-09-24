package org.example.commands;


import org.example.tools.CommandExecutor;

public class ExitCommand extends Command {

    public ExitCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute() {
        commandExecutor.exit();
    }
}
