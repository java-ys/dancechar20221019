package com.litian.dancechar.framework.common.httpclient;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.base.RespResultCode;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import com.litian.dancechar.framework.common.trace.TraceHttpHeaderEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

/**
 * httpclient工具类
 *
 * @author tojson
 * @date 2022/8/14 11:16
 */
@Slf4j
public class HttpClientUtil {
    private static  final String DEFAULT_CHARSET = "utf-8";

    /**
     *  post请求(默认超时2s时间)
     */
    @SuppressWarnings("unchecked")
    public static <T> RespResult<T> postJsonWithDefaultTimeOutObjectResult(String reqUrl, String data, Class<T> clz){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null,
                HttpClientPoolInit.requestConfig, -1);
        RespResult respResult = new RespResult(postResult.getCode(),postResult.getMessage(),
                                    postResult.getDetailMessage(),postResult.getTraceId(),
                                    postResult.getSpanId(),postResult.getEnv());
        return buildRespResult(postResult,clz, false);
    }

    /**
     *  post请求(默认超时2s时间)
     */
    @SuppressWarnings("unchecked")
    public static <T> RespResult<T> postJsonWithDefaultTimeOutObjectResult(String reqUrl, String data, Map<String,String> header, Class<T> clz){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, header,
                HttpClientPoolInit.requestConfig, -1);
        return buildRespResult(postResult,clz, false);
    }

    /**
     *  post请求(默认超时2s时间)
     */
    @SuppressWarnings("unchecked")
    public static RespResult postJsonWithDefaultTimeOutVoidResult(String reqUrl, String data){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null, HttpClientPoolInit.requestConfig,
                -1);
        return buildRespResult(postResult, null, true);
    }

    /**
     *  post请求(默认超时2s时间)
     */
    @SuppressWarnings("unchecked")
    public static RespResult postJsonWithDefaultTimeOutVoidResult(String reqUrl, String data, Map<String,String> header){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, header, HttpClientPoolInit.requestConfig,
                -1);
        return buildRespResult(postResult,null, true);
    }

    /**
     *  post请求(默认超时2s时间)
     */
    public static RespResult<String> postJsonWithDefaultTimeOutStrResult(String reqUrl, String data){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        return postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null,
                HttpClientPoolInit.requestConfig,
                -1);
    }

    /**
     *  post请求(默认超时2s时间)
     */
    public static RespResult<String> postJsonWithDefaultTimeOutStrResult(String reqUrl, String data, Map<String,String> header){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        return postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, header,
                HttpClientPoolInit.requestConfig,
                -1);
    }

    /**
     *  post请求(自定义超时时间)
     */
    public static RespResult<String> postJsonWithCustomTimeOutStrResult(String reqUrl, String data, int timeOut){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        return postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null, HttpClientPoolInit.requestConfig,
                timeOut);
    }

    /**
     *  post请求(自定义超时时间)
     */
    public static RespResult postJsonWithCustomTimeOutVoidResult(String reqUrl, String data, int timeOut){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null, HttpClientPoolInit.requestConfig,
                timeOut);
        return buildRespResult(postResult,null, true);
    }

    /**
     *  post请求(自定义超时时间)
     */
    public static RespResult postJsonWithCustomTimeOutVoidResult(String reqUrl, String data, Map<String,String> header, int timeOut){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, header, HttpClientPoolInit.requestConfig,
                timeOut);
        return buildRespResult(postResult,null, true);
    }

    /**
     *  post请求(自定义超时时间、携带header)
     */
    public static <T> RespResult<T> postJsonWithCustomTimeOutObjectResult(String reqUrl, String data, int timeOut , Class<T> clz){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, null, HttpClientPoolInit.requestConfig,
                timeOut);
        return buildRespResult(postResult,clz, false);
    }

    /**
     *  post请求(自定义超时时间、携带header)
     */
    public static <T> RespResult<T> postJsonWithCustomTimeOutObjectResult(String reqUrl, String data, Map<String,String> header, int timeOut, Class<T> clz){
        CloseableHttpClient closeableHttpClient = HttpClientPoolInit.getHttpClient();
        RespResult<String> postResult = postJsonWithCustomTimeOut(reqUrl, data, closeableHttpClient, header, HttpClientPoolInit.requestConfig, timeOut);
        return buildRespResult(postResult,clz,false);
    }

    private static <T> RespResult<T> buildRespResult(RespResult<String> postResult, Class<T> clz, boolean isNull){
        RespResult respResult = new RespResult(postResult.getCode(),postResult.getMessage(),
                postResult.getDetailMessage(),postResult.getTraceId(),
                postResult.getSpanId(),postResult.getEnv());
        if(isNull){
            respResult.setData(null);
            return respResult;
        }
        if(StrUtil.isNotEmpty(postResult.getData())){
            if(postResult.getData().indexOf("code") != -1){
                RespResult tempRespResult = JSONObject.parseObject(postResult.getData(), RespResult.class);
                respResult.setData(tempRespResult.getData());
            }else{
                respResult.setData(JSONObject.parseObject(postResult.getData(), clz));
            }
        }
        return respResult;
    }

    /**
     *  post请求(自定义超时时间)
     */
    private static RespResult<String> postJsonWithCustomTimeOut(String reqUrl, String data, CloseableHttpClient httpClient,
                                                       Map<String,String> header, RequestConfig requestConfig,
                                                       int timeOut){
        long start = System.currentTimeMillis();
        HttpPost request = new HttpPost(reqUrl);
        setHeaders(header, request, reqUrl);
        if(requestConfig != null && timeOut >=0){
            request.setConfig(RequestConfig.copy(requestConfig).setConnectTimeout(timeOut).setSocketTimeout(timeOut).build());
        }
        String json = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        if(data == null){
            data = "";
        }
        try{
            request.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
            response = httpClient.execute(request);
            entity = response.getEntity();
            json = EntityUtils.toString(entity, DEFAULT_CHARSET);
            log.info("post json data finish, 耗时:{}ms, url:{}, data:{},result:{}",
                    (System.currentTimeMillis()-start),reqUrl, data, json);
            return RespResult.success(json);
        }catch (SocketTimeoutException  e){
            log.error("post json data SocketTimeoutException, 耗时:{}ms, url:{}, data:{}, errMsg:{}",
                    (System.currentTimeMillis()-start),reqUrl, data, e.getMessage());
            return RespResult.error(RespResultCode.SOCKET_TIMEOUT_EXCEPTION);
        }catch (IOException e){
            log.error("post json data IoException, 耗时:{}ms, url:{}, data:{}, errMsg:{}",
                    (System.currentTimeMillis()-start),reqUrl, data, e.getMessage());
            return RespResult.error(RespResultCode.IO_EXCEPTION);
        }finally {
            try{
                if(entity != null){
                    EntityUtils.consume(entity);
                }
                if(response != null){
                    response.close();
                }
            }catch (Exception e){
                log.error("post json data exception, 耗时:{}ms, url:{}, data:{}, errMsg:{}",
                        (System.currentTimeMillis()-start),reqUrl, data, e.getMessage());
            }
        }
    }

    private static void setHeaders(Map<String, String> headers, HttpRequest request, String url){
        if(MapUtil.isNotEmpty(headers)){
            for(Map.Entry entry : headers.entrySet()){
                if(!"Cookie".equalsIgnoreCase(entry.getKey().toString())){
                    request.addHeader((String)entry.getKey(), (String)entry.getValue());
                } else {
                    Map<String, Object> cookies = (Map<String, Object>)entry.getValue();
                    for (Map.Entry entry1 :cookies.entrySet()){
                        request.addHeader(new BasicHeader("Cookie", (String)entry1.getValue()));
                    }
                }
            }
        }
        String traceId = StrUtil.isBlank(TraceHelper.getTraceId()) ? TraceHelper.genTraceId() : TraceHelper.getTraceId();
        request.addHeader(TraceHttpHeaderEnum.HEADER_TRACE_ID.getCode(), traceId);
    }


    public static void main(String[] args) {
        Map<String,String> data = Maps.newConcurrentMap();
        data.put("id", "1553731420447293441");
        RespResult<String> respResult = postJsonWithCustomTimeOutStrResult("http://127.0.0.1:8888/sys/student/findById",
                JSONUtil.toJsonStr(data), 1000);
        System.out.println(respResult.getData());

        RespResult<StudentRespDTO> respObjectResult = postJsonWithCustomTimeOutObjectResult("http://127.0.0.1:8888/sys/student/findById",
                JSONUtil.toJsonStr(data), 10, StudentRespDTO.class);
        System.out.println(respObjectResult.getData());

        List<Integer> totalList = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        List<List<Integer>> partitionList = Lists.partition(totalList, 5); // [[1, 2, 3, 4, 5], [6, 7, 8, 9, 10]]
        System.out.println(partitionList);
        partitionList.forEach(vo->{
            //todo 调用第三方
        });
    }
}
