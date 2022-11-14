package com.litian.dancechar.framework.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import java.util.concurrent.TimeUnit;

/**
 * http client线程池监控
 *
 * @author tojson
 * @date 2022/8/14 11:16
 */
public class HttpClientConnectionMonitorThread  extends Thread{
    private final HttpClientConnectionManager connectionManager;

    private volatile boolean shutDown;

    public HttpClientConnectionMonitorThread(HttpClientConnectionManager connectionManager){
        super();
        this.setName("http-connection-monitor");
        this.setDaemon(true);
        this.connectionManager = connectionManager;
        this.start();
    }

    public void shutDownMonitor(){
        synchronized (this){
            shutDown = true;
            notifyAll();
        }
    }

    @Override
    public void run(){
        try{
            while (!shutDown){
                synchronized (this){
                    // 等待5s
                    wait(5000);
                    // 关闭过期的连接
                    connectionManager.closeExpiredConnections();
                    // 关闭空闲30s的连接
                    connectionManager.closeIdleConnections(30 , TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex){}
    }



}
