package com.entian.common.service.transaction;

import com.entian.common.service.transaction.listener.OrderListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jianggangli
 * @version 1.0 2020/12/29 17:50
 * 功能:
 */
@Component
public class TransactionConsumer {

    String consumerGroup = "consumer-group";
    DefaultMQPushConsumer consumer;

    @Autowired
    OrderListener orderListener;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr("114.55.208.126:9876");
        consumer.subscribe("order","*");
        consumer.registerMessageListener(orderListener);
        consumer.start();
    }
}

