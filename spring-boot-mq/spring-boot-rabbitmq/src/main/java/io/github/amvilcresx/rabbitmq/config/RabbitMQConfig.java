package io.github.amvilcresx.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
}
