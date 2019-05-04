package com.dw.helloworld.config;

import com.dw.helloworld.entity.dto.LoginDto;
import com.dw.helloworld.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

/**
 * @Description:
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * Vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 * @Author: DING WEI
 * @Date: 2019-03-30 16:32
 */
@Configuration
public class RabbitMQConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory(host,port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setPublisherConfirms(true);
        return factory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     * @Author: DING WEI
     * @Date: 2019-03-30 17:12
     * @Version: 1.0
     */
    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(EXCHANGE_A);
    }

    @Bean
    public Queue queueA(){
        // 队列持久
        return new Queue(QUEUE_A,true);
    }

    @Bean
    public Queue queueB(){
        return new Queue(QUEUE_B,true);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(ROUTINGKEY_A);
    }

    @Resource
    private UserService userService;

    @Bean
    public SimpleMessageListenerContainer messageContainer(){
        // 加载处理消息队列A的队列
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        // 设置接收多个队列里面的消息，这里设置接收队列A
        // 假如想一个消费者处理多个队列里面的信息可以如下设置：
        container.setQueues(queueA());
        container.setExposeListenerChannel(true);
        // 设置最大的并发消费者数量
        container.setMaxConcurrentConsumers(10);
        // 设置最小的并发消费者数量
        container.setConcurrentConsumers(1);
        // 设置确认模式->手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                /**
                 * 通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个
                 * Consumer在同一个时间点最多处理一个Message，换句话说,在接收到该
                 * Consumer的ack前,它不会将新的Message分发给它
                 * */
                channel.basicQos(1);
                ObjectMapper objectMapper = new ObjectMapper();
                String msg = new String(message.getBody());
                LoginDto loginDto = objectMapper.readValue(msg.getBytes("utf-8"), LoginDto.class);
                logger.info("消费队列A的消息:"+msg);
                logger.info("消费队列A的消息对象:"+ loginDto.toString());
                // 保存userdto到数据库
                userService.saveUser(loginDto);
                /**
                 * 为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
                 * 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，
                 * 然后RabbitMQ才会将消息删除。
                 * */
                logger.info("应答===="+message.getMessageProperties().getDeliveryTag());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        });
        return container;
    }


}
