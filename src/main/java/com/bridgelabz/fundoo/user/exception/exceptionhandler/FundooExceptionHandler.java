package com.bridgelabz.fundoo.user.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.user.exception.TokenException;
import com.bridgelabz.fundoo.user.exception.UserException;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.util.StatusUtil;


@RestControllerAdvice
public class FundooExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> userExceptionHandler(UserException e)
	{
		Response statusInfo = StatusUtil.statusInfo(e.getMessage(), "1000");
		return new ResponseEntity<Response>(statusInfo, HttpStatus.OK);
	}
	
	public ResponseEntity<Response> tokenExceptionHandeler(TokenException e)
	{
		Response statusInfo = StatusUtil.statusInfo(e.getMessage(), "2000");
		return new ResponseEntity<Response>(statusInfo, HttpStatus.OK);
	}
}
