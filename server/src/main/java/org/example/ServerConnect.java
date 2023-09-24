package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class ServerConnect {

    static DatagramChannel serverChannel;
    private static final int serverPort = 7777;
    private static final Logger logger = LoggerFactory.getLogger(ServerConnect.class);

    public static void connect() {
        try {

            serverChannel = DatagramChannel.open();
            serverChannel.configureBlocking(false);

            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 9823);
            serverChannel.bind(serverAddress);

            logger.info("Сервер запущен и ожидает подключений");

        } catch (SocketException e) {
            logger.warn("Неверные настройки сокета");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
