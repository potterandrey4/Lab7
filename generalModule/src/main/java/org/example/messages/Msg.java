package org.example.messages;

public class Msg extends BaseMsg {

    public Msg(int uId, String command) {
        this.textMsg = command;
    }

   public String getName() {
        return textMsg;
   }
}
