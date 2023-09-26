package org.example.tools;

import org.example.ResponseSender;
import org.example.collection.classes.Worker;
import org.example.commands.*;
import org.example.exceptions.ReadException;
import org.example.messages.MsgWithArg;

import java.util.LinkedHashMap;


public class CommandManager {
    private static final LinkedHashMap<String, Command> commands = new LinkedHashMap<>();

    public CommandManager(CommandExecutor executor) {
        commands.put("add", new AddCommand(executor, "Добавить новый элемент в коллекцию", "add"));
        commands.put("help", new HelpCommand(executor, "Вывести справку по доступным командам", "help"));
        commands.put("exit",new ExitCommand(executor,"Закрыть приложение", "exit"));
        commands.put("show", new ShowCommand(executor, "Вывести все элементы коллекции", "show"));
        commands.put("clear", new ClearCommand(executor, "Удалить все элементы коллекции", "clear"));
        commands.put("head", new HeadCommand(executor, "Вывести первый элемент коллекции", "head"));
        commands.put("remove_by_id", new RemoveByIdCommand(executor, "Удалить элемент по заданному id", "remove_by_id {id}"));
        commands.put("update_by_id", new UpdateByIdCommand(executor, "Обновить данные работника по ID", "update_by_id {id}"));
        commands.put("print_field_descending_status", new PrintFieldDescendingStatusCommand(executor, "Вывести значения поля status всех элементов в порядке убывания", "print_field_descending_status"));
        commands.put("max_by_status", new MaxByStatusCommand(executor, "Вывести случайный объект с максимальным status", "max_by_status"));
        commands.put("info", new InfoCommand(executor, "Вывести информацию о коллекции", "info"));
        commands.put("remove_greater", new RemoveGreaterCommand(executor, "удалить все элементы после указанного (по id)", "remove_greater {id}"));
        commands.put("history", new HistoryCommand(executor, "Вывести 13 последних команд", "history"));
        commands.put("group_counting_by_name", new GroupCountingByNameCommand(executor, "Группирует элементы по имени", "group_counting_by_name"));
        commands.put("execute_script", new ExecuteScriptCommand(executor, "Исполнить скрипт из файла", "execute_script"));
    }

    public void execute(String name) {
        try {
            commands.get(name).execute();
        } catch (NullPointerException e) {
            ResponseSender.sendCommand("Данной команды не найдено");
        }
    }

    public void execute(MsgWithArg msg) throws ReadException {
        try {
            commands.get(msg.getName()).execute(msg);
        } catch (NullPointerException e) {
            ResponseSender.sendCommand("Данной команды не найдено");
        }
    }

//    public void execute(String name, String arg) throws ReadException {
//        try {
//           commands.get(name).execute(arg);
//        } catch (NullPointerException e) {
//            ResponseSender.sendCommand("Данной команды не найдено");
//        }
//    }

    public void execute(String name, Worker worker) throws ReadException {
        try {
            commands.get(name).execute(worker);
        } catch (NullPointerException e) {
            ResponseSender.sendCommand("Данной команды не найдено");
        }
    }

    public static LinkedHashMap<String, Command> getCommands() {
        return commands;
    }
}
