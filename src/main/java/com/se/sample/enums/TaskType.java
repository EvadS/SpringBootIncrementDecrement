package com.se.sample.enums;

import java.util.stream.Stream;

public enum TaskType {

    Increment(0),
    Decrement(1);

    private int taskType;

    TaskType(int taskType) {
        this.taskType = taskType;
    }

    public static TaskType of(int taskType) {
        return Stream.of(TaskType.values())
                .filter(p -> p.getTaskType() == taskType)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getTaskType() {
        return taskType;
    }
}
