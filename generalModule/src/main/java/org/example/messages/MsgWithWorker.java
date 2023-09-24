package org.example.messages;

import org.example.collection.classes.Worker;

public class MsgWithWorker extends BaseMsg {
    public MsgWithWorker(String command, Worker worker) {
        this.textMsg = command;
        this.worker = worker;
    }

    public String getName() {
        return textMsg;
    }

    public Worker getWorker() {
        return worker;
    }
}
