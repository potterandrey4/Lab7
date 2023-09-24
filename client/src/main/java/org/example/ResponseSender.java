package org.example;

import org.example.authModule.EntryHandler;
import org.example.collection.classes.Worker;
import org.example.io.OutputHandler;
import org.example.messages.Msg;
import org.example.messages.MsgWithArg;
import org.example.messages.MsgWithUser;
import org.example.messages.MsgWithWorker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

import static org.example.ClientConnect.*;
import static org.example.ClientConnect.serverPort;

public class ResponseSender {
    private final static int uId = EntryHandler.getUID();
    private static void sendData(Object o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();

            byte[] serializedData = baos.toByteArray();

            DatagramPacket packet = new DatagramPacket(serializedData, serializedData.length, serverAddress, serverPort);

            clientSocket.send(packet);

            oos.close();
        } catch (IOException e) {
            OutputHandler.printErr("Данные не отправлены");
        }
    }


    public static void sendMsg(String msg) {
        Msg response = new Msg(uId, msg);
        sendData(response);
    }

    public static void sendMsgWithArg(String msg, String arg) {
        MsgWithArg response = new MsgWithArg(uId, msg, arg);
        sendData(response);
    }

    public static void sendMsgWithWorker(String msg, Worker worker) {
        MsgWithWorker response = new MsgWithWorker(msg, worker);
        sendData(response);
    }


    public static void sendMsgWithUser(User user) {
        MsgWithUser response = new MsgWithUser(user);
        sendData(response);
    }
}
