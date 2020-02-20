package com.se.sample.helper;

import org.springframework.stereotype.Component;

@Component
public class ThreadNameHeleper {

    private static int incrementNumber = 0;
    private static int decrementNumber = 0;

    public synchronized void increaseIncrement() {
        incrementNumber++;
    }

    public synchronized void decreaseIncrement() {
        if (incrementNumber > 0)
            incrementNumber--;
    }

    public String getIncrementName() {
        return String.format("Increment %s", incrementNumber);
    }

    public synchronized void decreaseDecrement() {
        if (decrementNumber > 0)
            decrementNumber--;
    }

    public synchronized void increaseDecrement() {
        decrementNumber++;
    }

    public String getDecrementName() {
        return String.format("Decrement %s", decrementNumber);
    }
}
