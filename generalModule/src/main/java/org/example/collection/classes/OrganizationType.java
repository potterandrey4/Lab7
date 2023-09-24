package org.example.collection.classes;

import java.io.Serializable;

public enum OrganizationType implements Serializable {
    COMMERCIAL(),
    PUBLIC(),
    GOVERNMENT(),
    TRUST(),
    PRIVATE_LIMITED_COMPANY();


    OrganizationType() {
    }

    public static OrganizationType getOrganizationTypeByTitle(String title) {
        OrganizationType organizationType = null;
        if (title.matches("\\d+")) {
            int number = Integer.parseInt(title);
            if (number <= 4 && number > 0) {
                organizationType = OrganizationType.values()[number-1];
            } else {
                return null;
            }
        } else {
            for (OrganizationType s : OrganizationType.values()) {
                if (s.toString().equalsIgnoreCase(title)) {
                    organizationType = s;
                }
            }
        }
        return organizationType;
    }


}