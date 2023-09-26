package org.example.commads;


import org.example.RequestReader;
import org.example.ResponseSender;
import org.example.collection.classes.*;
import org.example.exceptions.ReadException;
import org.example.io.OutputHandler;

import java.util.ArrayList;
import java.util.Vector;

import static org.example.io.FileScanner.scan;

public class ExecuteScript extends Command {

    public ExecuteScript(String name) {
        super(name);
    }

    static Vector<String> filePaths = new Vector<>();

    @Override
    public void execute() {
        OutputHandler.printErr("Вы не ввели адрес скрипта");
    }

    @Override
    public void execute(String arg) throws ReadException {
        ArrayList<String> listCommands = scan(arg);
        filePaths.add(arg);
        for (String command : listCommands) {
            String[] st = command.split(" ");
            int m = -1;
            int i = -1;
            if (st.length == 1) {
                m++;
                if (i > -1 && m - i <= 9) {
                    continue;
                } else if (st[0].equals("add")) {
                    i = m;
                    try {
                        Worker worker = workerForScript(listCommands, i);
                        m = i+10;
                        ResponseSender.sendMsgWithWorker("add", worker);
                    } catch (NumberFormatException e) {
                        OutputHandler.println("В файле есть некорректные данные");
                        break;
                    }
                } else {
                    ResponseSender.sendMsg(st[0]);
                }
            } else if (st.length == 2) {
                if (st[0].equals("execute_script")) {
                    if (filePaths.contains(st[1])) {
                        OutputHandler.println("Команда " + st[0] + " " + st[1] + " уже была выполнена, дальнейшее выполнение приведёт к рекурсии");
                    }
                } else if (st[0].equals("update_by_id")) {
                    if (updateForScript(st[1], listCommands, i)) {
                        OutputHandler.println("Worker с id " + st[1] + " обновлён");
                        m = i+10;
                    }
                } else {
                    ResponseSender.sendMsgWithArg(st[0], st[1]);
                }
            }
            OutputHandler.println(RequestReader.read() + "\n");
        }
        filePaths.clear();
    }

    private boolean updateForScript(String id, ArrayList<String> listCommands, int i) {
        ResponseSender.sendMsgWithArg("update_by_id", id);
        Worker worker;
        Boolean flag = null;                // использование НЕпримитива обусловлена проверкой на null (для корректного получения)
        while (flag == null) {
            flag = Boolean.parseBoolean(RequestReader.read());
        }
        if (flag) {
            worker = workerForScript(listCommands, i);
            worker.setId(Integer.parseInt(id));
            ResponseSender.sendMsgWithWorker("update_by_id", worker);
            OutputHandler.println(RequestReader.read());
            return true;
        } else {
            OutputHandler.printErr("Объекта с ID = \"" + id + "\" не существует");
        }
        return false;
    }

    private Worker workerForScript(ArrayList<String> list, int i) {
        try {
            Worker worker = new Worker();
            worker.setName(list.get(i + 1));
            worker.setCoordinates(new Coordinates(Double.parseDouble(list.get(i + 2)), Float.parseFloat(list.get(i + 3))));
            worker.setSalary(Long.parseLong(list.get(i + 4)));
            worker.setPosition(Position.valueOf(list.get(i + 5)));
            worker.setStatus(Status.valueOf(list.get(i + 6)));
            worker.setOrganization(new Organization(list.get(i + 7), Float.parseFloat(list.get(i + 8)), OrganizationType.valueOf(list.get(i + 9))));
            return worker;
        }
        catch (IllegalArgumentException e) {
            OutputHandler.println("Ошибка в данных в файле");
            return null;
        }
    }
}