package org.example.toolsForCollection;

import org.example.collection.classes.Worker;

import java.util.Comparator;

public class StatusOfWorkerComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker o1, Worker o2) {
        return Integer.compare(o2.getStatus().getImportance(), o1.getStatus().getImportance());
    }
}