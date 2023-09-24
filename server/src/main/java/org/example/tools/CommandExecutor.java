package org.example.tools;

import org.example.ResponseSender;
import org.example.collection.classes.Worker;
import org.example.commands.Command;
import org.example.commands.HistoryCommand;
import org.example.db.WorkerInstructions;
import org.example.messages.MsgWithArg;
import org.example.tools.io.OutputHandler;
import org.example.toolsForCollection.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.db.WorkerInstructions.*;
import static org.example.tools.CheckSizeCollection.checkerSizeCollection;

public class CommandExecutor {

    static LinkedList<Worker> collection;

    List<String> commandsList = new ArrayList<>();

    public CommandExecutor(LinkedList<Worker> collection) {
        CommandExecutor.collection = collection;
    }

    public static Vector<Worker> statusSorter() {
        StatusOfWorkerComparator comp = new StatusOfWorkerComparator();
        Vector<Worker> list = new Vector<>(collection);
        list.sort(comp);
        return list;
    }

    public void help() {
        StringBuilder sb = new StringBuilder();
        for (Command element : CommandManager.getCommands().values()) {
            sb.append(element.description());
            sb.append("\n");
        }
        commandsList.add("help");

        ResponseSender.sendCommand(sb.toString());
    }

    public void exit() {
    }

    public void add(Worker worker) {
        worker.setCreationDate(LocalDateTime.now());

        collection.add(worker);
        save(worker);
        ResponseSender.sendCommand("Worker добавлен");
        commandsList.add("add");
    }

    public void show() {
        ShowCollection.show(collection);
        commandsList.add("show");
    }

    public void clear(MsgWithArg msg) {
        if (WorkerInstructions.deleteAllWorkersForUser(msg.getIntArg())) {
            collection = getAllWorkers();
            ResponseSender.sendCommand("Объекты, принадлежащие вам, удалены");
            commandsList.add("clear");
        } else {
            ResponseSender.sendCommand("Произошла ошибка удаления ваших объектов");
        }
    }

    public void head() {
        if (checkerSizeCollection(collection)) {
            ResponseSender.sendCommand(collection.getFirst().toString());
        } else {
            ResponseSender.sendCommand("Коллекция пуста");
        }
        commandsList.add("head");
    }

    public void removeById(MsgWithArg msg) {
        try {
            if (checkerSizeCollection(collection)) {
                int lastLengthCollection = collection.size();
                collection = RemoveById.remove(msg.getUid(), msg.getIntArg(), collection);
                if (lastLengthCollection != collection.size()) {
                    deleteWorker(msg.getUid(), msg.getIntArg());
                    ResponseSender.sendCommand("Элемент успешно удалён");
                    commandsList.add("remove_by_id");
                } else {
                    ResponseSender.sendCommand("Элемент с указанным ID не найден");
                }
            } else {
                ResponseSender.sendCommand("Коллекция пуста");
            }
        } catch (NumberFormatException e) {
            ResponseSender.sendCommand("Некорректный формат ID");
        }
    }

    public void updateById(MsgWithArg msg) {
        int id = msg.getIntArg();
        int uId = msg.getUid();
        try {
            boolean flag = false;
            if (checkerSizeCollection(collection)) {
                for (Worker worker : collection) {
                    if (Objects.equals(worker.getId(), id)) {
                        collection = RemoveById.remove(uId, id, collection);
                        save();
                        flag = true;
                        ResponseSender.sendCommand("true");
                        break;
                    }
                }
            }
            if (!flag) {
                ResponseSender.sendCommand("false");
            }
        } catch (NumberFormatException e) {
            ResponseSender.sendCommand("false");
        }

    }

    public void updateById(Worker worker) {
        worker.setCreationDate(LocalDateTime.now());
        collection.add(worker);
        ResponseSender.sendCommand("Объект успешно изменён");
        save();
        commandsList.add("update_by_id");
    }

    // значимость статусов сотрудников: regular, probation, hired, fired
    public void printFieldDescendingStatus() {
        // Если понимать формулировку "все используемые статусы в порядке убывания"
        if (checkerSizeCollection(collection)) {
            ResponseSender.sendCommand(PrintFieldDescending.print());
        } else {
            ResponseSender.sendCommand("Коллекция пуста");
        }

        commandsList.add("print_field_descending_status");
    }

    public void maxByStatus() {
        if (checkerSizeCollection(collection)) {
            ResponseSender.sendCommand(statusSorter().elementAt(0).toString());
            commandsList.add("max_by_status");
        } else {
            ResponseSender.sendCommand("Коллекция пуста");
        }
    }

    public void removeGreater(MsgWithArg msg) {
        if (checkerSizeCollection(collection)) {
            try {
                int lastLengthCollection = collection.size();
                collection = RemoveGreater.remove(msg.getIntArg(), collection);
                if (lastLengthCollection != collection.size()) {
                    ResponseSender.sendCommand("Элементы начиная с ID=" + msg.getIntArg() + " и старше удалены");
                    save();
                    commandsList.add("remove_greater");
                } else {
                    ResponseSender.sendCommand("Элемента с заданным ID не найдено");
                }
            } catch (NumberFormatException e) {
                ResponseSender.sendCommand("Некорректный формат ID");
            }
        } else {
            ResponseSender.sendCommand("Коллекция пуста");
        }
    }

    public void info() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о коллекции:\n\tТип: LinkedList\n\tКласс объектов: Worker\n");
        sb.append("\tКоличество элементов: ").append(collection.size()).append("\n\t");
        sb.append(LastModifyCollection.getLastTime(collection));

        ResponseSender.sendCommand(sb.toString());
        commandsList.add("info");
    }

    public void history() {
        commandsList.add("history");
        StringBuilder sb = new StringBuilder();
        if (commandsList.size() < HistoryCommand.number) {
            sb.append(commandsList);
        } else {
            sb.append(commandsList.subList(commandsList.size() - HistoryCommand.number, commandsList.size()));
        }

        ResponseSender.sendCommand(sb.toString());
    }

    //group_counting_by_name
    public void groupCountingByName() {
        if (checkerSizeCollection(collection)) {
            Stream<Worker> streamWorker = collection.stream();
            Map<String, Long> workerByName = streamWorker.collect(Collectors.groupingBy(
                    Worker::getName,
                    Collectors.counting()
            ));
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Long> item : workerByName.entrySet()) {
                sb.append(item.getKey()).append(" - ").append(item.getValue()).append("\n");
            }
            ResponseSender.sendCommand(sb.toString());
        } else {
            ResponseSender.sendCommand("Коллекция пуста");
        }
        commandsList.add("group_counting_by_name");
    }

    public void save(Worker worker) {
        try {
            if (WorkerInstructions.addWorker(worker.getUserId(), worker)) {
                collection = getAllWorkers();
                commandsList.add("save");
            }
        } catch (NullPointerException e) {
            OutputHandler.printErr(e.getMessage());
        }
    }

    // fictive temp method
    public void save() {
        deleteAllWorkers();
        for (Worker worker : collection) {
            WorkerInstructions.addWorker(worker.getUserId(), worker);
        }
        collection = getAllWorkers();
    }

}