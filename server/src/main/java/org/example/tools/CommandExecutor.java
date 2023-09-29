package org.example.tools;

import org.example.ResponseSender;
import org.example.collection.classes.Worker;
import org.example.commands.Command;
import org.example.commands.HistoryCommand;
import org.example.db.WorkerInstructions;
import org.example.messages.MsgWithArg;
import org.example.toolsForCollection.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.db.WorkerInstructions.*;
import static org.example.tools.CheckSizeCollection.checkerSizeCollection;

public class CommandExecutor {

    private final Object collectionLock = new Object();
    static List<Worker> collection;

    List<String> commandsList = new ArrayList<>();

    public CommandExecutor(List<Worker> collection) {
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

        ResponseSender.sendResponse(sb.toString());
    }

    public void exit() {}

    public void add(Worker worker) {
        worker.setCreationDate(LocalDateTime.now());

        int generatedId = addWorker(worker);
        if (generatedId >= 0) {
            worker.setId(generatedId);
            synchronized (collectionLock) {
                collection.add(worker);
            }
            ResponseSender.sendResponse("Worker добавлен");
            commandsList.add("add");
        }
        else {
            ResponseSender.sendResponse("Произошла ошибка, worker не был добавлен");
        }
    }

    public void show() {
        synchronized (collectionLock) {
            ShowCollection.show(collection);
        }
        commandsList.add("show");
    }

    public void clear(int uId) {
        synchronized (collectionLock) {
            if (WorkerInstructions.deleteAllWorkersForUser(uId, collection.size() + 1)) {
                collection = getAllWorkers();
                ResponseSender.sendResponse("Объекты, принадлежащие вам, удалены");
                commandsList.add("clear");
            } else {
                ResponseSender.sendResponse("Ваших объектов в коллекции не найдено");
            }
        }
    }

    public void head() {
        synchronized (collectionLock) {
            if (checkerSizeCollection(collection)) {
                ResponseSender.sendResponse(collection.get(0).toString());
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
        commandsList.add("head");
    }

    public void removeById(MsgWithArg msg) {
        synchronized (collectionLock) {
            if (checkerSizeCollection(collection)) {
                try {
                    int uId = msg.getUid();
                    int id = msg.getIntArg();
                    if (deleteWorker(uId, id)) {
                        collection = RemoveById.remove(id, collection);
                        ResponseSender.sendResponse("Элемент успешно удалён");
                        commandsList.add("remove_by_id");
                    } else {
                        ResponseSender.sendResponse("Элемента с заданным ID не найдено или он принадлежит не вам");
                    }
                } catch (NumberFormatException e) {
                    ResponseSender.sendResponse("Некорректный формат ID");
                }
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
    }

    public void updateById(MsgWithArg msg) {
        synchronized (collectionLock) {
            int id = msg.getIntArg();
            try {
                boolean flag = false;
                if (checkerSizeCollection(collection)) {
                    if (workerExist(id)) {
                        for (Worker worker : collection) {
                            if (Objects.equals(worker.getId(), id)) {
                                flag = true;
                                ResponseSender.sendResponse("true");
                                break;
                            }
                        }
                    }
                }
                if (!flag) {
                    ResponseSender.sendResponse("false");
                }
            } catch (NumberFormatException e) {
                ResponseSender.sendResponse("false");
            }
        }

    }

    public void updateById(Worker worker) {
        synchronized (collectionLock) {
            worker.setCreationDate(LocalDateTime.now());
            if (updateWorker(worker)) {
                collection = RemoveById.remove(worker.getId(), collection);
                collection.add(worker);
                ResponseSender.sendResponse("Объект успешно изменён");
                commandsList.add("update_by_id");
            } else {
                ResponseSender.sendResponse("Произошла ошибка изменения worker'a");
            }
        }
    }

    // значимость статусов сотрудников: regular, probation, hired, fired
    public void printFieldDescendingStatus() {
        synchronized (collectionLock) {
            // Если понимать формулировку "все используемые статусы в порядке убывания"
            if (checkerSizeCollection(collection)) {
                ResponseSender.sendResponse(PrintFieldDescending.print());
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
        commandsList.add("print_field_descending_status");
    }

    public void maxByStatus() {
        synchronized (collectionLock) {
            if (checkerSizeCollection(collection)) {
                ResponseSender.sendResponse(statusSorter().elementAt(0).toString());
                commandsList.add("max_by_status");
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
    }

    public void removeGreater(MsgWithArg msg) {
        synchronized (collectionLock) {
            if (checkerSizeCollection(collection)) {
                try {
                    int uId = msg.getUid();
                    int id = msg.getIntArg();
                    if (removeGreaterWorkers(uId, id)) {
                        collection = getAllWorkers();
                        ResponseSender.sendResponse("Элементы начиная с ID=" + msg.getIntArg() + " и старше удалены");
                        commandsList.add("remove_greater");
                    } else {
                        ResponseSender.sendResponse("Элемента с заданным ID не найдено");
                    }
                } catch (NumberFormatException e) {
                    ResponseSender.sendResponse("Некорректный формат ID");
                }
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
    }

    public void info() {
        synchronized (collectionLock) {
            StringBuilder sb = new StringBuilder();
            sb.append("Информация о коллекции:\n\tТип: LinkedList\n\tКласс объектов: Worker\n");
            sb.append("\tКоличество элементов: ").append(collection.size()).append("\n\t");
            sb.append(LastModifyCollection.getLastTime(collection));

            ResponseSender.sendResponse(sb.toString());
        }
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

        ResponseSender.sendResponse(sb.toString());
    }

    //group_counting_by_name
    public void groupCountingByName() {
        synchronized (collectionLock) {
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
                ResponseSender.sendResponse(sb.toString());
            } else {
                ResponseSender.sendResponse("Коллекция пуста");
            }
        }
        commandsList.add("group_counting_by_name");
    }

}