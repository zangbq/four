package com.jk.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    @Bean
    public Queue messageQueue2(){
        return new Queue("videoMongoUserShopping_Id");
    }

    @Bean
    public Queue messageQueue1(){
        return new Queue("findByShoppingId_Info");
    }

}



