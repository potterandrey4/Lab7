package org.example;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientConnect {
    static DatagramSocket clientSocket;
    static InetAddress serverAddress;
    static int serverPort;

    public static void connect() {
        try {

            serverAddress = InetAddress.getByName("localhost");
            serverPort = 9823;
            clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(3000); // Тайм-аут в 3 секунды

        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        clientSocket.close();
    }
}
