package org.example.commands;

import org.example.tools.CommandExecutor;

public class ClearCommand extends Command {
    public ClearCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute(int uId) {
        commandExecutor.clear(uId);
    }

}
