package org.example.tools;

import org.example.collection.classes.Worker;

import java.util.LinkedList;
import java.util.OptionalInt;

public class GetNewId {
    public static int get(LinkedList<Worker> collection) {
        OptionalInt maxId = collection.stream().mapToInt(Worker::getId).max();

        if (maxId.isPresent()) {
            return maxId.getAsInt() +1;
        }

        return 0;
//        Optional<Worker> highestIdWorker = collection.stream()
//                .reduce((w1, w2) -> w1.getId() > w2.getId() ? w1 : w2);

//        if (highestIdWorker.isPresent()) {
//            Worker worker = highestIdWorker.get();
//            return worker.getId() +1;
//        }
//        else {
//            return 0;
//        }
    }
}
