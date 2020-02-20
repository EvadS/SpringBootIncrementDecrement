package com.se.sample.service;

import com.se.sample.helper.ThreadHelper;
import com.se.sample.helper.ThreadNameHeleper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class Decrement implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(Decrement.class);

    private ReentrantLock locker;
    private Counter counter;
    private ThreadNameHeleper threadNameHeleper;
    private String name;

    /**
     * @param counter
     * @param locker
     * @param threadNameHeleper
     */
    public Decrement(Counter counter, ReentrantLock locker, ThreadNameHeleper threadNameHeleper) {
        this.locker = locker;
        this.counter = counter;
        this.threadNameHeleper = threadNameHeleper;

        threadNameHeleper.increaseDecrement();
        this.name = threadNameHeleper.getDecrementName();

    }

    @Override
    public void run() {
        try {
            while (true) {
                if (ThreadHelper.checkBreakCondition(locker, name, this.counter.getContinueProducing()))
                    break;

                counter.decrement(ThreadHelper.DECREMENT_VALUE, this.name);
                Thread.sleep(ThreadHelper.DECREMENT_DELAY_MS);
            }
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage(), ex.getStackTrace());
        } finally {
            if (locker.isLocked()) {
                locker.unlock();
            }

            logger.info("{} finished its job; terminating...", name);
            threadNameHeleper.decreaseDecrement();
        }
    }
}
