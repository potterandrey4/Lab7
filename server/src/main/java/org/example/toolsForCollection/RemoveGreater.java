package org.example.toolsForCollection;

import org.example.collection.classes.Worker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveGreater {
    public static LinkedList<Worker> remove(int id, LinkedList<Worker> collection) {
        LinkedList<Worker> newList = collection.stream()
                .sorted(Comparator.comparingInt(Worker::getId))
                .takeWhile(el -> el.getId() != id)
                .collect(Collectors.toCollection(LinkedList::new));

        return newList;
    }
}
