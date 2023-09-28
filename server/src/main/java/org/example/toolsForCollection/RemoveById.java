package org.example.toolsForCollection;

import org.example.collection.classes.Worker;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveById {
    public static LinkedList<Worker> remove(int id, LinkedList<Worker> collection) {

        return collection.stream()
                .filter(el -> el.getId() != id)
                .collect(Collectors.toCollection(LinkedList::new));

    }
}
