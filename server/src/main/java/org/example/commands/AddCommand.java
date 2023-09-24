package org.example.commands;

import org.example.collection.classes.Worker;
import org.example.tools.CommandExecutor;

public class AddCommand extends Command {

    public AddCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute(Worker worker) {
        commandExecutor.add(worker);
    }
}
