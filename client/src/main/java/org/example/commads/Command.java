package org.example.commads;

import org.example.exceptions.ReadException;

import java.io.Serializable;

public abstract class Command implements Serializable {
    String name;

    public Command(String name) {
        this.name = name;
    }

    public abstract void execute(String arg) throws ReadException;

    public abstract void execute();
}
