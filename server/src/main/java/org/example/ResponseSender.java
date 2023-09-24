package org.example;

import org.example.messages.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.RequestReader.clientAddress;
import static org.example.ServerConnect.serverChannel;

public class ResponseSender {

    // максимальный размер одного пакета
    private static final int FRAGMENT_SIZE = 65507;
    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);


    public static void sendCommand(String string) {
        try {
            Msg response = new Msg(0, string);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();

            byte[] responseData = baos.toByteArray();   // большой массив байтов

            // Разделение данных на фрагменты
            List<byte[]> fragments = new ArrayList<>();
            for (int i = 0; i < responseData.length; i += FRAGMENT_SIZE) {
                int fragmentLength = Math.min(FRAGMENT_SIZE, responseData.length - i);
                byte[] fragment = Arrays.copyOfRange(responseData, i, i + fragmentLength);
                fragments.add(fragment);
            }

            // отправка данных
            for (byte[] fragment : fragments) {
                ByteBuffer responseBuffer = ByteBuffer.wrap(fragment);
                serverChannel.send(responseBuffer, clientAddress);
            }
            logger.info("Ответ " + response + " был отправлен " + clientAddress);

            oos.close();
        } catch (IOException e) {
            logger.warn("Произошла ошибка при отправке");
        }
    }
}