package org.example.commands;

import org.example.collection.classes.Worker;
import org.example.messages.MsgWithArg;
import org.example.tools.CommandExecutor;

public class UpdateByIdCommand extends Command {
    public UpdateByIdCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute(MsgWithArg msg) {
        commandExecutor.updateById(msg);
    }

    public void execute(Worker worker) {
        commandExecutor.updateById(worker);
    }

}

