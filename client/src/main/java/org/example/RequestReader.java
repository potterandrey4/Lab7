package org.example;

import org.example.io.OutputHandler;
import org.example.messages.Msg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.example.ClientConnect.clientSocket;

public class RequestReader {

    public static String read() {
        try {

            int maxPacketSize = 65507; // Максимальный размер пакета
            byte[] receivedData = new byte[0]; // Собранные данные

            // Получение пакетов и сборка данных
            while (true) {
                byte[] buffer = new byte[maxPacketSize];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(receivePacket);

                byte[] fragment = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
                receivedData = Arrays.copyOf(receivedData, receivedData.length + fragment.length);
                System.arraycopy(fragment, 0, receivedData, receivedData.length - fragment.length, fragment.length);

                if (fragment.length < maxPacketSize) {
                    break;
                }
            }

            // преобразовываем полученные данные в ByteBuffer
            ByteBuffer buffer = ByteBuffer.wrap(receivedData);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais);

            // десериализуем
            Msg response = (Msg) ois.readObject();
            ois.close();
            buffer.clear();

            return response.getName();

        } catch (SocketTimeoutException e) {
            return "Сервер временно недоступен. Попробуйте позже.";
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            OutputHandler.printErr("произошла ошибка на этапе чтения полученных данных");
        }
        return null;
    }
}
