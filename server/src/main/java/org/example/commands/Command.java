package org.example.commands;

mport org.example.exceptions.ReadException;
import org.example.collection.classes.Worker;
import org.example.messages.MsgWithArg;
import org.example.tools.CommandExecutor;


public class Command {
    CommandExecutor commandExecutor;
    String description;
    String name;

    public Command(CommandExecutor commandExecutor, String description, String name) {
        this.commandExecutor = commandExecutor;
        this.description = description;
        this.name = name;
    }

    public String description(){
        return (name + ": " + description);
    }

    public void execute(){}

    public void execute(String arg) throws ReadException {
        execute();
    }

    public void execute(Worker worker) throws ReadException {}

    public void execute(MsgWithArg msg) throws ReadException {}

}
