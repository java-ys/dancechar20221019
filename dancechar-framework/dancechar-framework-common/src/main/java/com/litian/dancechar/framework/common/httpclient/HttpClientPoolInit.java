package com.litian.dancechar.framework.common.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;

/**
 * httpclient线程池初始化相关处理
 *
 * @author tojson
 * @date 2022/8/14 11:16
 */
@Slf4j
public class HttpClientPoolInit {
    private static PoolingHttpClientConnectionManager clientConnectionManager = null;
    public static String DEFAULT_CHARSET = "utf-8";

    private static CloseableHttpClient httpClient = null;
    private static CloseableHttpClient proxyHttpClient = null;

    private static Object syncLock = new Object();
    private static Object syncProxyLock = new Object();
    /**默认连接超时时间*/
    private static Integer CONNECT_TIMEOUT = 2 * 1000;
    /**默认获取远程数据超时时间*/
    private static Integer SOCKET_TIME_OUT = 2 * 1000;
    /**默认从连接池中获取连接的时间*/
    private static Integer CONNECTION_REQUEST_TIME_OUT = 100;

    private static Integer HTTP_CLIENT_MAX_TOTAL = 1500;
    private static Integer HTTP_CLIENT_MAX_PER_ROUTE = 400;

    public static RequestConfig requestConfig;
    public static RequestConfig proxyRequestConfig;

    /**http 连接管理器守护线程*/
    private static HttpClientConnectionMonitorThread thread;

    private HttpClientPoolInit(){}

    static {
        try{
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https",factory)
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();
            clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        }catch (Exception e){
            log.error("httpclient pool init error!errMsg:{}", e.getMessage(), e);
        }
    }

    public static CloseableHttpClient getHttpClient(){
        if(httpClient != null){
            return httpClient;
        }
        synchronized (syncLock){
            if(httpClient == null){
                RequestConfig config = RequestConfig.custom()
                        .setConnectTimeout(CONNECT_TIMEOUT)
                        .setSocketTimeout(SOCKET_TIME_OUT)
                        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT)
                        .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                        .build();
                requestConfig = config;
                httpClient = getCloseableHttpClient(config);
            }
        }
        return httpClient;
    }

    private static CloseableHttpClient getCloseableHttpClient(RequestConfig config){
        // 设置连接池大小
        clientConnectionManager.setMaxTotal(HTTP_CLIENT_MAX_TOTAL);
        // 连接每个域名对应的连接数
        clientConnectionManager.setDefaultMaxPerRoute(HTTP_CLIENT_MAX_PER_ROUTE);
        HttpClientPoolInit.thread = new HttpClientConnectionMonitorThread(clientConnectionManager);
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setConnectionManager(clientConnectionManager)
                .setDefaultRequestConfig(config)
                .setConnectionManagerShared(true)
                .setKeepAliveStrategy(
                        (HttpResponse response, HttpContext context) ->{
                            HeaderElementIterator it = new BasicHeaderElementIterator(
                                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                            while (it.hasNext()){
                                HeaderElement element = it.nextElement();
                                String param = element.getName();
                                String value = element.getValue();
                                if(value != null && "timeout".equalsIgnoreCase(param)){
                                    return Long.parseLong(value) * 1000;
                                }
                            }
                            return 30000;
                        }
                ).build();
        return closeableHttpClient;
    }
}
