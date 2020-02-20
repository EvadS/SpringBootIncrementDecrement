package com.se.sample.service;

import com.se.sample.helper.ThreadHelper;
import com.se.sample.helper.ThreadNameHeleper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class Increment implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(Increment.class);

    private Counter counter;
    private String name;
    private ReentrantLock locker;
    private ThreadNameHeleper threadNameHeleper;

    /**
     * @param counter
     * @param locker
     * @param threadNameHeleper
     */
    public Increment(Counter counter, ReentrantLock locker, ThreadNameHeleper threadNameHeleper) {
        this.locker = locker;
        this.counter = counter;
        this.threadNameHeleper = threadNameHeleper;

        threadNameHeleper.increaseIncrement();
        this.name = threadNameHeleper.getIncrementName();
    }

    @Override
    public void run() {
        try {
            while (true) {

                if (ThreadHelper.checkBreakCondition(locker, name, this.counter.getContinueProducing()))
                    break;

                counter.increment(ThreadHelper.INCREMENT_VALUE, this.name);
                Thread.sleep(ThreadHelper.INCREMENT_DELAY_MS);
            }

        } catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex.getStackTrace());
        } finally {
            // situation when we got exception from counter
            // or exception from  ThreadHelper
            if (locker.isLocked()) {
                locker.unlock();
            }

            logger.info("{} finished its job; terminating...", name);
            threadNameHeleper.decreaseIncrement();
        }
    }
}
