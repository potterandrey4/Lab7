package org.example.commands;

import org.example.tools.CommandExecutor;

public class HeadCommand extends Command {
    public HeadCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute(int uId) {
        commandExecutor.head(uId);
    }
}

