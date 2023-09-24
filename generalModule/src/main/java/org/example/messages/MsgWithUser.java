package org.example.messages;

import org.example.User;

public class MsgWithUser extends BaseMsg {
    public MsgWithUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
