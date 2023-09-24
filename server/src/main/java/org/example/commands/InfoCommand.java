package org.example.commands;

import org.example.tools.CommandExecutor;

public class InfoCommand extends Command {

    public InfoCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute() {
        commandExecutor.info();
    }
    
}
