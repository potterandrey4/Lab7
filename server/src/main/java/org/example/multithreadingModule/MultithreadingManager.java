package org.example.multithreadingModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MultithreadingManager {
    private static final ForkJoinPool requestThreadPool = new ForkJoinPool();
    private static final ExecutorService responseThreadPool = Executors.newFixedThreadPool(2);

    public static ForkJoinPool getRequestThreadPool() {
        return requestThreadPool;
    }

    public static ExecutorService getResponseThreadPool() {
        return responseThreadPool;
    }
}