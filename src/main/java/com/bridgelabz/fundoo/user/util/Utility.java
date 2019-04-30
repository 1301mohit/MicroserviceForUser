package com.bridgelabz.fundoo.user.util;

public class Utility {

	public static String getBody(Long userId, String link) 
	{
		return "http://localhost:4200/"+link+"/"+UserToken.generateToken(userId);
	}
	
	public static String getUrl(Long id)
	{
		return "192.168.0.90:8081/api/user/"+UserToken.generateToken(id);
	}
	
}
