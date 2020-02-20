package com.se.sample.controller;

import com.se.sample.config.CounterExecutor;
import com.se.sample.helper.ThreadNameHeleper;
import com.se.sample.service.Counter;
import com.se.sample.service.Decrement;
import com.se.sample.service.Increment;
import com.se.sample.service.RequestInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;


@Api(value = "Producer consumer management")
@RestController("/counter")
public class CounterController {

    private CounterExecutor executorService;
    private ThreadNameHeleper threadNameHeleper;

    private RequestInfoService requestInfoService;

    private ReentrantLock locker = new ReentrantLock();
    private Counter counter;

    @Autowired
    public CounterController(@Autowired CounterExecutor executorService,
                             @Autowired ThreadNameHeleper threadNameHeleper,
                             @Autowired RequestInfoService requestInfoService) {

        this.executorService = executorService;
        this.threadNameHeleper = threadNameHeleper;
        this.requestInfoService = requestInfoService;
        counter = new Counter(requestInfoService);
    }


    @ApiOperation(value = "Receive consumer and producer threads")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully increase the number of threads"),
            @ApiResponse(code = 400, message = "Incorrect value for producer or consumer threads"),
    })
    @GetMapping("/increase")
    public ResponseEntity increaseCounter(
            @ApiParam(value = "increase count the number of producer threads")
            @RequestParam(value = "increment", defaultValue = "0") int incrementVal,
            @ApiParam(value = "increase count the number of producer threads")
            @RequestParam(value = "decrement", defaultValue = "0") int decrementVal) {


        if (incrementVal > 0) {
            for (int i = 0; i < incrementVal; i++) {
                executorService.addTaskToQueue(new Increment(counter, locker, threadNameHeleper));
            }
        }

        if (decrementVal > 0) {
            for (int i = 0; i < decrementVal; i++) {
                executorService.addTaskToQueue(new Decrement(counter, locker, threadNameHeleper));
            }
        }

        if (incrementVal > 0 || decrementVal > 0) {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(String.format(" The response will be HTTP %s  success status.", HttpStatus.CREATED));
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Incorrect parameters.You must specify the number of increments or decrements "));
    }


    @ApiOperation(value = "Increase number of producer threads")
    @GetMapping("/add-one-thread-increment")
    public ResponseEntity increaseProducer() {
        Runnable decrement = new Increment(counter, locker, threadNameHeleper);
        executorService.addTaskToQueue(decrement);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(String.format(" The response will be HTTP %s  success status.", HttpStatus.CREATED));
    }

    @ApiOperation(value = "Increase number of consumer threads")
    @GetMapping("/add-one-thread-decrement")
    public ResponseEntity increaseDecrement() {
        Runnable decrement = new Decrement(counter, locker, threadNameHeleper);
        executorService.addTaskToQueue(decrement);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(String.format(" The response will be HTTP %s  success status.", HttpStatus.CREATED));
    }

    @ApiOperation(value = "change the current value of the counter")
    @GetMapping("/refresh-counter")
    public ResponseEntity refreshCounter(
            @ApiParam(value = "counter value")
            @RequestParam("data") int itemId) {
        counter.setCounter(itemId);

        counter.enableContinueProducing();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Counter %s", counter.getCounterValue()));
    }
}