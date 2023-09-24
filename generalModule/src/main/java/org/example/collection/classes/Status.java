package org.example.collection.classes;

import java.io.Serializable;

public enum Status implements Serializable {
    REGULAR(4),
    PROBATION(3),
    HIRED(2),
    FIRED(1);

    private final int importance;

    Status(int importance) {
        this.importance = importance;
    }


    public static Status oaoaoa(String name) {
        try {
            return Status.values()[Integer.parseInt(name) - 1];
        } catch (NumberFormatException e) {
            return Status.valueOf(name.toUpperCase());
        }
    }
    public static Status getStatusByTitle(String title) {
        Status status = null;
        if (title.matches("\\d+")) {
            int number = Integer.parseInt(title);
            if (number <= 4 && number > 0) {
                status = Status.values()[number-1];
            } else {
                return null;
            }
        } else {
            for (Status s : Status.values()) {
                if (s.toString().equalsIgnoreCase(title)) {
                    status = s;
                }
            }
        }
        return status;
    }

    public int getImportance() {
        return importance;
    }

}