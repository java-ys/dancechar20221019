package com.litian.dancechar.examples.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.*;

@Slf4j
public class ThreadExample {

    public static void main(String[] args) throws Exception{
        // 创建单个线程池
        //testNewSingleThreadExecutor();
        // 创建固定大小的线程池
        //testNewFixedThreadPool();
        // 创建一个可缓存线程池
        //testNewCachedThreadPool();
        // 创建一个可以执行延迟任务的线程池
        //testNewScheduledThreadPool();
        // 创建一个抢占式执行的线程池
        //testNewWorkStealingPool();
        testThreadPoolExecutor();
    }

    public static void testThreadPoolExecutor() throws  Exception{
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(r instanceof SaveLogThread){
                    SaveLogThread saveLogThread = (SaveLogThread)r;
                    log.info("当前线程{}被拒绝", saveLogThread.name);
                }
            }
        });
        for(int i=0;i < 10;i++){
            SaveLogThread thread = new SaveLogThread("线程"+ i);
            threadPoolExecutor.execute(thread);
        }
        threadPoolExecutor.shutdown();
    }

    public static void testNewSingleThreadExecutor() throws  Exception{
        ExecutorService executorService = null;
        executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<100;i++){
            executorService.execute(()->{
                log.info("testNewSingleThreadExecutor======{}", Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }

    public static void testNewFixedThreadPool() throws  Exception{
        ExecutorService executorService = null;
        executorService = Executors.newFixedThreadPool(3);
        for(int i=0;i<100;i++){
            executorService.execute(()->{
                log.info("testNewFixedThreadPool======{}", Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }

    /**
     * 创建可缓存的线程池
     */
    public static void testNewCachedThreadPool() throws  Exception{
        ExecutorService executorService = null;
        executorService = Executors.newCachedThreadPool();
        //executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("dancechar-pool-").build());
        for(int i=0;i<100;i++){
            executorService.execute(()->{
                log.info("testNewCachedThreadPool======{}", Thread.currentThread().getName());
            });
        }
        Future<String> future = executorService.submit(()->{
            log.info("submit======{}", Thread.currentThread().getName());
            return "nihao";
        });
        String obj = future.get();
        log.info(obj);
        executorService.shutdown();
    }

    public static void testNewScheduledThreadPool() throws  Exception{
        ScheduledExecutorService executorService = null;
        executorService = Executors.newScheduledThreadPool(3);
        log.info("testNewScheduledThreadPool-添加任务，时间：" + new Date());
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("testNewScheduledThreadPool-任务被执行，时间：" + new Date());
            }
        }, 10, TimeUnit.SECONDS);
        //  表示延迟2秒后每3秒执行一次
        /*
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("testNewScheduledThreadPool-任务被执行，时间：" + new Date());
            }
        }, 2, 3, TimeUnit.SECONDS);
         */
        executorService.shutdown();
    }

    public static void testNewWorkStealingPool() throws  Exception{
        ExecutorService executorService = null;
        executorService = Executors.newWorkStealingPool();
        for(int i=0;i<100;i++){
            executorService.execute(()->{
                log.info("testNewWorkStealingPool======{}", Thread.currentThread().getName());
            });
        }
        // 确保任务执行完成
        while (!executorService.isTerminated()) {
        }
    }
}