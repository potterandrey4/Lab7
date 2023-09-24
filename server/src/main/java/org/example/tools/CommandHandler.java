package org.example.tools;

mport org.example.exceptions.ReadException;
import org.example.collection.classes.Worker;
import org.example.messages.BaseMsg;
import org.example.messages.Msg;
import org.example.messages.MsgWithArg;
import org.example.messages.MsgWithWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
    public static void handle(BaseMsg receivedCommand, CommandManager commandManager) {
        try {
            if (receivedCommand instanceof Msg) {
                String command = ((Msg) receivedCommand).getName();
                commandManager.execute(command);
            } else if (receivedCommand instanceof MsgWithArg) {
                MsgWithArg msg = (MsgWithArg) receivedCommand;
                commandManager.execute(msg);
            } else if (receivedCommand instanceof MsgWithWorker) {
                String command = ((MsgWithWorker) receivedCommand).getName();
                Worker worker = ((MsgWithWorker) receivedCommand).getWorker();
                commandManager.execute(command, worker);
            }


        } catch (ReadException e) {
            logger.warn("Произошла ошибка при чтении данных");
        }
    }
}
