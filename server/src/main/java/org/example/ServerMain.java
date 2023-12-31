package org.example;

import org.example.authModule.ServerEntryHandler;
import org.example.collection.classes.Worker;
import org.example.db.DatabaseHandler;
import org.example.db.GetCredentials;
import org.example.db.WorkerInstructions;
import org.example.messages.BaseMsg;
import org.example.messages.MsgWithUser;
import org.example.tools.CommandExecutor;
import org.example.tools.CommandHandler;
import org.example.tools.CommandManager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    public static void main(String[] args) {

        // запуск сервера
        ServerConnect.connect();

        // подключение к БД
        GetCredentials tool = new GetCredentials();
        tool.prepareCredentials();
        String username = tool.getUserName();
        String password = tool.getUserPassword();
        //String jdbcURL = "jdbc:postgresql://localhost:5432/studs";      // локаль
        String jdbcURL = "jdbc:postgresql://pg:5432/studs";           // helios
        DatabaseHandler dbHandler = new DatabaseHandler(jdbcURL, username, password);
        dbHandler.connect();

        final ExecutorService processorThreadPool = Executors.newCachedThreadPool(); // Ваш пул для обработки запросов

        //блок, отвечающий за подготовку к работе с коллекцией
        List<Worker> collection = Collections.synchronizedList(WorkerInstructions.getAllWorkers());

        // блок, отвечающий за подготовку к работе с командами
        CommandExecutor commandExecutor = new CommandExecutor(collection);
        CommandManager commandManager = new CommandManager(commandExecutor);

        // работа с командами
        while (true) {
            BaseMsg receivedCommand = RequestReader.readCommand();
            processorThreadPool.submit(() -> {
                if (receivedCommand instanceof MsgWithUser) {
                    ServerEntryHandler entryHandler = new ServerEntryHandler();
                    entryHandler.defineExistUser(((MsgWithUser) receivedCommand).getUser());
                } else {
                    CommandHandler.handle(receivedCommand, commandManager);
                }
            });
        }
    }
}
