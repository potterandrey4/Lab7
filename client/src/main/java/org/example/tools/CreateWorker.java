package org.example.tools;

import org.example.collection.classes.Worker;
import org.example.io.OutputHandler;

import static org.example.tools.Generators.*;

public class CreateWorker {
    public static Worker create() {

        Worker worker = new Worker();

        OutputHandler.println("Введите имя: ");
        worker.setName(generateString());
        worker.setCoordinates(generateCoordinates());
        worker.setSalary(generateSalary());
        worker.setPosition(generatePosition());
        worker.setStatus(generateStatus());
        worker.setOrganization(generateOrganization());

        return worker;
    }
}
