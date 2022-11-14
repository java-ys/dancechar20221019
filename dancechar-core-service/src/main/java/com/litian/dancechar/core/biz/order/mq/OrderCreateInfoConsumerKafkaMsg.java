package com.litian.dancechar.core.biz.order.mq;

import com.litian.dancechar.core.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * 下单消息订阅
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class OrderCreateInfoConsumerKafkaMsg {

    @KafkaListener(groupId="dancechar-core-consumer", topics = {CommConstants.KafkaTopic.TOPIC_ORDER_INFO})
    public void consumer1(ConsumerRecord<Integer, String> record) {
        log.info("下单消息订阅！topic:{},partition:{},value:{}",record.topic(),record.partition(),
                record.value());
    }

}
