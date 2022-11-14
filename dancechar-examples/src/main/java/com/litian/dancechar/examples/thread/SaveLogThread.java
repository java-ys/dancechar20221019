package com.litian.dancechar.examples.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveLogThread implements Runnable{
    public String name;
    public SaveLogThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        log.info("{}线程保存日志！", name);
    }
}
