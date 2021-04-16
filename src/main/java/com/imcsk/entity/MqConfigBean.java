package com.imcsk.entity;/**
 * @author csk
 * @date 2021/4/4
 */

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.*;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author csk
 * @date 2021/4/4
 */
@Configuration
public class MqConfigBean {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private String userPwd;

    @Value("${spring.activemq.queue-name}")
    private String queueName;

    @Value("${spring.activemq.topic-name}")
    private String topicName;

    BrokerService brokerService;

    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue(queueName);
    }

    @Bean(name = "topic")
    public Topic topic() {
        return new ActiveMQTopic(topicName);
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, userPwd, brokerUrl);
        try {
            ActiveMQConnection connection = (ActiveMQConnection)factory.createConnection();
            DestinationSource destinationSource = connection.getDestinationSource();
            QueueSession queueSession = connection.createQueueSession(true, Session.CLIENT_ACKNOWLEDGE);

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ActiveMQConnectionFactory(userName, userPwd, brokerUrl);
    }

    @Bean
    public JmsMessagingTemplate jmsMessageTemplate(){
        return new JmsMessagingTemplate(connectionFactory());
    }

    // 在Queue模式中，对消息的监听需要对containerFactory进行配置
    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    public static void main(String[] args) {

         String url="service:jmx:rmi:///jndi/rmi://localhost:11099/jmxrmi";
         String broker="org.apache.activemq:brokerName=localhost,type=Broker";

        try {
            JMXServiceURL urls = new JMXServiceURL(url);
            JMXConnector connector = JMXConnectorFactory.connect(urls, null);
            connector.connect();
            MBeanServerConnection conn = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName(broker);
            BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(conn, name, BrokerViewMBean.class, true);
            for (ObjectName na : mBean.getQueues()) {//获取点对点的队列       mBean.getTopics() 获取订阅模式的队列
                QueueViewMBean queueBean = (QueueViewMBean)
                        MBeanServerInvocationHandler.newProxyInstance(conn, na, QueueViewMBean.class, true);
                System.out.println("******************************");
                System.out.println("队列的名称：" + queueBean.getName());
                System.out.println("队列中剩余的消息数：" + queueBean.getQueueSize());
                System.out.println("消费者数：" + queueBean.getConsumerCount());
                System.out.println("出队列的数量：" + queueBean.getDequeueCount());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
