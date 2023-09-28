package org.example.commads;

import org.example.RequestReader;
import org.example.ResponseSender;
import org.example.authModule.EntryHandler;
import org.example.collection.classes.Worker;
import org.example.io.OutputHandler;
import org.example.tools.CreateWorker;

public class UpdateCommand extends Command {

    public UpdateCommand(String name) {
        super(name);
    }

    @Override
    public void execute(String id) {
        ResponseSender.sendMsgWithArg("update_by_id", id);
        String flag = null;
        while (flag == null) {
            flag = RequestReader.read();
        }
        if (Boolean.parseBoolean(flag)) {
            Worker worker = CreateWorker.create();
            worker.setId(Integer.parseInt(id));
            worker.setUserId(EntryHandler.getUID());
            ResponseSender.sendMsgWithWorker("update_by_id", worker);
            OutputHandler.println(RequestReader.read());
        } else {
            OutputHandler.printErr("Объекта с ID = \"" + id + "\" не существует");
        }
    }

    @Override
    public void execute() {
        execute("");
    }

}