package org.example.commads;

import org.example.io.OutputHandler;

public class ExitCommand extends Command {

    public ExitCommand(String name) {
        super(name);
    }

    @Override
    public void execute(String arg) {
        execute();
    }

    @Override
    public void execute() {
        OutputHandler.println("Завершаемся...");
        System.exit(0);
    }
}
