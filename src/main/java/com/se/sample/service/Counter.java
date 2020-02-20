package com.se.sample.service;

import com.se.sample.entity.RequestInfo;
import com.se.sample.enums.TaskType;
import com.se.sample.helper.ThreadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final Logger logger = LoggerFactory.getLogger(Counter.class);

    private RequestInfoService requestInfoService;

    private volatile Boolean continueProducing = Boolean.TRUE;
    private AtomicInteger counter = new AtomicInteger(50);

    public Counter(RequestInfoService requestInfoService) {
        this.requestInfoService = requestInfoService;
    }

    public synchronized int decrement(int value, String name) {
        if (checkNeedToStop(name, TaskType.Decrement))
            return counter.get();

        logger.info("Decrement. Thread : {} ,Counter  : {}, decrease : {} ", name, counter.get(), value);

        counter.updateAndGet(i -> i - value);
        logger.info("==  {} Counter Decrease: {}. Counter value  : {}", name, value, counter);

        if (checkNeedToStop(name, TaskType.Decrement))
            return counter.get();

        return counter.get();
    }

    public synchronized int increment(int value, String name) {

        if (checkNeedToStop(name, TaskType.Increment))
            return counter.get();

        logger.info("Increment. Thread : {} ,  Counter  : {}, increase: {}", name, counter.get(), value);
        counter.updateAndGet(i -> i + value);

        logger.info(name + "== Counter decrease: {}. Counter value: {} ", value, counter);

        if (checkNeedToStop(name, TaskType.Increment))
            return counter.get();

        return counter.get();
    }

    private synchronized boolean checkNeedToStop(String name, TaskType taskType) {
        if (!continueProducing) {
            return true;
        }

        if (counter.get() < ThreadHelper.BOTTOM_LINE || counter.get() > ThreadHelper.UPPER_LINE) {
            storeToDataBase(name, counter.get(), taskType);
            logger.info("Went beyond in  : {}, counter value : {}", name, counter);

            continueProducing = Boolean.FALSE;
            return true;
        }

        return false;
    }

    private void storeToDataBase(String name, int counter, TaskType taskType) {
        RequestInfo requestInfo = new RequestInfo(name, counter, taskType);
        requestInfoService.create(requestInfo);
    }

    public void setCounter(int counter) {
        this.counter.set(counter);
    }

    public void enableContinueProducing() {
        continueProducing = Boolean.TRUE;
    }

    public Boolean getContinueProducing() {
        return continueProducing;
    }

    public int getCounterValue() {
        return counter.intValue();
    }
}