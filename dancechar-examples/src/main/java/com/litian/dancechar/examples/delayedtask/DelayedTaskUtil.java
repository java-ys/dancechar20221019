package com.litian.dancechar.examples.delayedtask;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DelayedTaskUtil {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3);
        executorService.schedule(()->System.out.println("do something") , 2, TimeUnit.SECONDS);
    }
}
