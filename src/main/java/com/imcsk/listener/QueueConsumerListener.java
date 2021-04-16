package com.imcsk.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author csk
 * @date 2021/4/4
 */
@Component
public class QueueConsumerListener {

    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message){
        System.out.println("queue接收到消息："+message);
    }
}
