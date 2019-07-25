package com.bridgelabz.fundoo.user.service;

import java.util.List;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.response.Response;

public interface UserService {
	
	public Response register(UserDto userDTO);
	public String validateEmailId(String token);
	public Response login(LoginDto loginuser);
	public Response forgotPassword(String email);
	public Response resetPassword(String token, String password);
	public List<User> getAllUser();
	
}
