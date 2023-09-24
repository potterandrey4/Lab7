package org.example;

import org.example.messages.BaseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import static org.example.ServerConnect.serverChannel;

public class RequestReader {
    static InetSocketAddress clientAddress;
    static BaseMsg response;
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);

    public static BaseMsg readCommand() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(20000);
            clientAddress = (InetSocketAddress) serverChannel.receive(buffer);

            if (clientAddress != null) {
                buffer.flip();

                byte[] receivedData = new byte[buffer.remaining()];
                buffer.get(receivedData);

                // получаем байтовое представление сериализованного класса
                ByteArrayInputStream bais = new ByteArrayInputStream(receivedData);
                ObjectInputStream ois = new ObjectInputStream(bais);

                // десериализуем
                response = (BaseMsg) ois.readObject();
                ois.close();
                logger.info("Получен объект " + response + " от клиента: " + clientAddress);

                buffer.clear();
                return response;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            logger.warn("произошла ошибка на этапе чтения полученных данных");
        }
        return null;
    }

}