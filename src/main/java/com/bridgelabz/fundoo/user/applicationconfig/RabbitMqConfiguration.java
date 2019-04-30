package com.bridgelabz.fundoo.user.applicationconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.user.rabbitmq.MessageListener;

@Configuration
public class RabbitMqConfiguration {

	 public static final String USER_ROUTING_KEY = "user.routing.key";
	 
//	 public static final String USER_QUEUE_KEY = "user.queue.key";
	 
	 public static final String EXCHANGE = "queue.exchange";
	 
	 public static final String NOTE_ROUTING_KEY = "note.routing.key";
	 
//	 public static final String NOTE_QUEUE_KEY = "note.queue.key";

	//private static final Queue noteQueue = null;

	 
//	 @Bean(name="userQueue")
//	 Queue queue() {
//		 return new Queue(USER_QUEUE_KEY, true);
//	 }
	 
	 @Bean(name="userQueue")
	 Queue queue() {
		 return new Queue(USER_ROUTING_KEY, true);
	 }

	 @Bean
	 TopicExchange exchange() {
		 return new TopicExchange(EXCHANGE);
	 }

	 @Bean
	 public Jackson2JsonMessageConverter producer() {
		 return new Jackson2JsonMessageConverter();
	 }
 
	 @Bean
	 Binding binding(Queue userQueue, TopicExchange exchange) {
		 return BindingBuilder.bind(userQueue).to(exchange).with(USER_ROUTING_KEY);
	 }
	 
//	 @Bean(name="noteQueue")
//	 Queue noteQueue() {
//		 return new Queue(NOTE_QUEUE_KEY, true);
//	 }
	 
	 @Bean(name="noteQueue")
	 Queue noteQueue() {
		 return new Queue(NOTE_ROUTING_KEY, true);
	 }

	 @Bean
	 Binding noteBinding(Queue noteQueue, TopicExchange exchange) {
		 return BindingBuilder.bind(noteQueue).to(exchange).with(NOTE_ROUTING_KEY);
	 }
	 
//	 For RabbitMQ listener
	 
	 @Bean
	 SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
	  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	  container.setConnectionFactory(connectionFactory);
	  container.setQueueNames(USER_ROUTING_KEY);
	  container.setMessageListener(listenerAdapter);
	  return container;
	 }


	 @Bean
	 MessageListenerAdapter myQueueListener(MessageListener listener) {
	  return new MessageListenerAdapter(listener, "onMessage");
	 }
}
