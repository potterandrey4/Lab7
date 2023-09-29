package org.example;

import org.example.messages.BaseMsg;
import org.example.multithreadingModule.MultithreadingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static org.example.ServerConnect.serverChannel;

public class RequestReader {
    static InetSocketAddress clientAddress;
    private static final Logger logger = LoggerFactory.getLogger(RequestReader.class);

    private static class ReadTask extends RecursiveTask<BaseMsg> {
        @Override
        protected BaseMsg compute() {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(20000);
                clientAddress = (InetSocketAddress) serverChannel.receive(buffer);

                if (clientAddress != null) {
                    buffer.flip();

                    byte[] receivedData = new byte[buffer.remaining()];
                    buffer.get(receivedData);

                    ByteArrayInputStream bais = new ByteArrayInputStream(receivedData);
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    BaseMsg response = (BaseMsg) ois.readObject();
                    ois.close();
                    logger.info("Получен объект " + response + " от клиента: " + clientAddress);

                    buffer.clear();
                    return response;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                logger.warn("Произошла ошибка на этапе чтения полученных данных");
            }
            return null;
        }
    }

    public static BaseMsg readCommand() {
        ForkJoinPool forkJoinPool = MultithreadingManager.getRequestThreadPool();
        ReadTask readTask = new ReadTask();
        return forkJoinPool.invoke(readTask);
    }

}