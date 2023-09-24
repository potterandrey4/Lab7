package org.example.commads;

import org.example.RequestReader;
import org.example.authModule.EntryHandler;
import org.example.collection.classes.Worker;
import org.example.io.OutputHandler;
import org.example.tools.CreateWorker;

import static org.example.ResponseSender.sendMsgWithWorker;

public class AddCommand extends Command {

    public AddCommand(String name) {
        super(name);
    }

    @Override
    public void execute(String arg) {execute();}

    @Override
    public void execute() {
        Worker worker = CreateWorker.create();
        worker.setUserId(EntryHandler.getUID());
        sendMsgWithWorker(this.name, worker);
        OutputHandler.println(RequestReader.read());
    }
}
