package com.entian.common.service.transaction.listener;

import com.alibaba.fastjson.JSONObject;
import com.entian.common.service.dto.CommodityOrderDTO;
import com.entian.common.service.service.PointsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jianggangli
 * @version 1.0 2020/12/29 17:46
 * 功能:
 */
@Slf4j
@Component
public class OrderListener implements MessageListenerConcurrently {

    @Autowired
    PointsService pointsService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        log.info("消费者线程监听到消息。");
        try{
            for (MessageExt message:list) {
                if (!processor(message)){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            log.error("处理消费者数据发生异常。{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    /**
     * 消息处理，第3次处理失败后，发送邮件通知人工介入
     * @param message
     * @return
     */
    private boolean processor(MessageExt message){
        String body = new String(message.getBody());
        try {
            log.info("消息处理....{}",body);
            log.info("开始处理订单数据，准备增加积分....");
            CommodityOrderDTO order  = JSONObject.parseObject(message.getBody(), CommodityOrderDTO.class);
            pointsService.increasePoints(order);
            int i = 1 / 0;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            if(message.getReconsumeTimes()>=3){
                log.error("消息重试已达最大次数，将通知业务人员排查问题。{}",message.getMsgId());
                //sendMail(message);
                return true;
            }
            return false;
        }
    }
}
