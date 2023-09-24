package org.example.tools;

import org.example.collection.classes.Worker;

import java.util.LinkedList;

public class CheckSameId {
    public static boolean check(LinkedList<Worker> workers) {
        return workers.stream()
                .map(Worker::getId)
                .distinct()
                .count() == workers.size();
    }
}
