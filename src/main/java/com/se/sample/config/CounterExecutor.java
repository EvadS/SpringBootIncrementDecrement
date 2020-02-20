package com.se.sample.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CounterExecutor {
    private ExecutorService executorService;


    @PostConstruct
    public void init() {
        int cores = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(cores);
    }

    @PreDestroy
    public void cleanup() {
        executorService.shutdown();
    }

    public void addTaskToQueue(Runnable task) {
        executorService.submit(task);
    }
}


