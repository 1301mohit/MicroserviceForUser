package com.bridgelabz.fundoo.user.rabbitmq;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.applicationconfig.RabbitMqConfiguration;


@Component
public class MessageProducer {

	@Autowired
    private AmqpTemplate rabbitTemplate;
 
	public void sendMessage(RabbitMqBody body) {
		System.out.println(new Date());
		rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE,RabbitMqConfiguration.USER_ROUTING_KEY, body);
	    System.out.println("Is listener returned ::: "+body);
		System.out.println(new Date());
	}
	
}
