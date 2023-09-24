package org.example;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String name;
    String pswd;


    public User() {}

    public User (String name, String pswd) {
        this.name = name;
        this.pswd = pswd;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
