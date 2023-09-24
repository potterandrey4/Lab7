package org.example.toolsForCollection;


import org.example.collection.classes.Worker;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;

import static org.example.tools.CheckSizeCollection.checkerSizeCollection;


public class LastModifyCollection {
    public static String getLastTime(LinkedList<Worker> collection) {
        if (checkerSizeCollection(collection)) {
            Optional<Worker> lastModifiedWorker = collection.stream()
                    .max(Comparator.comparing(Worker::getCreationDate));

            if (lastModifiedWorker.isPresent()) {
                Worker lastTime = lastModifiedWorker.get();
                return "Время последней модификации коллекции: " + lastTime.time() + "\n";
            } else {
                return "Коллекция пуста";
            }
        }
        return "Коллекция пуста";
    }
}