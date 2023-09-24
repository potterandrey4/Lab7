package org.example.collection.classes;

import java.io.Serializable;

public enum Position implements Serializable {
    MANAGER(),
    HUMAN_RESOURCES(),
    LEAD_DEVELOPER(),
    CLEANER();

    Position() {}

    public static Position getPositionByTitle(String title) {
        Position positions = null;
        if (title.matches("\\d+")) {
            int number = Integer.parseInt(title);
            if (number > 0 && number <= 4 ) {
                positions = Position.values()[number-1];
            } else {
                return null;
            }
        } else {
            for (Position s : Position.values()) {
                if (s.toString().equalsIgnoreCase(title)) {
                    positions = s;
                }
            }
        }
        return positions;
    }

}