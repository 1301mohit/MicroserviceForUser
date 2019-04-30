package com.bridgelabz.fundoo.user.rabbitmq;

import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.user.rabbitmq.RabbitMqBody;
import com.bridgelabz.fundoo.user.util.Email;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageListenerImplimentation implements MessageListener{

	@Autowired
	RabbitMqBody rabbitMqBody;
	
	@Autowired
	Email email;
	
	@Override
	public void onMessage(byte[] message) {
		
		System.out.println(new Date());
		try {
			Thread.sleep(5000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Message Received:"+message);
		String msg = new String(message);
		System.out.println("Message received:"+msg);
		ObjectMapper mapper = new ObjectMapper();
		try {
			rabbitMqBody = mapper.readValue(msg, RabbitMqBody.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		email.sendEmail(rabbitMqBody);
		System.out.println(new Date());
		
	}

}
