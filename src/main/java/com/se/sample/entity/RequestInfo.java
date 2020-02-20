package com.se.sample.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.se.sample.enums.TaskType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "threadInfo")

@ApiModel(description = "Threads that are out of range ")
public class RequestInfo {

    @ApiModelProperty(notes = "The database generated  ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(notes = "The thread name")
    @NotEmpty
    private String name;


    @ApiModelProperty(notes = "Time when out of range")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time")
    @JsonIgnore
    private Date postedAt = new Date();

    @NotNull
    private int counterValue;

    @ApiModelProperty(notes = "The type  of increment")
    @Enumerated(EnumType.ORDINAL)
    private TaskType taskType;


    public RequestInfo() {
    }

    public RequestInfo(String name, int counter, TaskType taskType) {
        this.name = name;
        this.counterValue = counter;
        this.taskType = taskType;
    }

    public TaskType getStatus() {
        return taskType;
    }

    public void setStatus(TaskType status) {
        this.taskType = status;
    }

    public int getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(int counterValue) {
        this.counterValue = counterValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
