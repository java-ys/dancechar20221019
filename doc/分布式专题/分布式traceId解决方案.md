                                  高频面试题：分布式traceId解决方案
    
一、常见场景
1、场景一
工作中根据日志排查问题时我们经常想看到某个请求下的所有日志，可是由于生产环境并发很大，服务被调过于频繁，日志刷新太快，
每个请求之间的日志并不连贯，互相穿插，如果在打印日志时没有为日志增加一个唯一标识，是没法分辨出哪些日志是哪个请求打印，
会影响到测试联调、线上问题排查的效率。

2、场景二
我们想知道一个请求中所有和该请求相关的链路日志，此时也需要为日志增加一个唯一标识。通常可以使用UUID或者其它雪花算法等作为唯一标识

基于以上场景，我们可以为每个请求设置一个traceId，这个请求整个链路公用同一个traceId，然后将日志收集到统一日志平台，
通过日志关键字找出traceId，再根据traceId，找出整个链路的请求过程，甚至还可以与分布式链路框架skywalking结合，分析链路的性能。

二、实现方案
1、如何实现
利用MDC机制,MDC（Mapped Diagnostic Context）映射诊断环境，是日志框架log4j、log4j2、logback提供的一种方便在多线程条件下记录日志的功能，
可以看成是一个与当前线程绑定的ThreadLocal，可以往其中添加键值对。
 #MDC的使用方法
 MDC中设置值：MDC.put(key, value);
 MDC中取值：MDC.get(key);
 MDC中内容打印到日志中：%X{traceId}
 
2、实现步骤
2.1 封装traceId和spanId相关的sdk

2.2 在日志配置文件logback-spring.xml中配置traceId和spanId
<property name="CONSOLE_LOG_PATTERN"
              value="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level[${APP_NAME},%thread,%X{X-B3-TraceId:-},%X{X-B3-SpanId:-}] 
              %logger{50}.%method:%L- %m%n"/>

2.3 spring cloud gateway中记录traceId，在过滤器SecurityFilter中实现

2.4 spring cloud gateway-----》微服务中记录traceId和spanId，提供TraceWebFilter过滤器实现

2.5 微服务多线程调用traceId传递，通过创建threadPoolTaskExecutor的任务适配器或自定义threadPoolTaskExecutor

2.6 微服务-----》微服务之间traceId传递，服务之间是通过feign调用，我们通过实现RequestInterceptor拦截器实现traceId传递

3、代码演示
整体框架：
spring cloud alibaba + nacos +  mybatis-plus


