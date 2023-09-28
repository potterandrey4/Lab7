package org.example.commands;


import org.example.tools.CommandExecutor;

public class HelpCommand extends Command {

    public HelpCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute(int uId) {
        commandExecutor.help(uId);
    }
}

