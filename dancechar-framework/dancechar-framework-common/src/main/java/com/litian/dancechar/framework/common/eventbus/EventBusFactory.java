package com.litian.dancechar.framework.common.eventbus;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Maps;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.litian.dancechar.framework.common.thread.CustomThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.EventListener;
import java.util.Map;

/**
 * eventbus发布事件工厂类
 *
 * @author tojson
 * @date 2022年7月10日 下午1:45:15
 */
@Slf4j
public class EventBusFactory {
    private static volatile EventBusFactory instance;
    private Map<String, Class<? extends EventListener>> registerListenerContainers = Maps.newConcurrentMap();
    private Map<String, Class<? extends EventListener>> asyncRegisterListenerContainers = Maps.newConcurrentMap();
    private final EventBus eventBus = new EventBus();
    private final AsyncEventBus asyncEventBus = new AsyncEventBus(new CustomThreadPoolFactory().newFixedThreadPool(
            2 * Runtime.getRuntime().availableProcessors() + 1));

    private EventBusFactory() {
    }

    public static EventBusFactory build() {
        // 双重校验实现单例
        if (instance == null) {
            synchronized (EventBusFactory.class) {
                if (instance == null) {
                    instance = new EventBusFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 发布同步事件
     *
     * @param event 事件对象
     */
    public void postEvent(BaseEvent event) {
        eventBus.post(event);
    }

    /**
     * 发布异步事件
     *
     * @param event 事件对象
     */
    public void postAsyncEvent(BaseEvent event) {
        asyncEventBus.post(event);
    }

    /**
     * 注册同步监听器
     *
     * @param clazz 注册类
     */
    public void registerEvent(Class<? extends EventListener> clazz) {
        String className = clazz.getSimpleName();
        if (registerListenerContainers.containsKey(className)) {
            return;
        }
        try {
            registerListenerContainers.put(className, clazz);
            Object obj = SpringUtil.getBean(clazz);
            if (ObjectUtil.isNull(obj)) {
                obj = registerListenerContainers.get(clazz).newInstance();
            }
            eventBus.register(obj);
        } catch (Exception e) {
            log.error("注册同步监听器系统异常!className:{},errMsg:{}", className, e.getMessage(), e);
        }
    }

    /**
     * 注册异步监听器
     *
     * @param asyncClass 异步监听器
     */
    public void registerAsyncEvent(Class<? extends EventListener> asyncClass) {
        String asyncClassName = asyncClass.getSimpleName();
        if (asyncRegisterListenerContainers.containsKey(asyncClassName)) {
            return;
        }
        try {
            asyncRegisterListenerContainers.put(asyncClassName, asyncClass);
            Object obj = SpringUtil.getBean(asyncClass);
            if (ObjectUtil.isNull(obj)) {
                obj = asyncRegisterListenerContainers.get(asyncClassName).newInstance();
            }
            asyncEventBus.register(obj);
        } catch (Exception e) {
            log.error("注册异步监听器系统异常!asyncClassName:{},errMsg:{}", asyncClassName, e.getMessage(), e);
        }
    }
}