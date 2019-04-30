package com.bridgelabz.fundoo.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.rabbitmq.RabbitMqBody;

@Component
public class Email {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	public void sendEmail(RabbitMqBody rabbitmqBody){
		System.out.println("Send email");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(rabbitmqBody.getToEmailId());
		mail.setSubject(rabbitmqBody.getSubject());
		mail.setText(rabbitmqBody.getUrl());
		System.out.println(environment.getProperty("status.email.ready"));
		javaMailSender.send(mail);
		System.out.println(environment.getProperty("status.email.send"));
	}
}
