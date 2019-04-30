package com.bridgelabz.fundoo.user.rabbitmq;

public interface MessageListener {

	public void onMessage(byte[] message);
}
