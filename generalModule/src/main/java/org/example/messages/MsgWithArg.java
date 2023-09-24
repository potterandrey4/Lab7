package org.example.messages;

public class MsgWithArg extends BaseMsg {
    public MsgWithArg(int uId, String command, String arg) {
        this.textMsg = command;
        this.arg = arg;
    }

    public String getName() {
        return textMsg;
    }

    public String getArg() {
        return arg;
    }

    public int getIntArg() {
        return Integer.parseInt(arg);
    }

    public int getUid() {
        return uId;
    }
}