package io.github.amvilcresx.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /* =================== 【定义 Direct 交换机】 ====================== */
    @Bean
    public DirectExchange directExchange() {
        // 参数1： 交换机名称
        // 参数2： 是否开启持久化
        // 参数3： 交换机长时间未使用时，是否自动删除
        return new DirectExchange("direct.exchange", true, false);
    }

    // 定义队列
    @Bean
    public Queue directQueue1() {
        return new Queue("direct.queue1");
    }

    @Bean
    public Queue directQueue2() {
        return new Queue("direct.queue2");
    }

    // 绑定队列到交换机，指定路由键
    @Bean
    public Binding bindingDirect1(DirectExchange directExchange, Queue directQueue1) {
        // 【队列】 -----绑定----> 【交换机】 ------用哪个【routing key】
        return BindingBuilder.bind(directQueue1)
                .to(directExchange)
                .with("routing.key1");
    }

    @Bean
    public Binding bindingDirect2(DirectExchange directExchange, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2)
                .to(directExchange)
                .with("routing.key2");
    }




    /* =================== 【定义 Topic 交换机】 ====================== */
    @Bean
    public TopicExchange topicExchange() {
        // 参数1： 交换机名称
        // 参数2： 是否开启持久化
        // 参数3： 交换机长时间未使用时，是否自动删除
        return new TopicExchange("topic.exchange", true, false);
    }


    @Bean
    public Queue topicQueue1() {
        return new Queue("topic.queue1");
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topic.queue2");
    }

    @Bean
    public Binding bindingTopic1(TopicExchange topicExchange, Queue topicQueue1) {
        return BindingBuilder.bind(topicQueue1)
                .to(topicExchange)
                .with("msg.*");
    }

    @Bean
    public Binding bindingTopic2(TopicExchange topicExchange, Queue topicQueue2) {
        return BindingBuilder.bind(topicQueue2)
                .to(topicExchange)
                .with("#.body");
    }

    /*
    * 对于上述两个 routingKey：
    *  若 routingKey = "msg.hello" ,  则只有 topic.queue1 可以收到消息；
    *  若 routingKey = "nihao.body" ,  则只有 topic.queue2 可以收到消息；
    *  若 routingKey = "nihao.rabbbit.body" ,  则只有 topic.queue2 可以收到消息；
    *  若 routingKey = "msg.body", 则 topic.queue1 与 topic.queue2 可以收到消息
    * */



    /* =================== 【定义 Fanout 交换机】 ====================== */

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    @Bean
    public Binding bindingFanout1(FanoutExchange fanoutExchange, Queue fanoutQueue1) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanout2(FanoutExchange fanoutExchange, Queue fanoutQueue2) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }


    /* =================== 【 死信队列相关 】 ====================== */
    // 声明死信交换机（Direct 类型）
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dead_letter_exchange");
    }

    // 声明死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dead_letter_queue").build();
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("dead_routing_key");
    }

    // 声明业务交换机
    @Bean
    public DirectExchange businessExchange() {
        return new DirectExchange("business_exchange");
    }


    // 声明业务队列，并配置死信交换机和 TTL
    /*
    *
        1、消息被拒绝并且没有设置重新入队：(NACK || Reject ) && requeue == false
        2、消息过期（消息或者队列的TTL设置）
        3、消息堆积，并且队列达到最大长度，先入队的消息会变成DL。
     */
    @Bean
    public Queue businessQueue() {
        return QueueBuilder.durable("business_queue")
                .deadLetterExchange("dead_letter_exchange")    // 死信交换机名称
                .deadLetterRoutingKey("dead_routing_key") // 死信路由键
                .ttl(5000) // // 消息过期时间（5秒）
                .build();

    }

    // 绑定业务队列到业务交换机
    @Bean
    public Binding businessBinding(Queue businessQueue, DirectExchange businessExchange) {
        return BindingBuilder.bind(businessQueue)
                .to(businessExchange)
                .with("business_routing_key");
    }
}
