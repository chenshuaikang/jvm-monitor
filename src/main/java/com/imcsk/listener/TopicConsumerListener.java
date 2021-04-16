package com.imcsk.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author csk
 * @date 2021/4/4
 */
@Component
public class TopicConsumerListener {

    @JmsListener(destination="${spring.activemq.topic-name}", containerFactory="topicListener")
    public void readActiveTopic(String message){
        System.out.println("topic接收到消息："+message);
    }

}
