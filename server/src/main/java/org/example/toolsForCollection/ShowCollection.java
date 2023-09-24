package org.example.toolsForCollection;

import org.example.ResponseSender;
import org.example.collection.classes.Worker;

import java.util.Collections;
import java.util.LinkedList;

import static org.example.tools.CheckSizeCollection.checkerSizeCollection;

public class ShowCollection {
    public static void show(LinkedList<Worker> collection) {
        StringBuilder collectionForShow = new StringBuilder();
        Collections.sort(collection);
        if (checkerSizeCollection(collection)) {
            for (Worker el : collection) {
                collectionForShow.append(el.toString()).append("\n");
            }
            ResponseSender.sendCommand(collectionForShow.toString());
        }
        else {
            ResponseSender.sendCommand("Коллекция пуста");
        }
    }
}
