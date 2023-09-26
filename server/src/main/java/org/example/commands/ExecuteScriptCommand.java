package org.example.commands;

import org.example.exceptions.ReadException;
import org.example.tools.CommandExecutor;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute() {
    }

    public void execute(String arg) throws ReadException {
    }
}
