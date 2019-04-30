package com.bridgelabz.fundoo.user.exception;

import org.springframework.stereotype.Component;

public class UserException extends RuntimeException{

	public UserException(String message) {
		super(message);
	}
	
}
