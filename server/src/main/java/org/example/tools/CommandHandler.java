package org.example.tools;

import org.example.collection.classes.Worker;
import org.example.exceptions.ReadException;
import org.example.messages.BaseMsg;
import org.example.messages.Msg;
import org.example.messages.MsgWithArg;
import org.example.messages.MsgWithWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
    public static void handle(BaseMsg receivedCommand, CommandManager commandManager) throws ReadException {
        if (receivedCommand instanceof Msg msg) {
            String command = msg.getName();
            int uId = msg.getUid();
            commandManager.execute(command, uId);
        } else if (receivedCommand instanceof MsgWithArg msgWithArg) {
            commandManager.execute(msgWithArg);
        } else if (receivedCommand instanceof MsgWithWorker msgWithWorker) {
            String command = msgWithWorker.getName();
            Worker worker = msgWithWorker.getWorker();
            commandManager.execute(command, worker);
        }
    }
}
