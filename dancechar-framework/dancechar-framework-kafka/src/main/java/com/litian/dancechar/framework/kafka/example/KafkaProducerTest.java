package com.litian.dancechar.framework.kafka.example;

import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * kafka生产者工具类
 *
 * @author tojson
 * @date 2022/23/50 08:17
 */
@Slf4j
public class KafkaProducerTest {
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;

    public void produceMsg(){
        kafkaProducerUtil.sendMessage("topic_act", "123");
    }
}
