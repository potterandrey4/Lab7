package org.example.toolsForCollection;

import java.util.stream.Collectors;

import static org.example.tools.CommandExecutor.statusSorter;

public class PrintFieldDescending {
    public static String print() {

        return statusSorter().stream()
                .map(worker -> worker.getStatus().toString())
                .collect(Collectors.joining());

    }
}