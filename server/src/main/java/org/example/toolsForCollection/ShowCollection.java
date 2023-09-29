package org.example.toolsForCollection;

import org.example.ResponseSender;
import org.example.collection.classes.Worker;

import java.util.Collections;
import java.util.List;

import static org.example.tools.CheckSizeCollection.checkerSizeCollection;

public class ShowCollection {
    public static void show(List<Worker> collection) {
        StringBuilder collectionForShow = new StringBuilder();
        Collections.sort(collection);
        if (checkerSizeCollection(collection)) {
            for (Worker el : collection) {
                collectionForShow.append(el.toString()).append("\n");
            }
            ResponseSender.sendResponse(collectionForShow.toString());
        }
        else {
            ResponseSender.sendResponse("Коллекция пуста");
        }
    }
}
