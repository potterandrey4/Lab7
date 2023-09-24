package org.example.messages;

import org.example.User;
import org.example.collection.classes.Worker;

import java.io.Serializable;

public abstract class BaseMsg implements Serializable {
    int uId;
    String textMsg;
    String arg;
    Worker worker;
    User user;
}
