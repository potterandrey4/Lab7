package org.example.toolsForCollection;

import org.example.collection.classes.Worker;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveGreater {
    public static LinkedList<Worker> remove(int uId, int id, LinkedList<Worker> collection) {
        LinkedList<Worker> newList = collection.stream()
                .filter(el -> el.getUserId() != uId)
                .filter(el -> el.getId() <= id)
                .collect(Collectors.toCollection(LinkedList::new));

        return newList;
    }
}
