package com.se.sample.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadHelper {

    public static final int INCREMENT_DELAY_MS = 1000;
    public static final int DECREMENT_DELAY_MS = 1000;
    public static final int DECREMENT_VALUE = 3;
    public static final int INCREMENT_VALUE = 2;
    public static final int UPPER_LINE = 100;
    public static final int BOTTOM_LINE = 0;
    private static final Logger logger = LoggerFactory.getLogger(ThreadHelper.class);

    // one place for check top and bottom line per counter
    public static boolean checkBreakCondition(ReentrantLock locker, String name, boolean continueProducing) {
        locker.lock();

        if (!continueProducing) {
            logger.info("Was stopping {}", name);
            locker.unlock();

            return true;
        }

        locker.unlock();

        return false;
    }
}