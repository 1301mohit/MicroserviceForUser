package com.bridgelabz.fundoo.user.controller;

import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@PropertySource("classpath:message.properties")
@RequestMapping("/api/user")
public class UserController {
	
static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public Environment environment;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	@ApiOperation(value = "api of register for new user")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDto userDTO/*BindingResult bindingResult*/) 
	{
		logger.info("userDTO:"+userDTO);
		logger.trace("User Registration");
		Response response = userService.register(userDTO);
		return new ResponseEntity<Response>(response , HttpStatus.OK);
	}
	
	@GetMapping("/{token}")
	@ApiOperation(value = "api of email verification for new user")
	public String emailValidation(@PathVariable String token)
	{
		logger.info("Token:"+token);
		String result = userService.validateEmailId(token);
		return result;
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "api of login for user")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDto loginDTO)
	{
		logger.info("loginDTO:"+loginDTO);
		logger.trace("Login");
		boolean flag = false;
		Response response = userService.login(loginDTO);
		logger.info("Response:"+response);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws Exception
	{
		logger.info("email:"+email);
		logger.trace("Forgot Password");
		Response response = userService.forgotPassword(email);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/user/{token}")
	public ResponseEntity<Response> resetPassword(@RequestParam("password") String password, @PathVariable String token) throws Exception
	{
		logger.info("token:"+token);
		logger.info("password:"+password);
		Response response = userService.resetPassword(token, password);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getAllUser")
	public ResponseEntity<List<User>> getAllUser(){
		logger.info("Get all user");
		List<User> list = userService.getAllUser();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
