package org.example.commands;

import org.example.ResponseSender;
import org.example.messages.MsgWithArg;
import org.example.tools.CommandExecutor;

public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand(CommandExecutor commandExecutor, String description, String name) {
        super(commandExecutor, description, name);
    }

    public void execute() {
        ResponseSender.sendCommand("Вы не ввели id");
    }

    public void execute(MsgWithArg msg) {
        try {
            Integer.parseInt(msg.getArg());
            commandExecutor.removeById(msg);
        } catch (NumberFormatException e) {
            ResponseSender.sendCommand("Введённый аргумент не является целочисленным");
        }
    }

}
