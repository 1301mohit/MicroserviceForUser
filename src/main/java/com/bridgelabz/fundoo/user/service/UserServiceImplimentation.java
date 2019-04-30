package com.bridgelabz.fundoo.user.service;

import java.time.LocalDate;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.user.dto.LoginDto;
import com.bridgelabz.fundoo.user.dto.UserDto;
import com.bridgelabz.fundoo.user.exception.UserException;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.rabbitmq.MessageProducer;
import com.bridgelabz.fundoo.user.rabbitmq.RabbitMqBody;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.util.StatusUtil;
import com.bridgelabz.fundoo.user.util.UserToken;
import com.bridgelabz.fundoo.user.util.Utility;

@Service
public class UserServiceImplimentation implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RabbitMqBody rabbitMqBody;
	
	@Autowired
	private MessageProducer messageProducer;

	@Override
	public Response register(UserDto userDTO) {
		
		Optional<User> useravailable = userRepository.findByEmail(userDTO.getEmail());
		
		//To check user is available or not
		if(useravailable.isPresent())
		{
			throw new UserException(environment.getProperty("status.register.dublicateUser"));
		}
		
		//Copy user data userDTO to user class
		User user=modelMapper.map(userDTO, User.class);
		
		//set password in encrypted form
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRegisteredDate(LocalDate.now());
		
		//save user data in data base
	    user = userRepository.save(user);
	    
	    //Set the subject, emailid and activationlink in rabbitmqbody
	    rabbitMqBody.setSubject("Verify emailId");
	    rabbitMqBody.setToEmailId(user.getEmail());
	    String userActivationLink = Utility.getUrl(user.getUserId());
	    rabbitMqBody.setUrl(userActivationLink);
	    System.out.println("RabbitMqBody--------------------------->"+rabbitMqBody);
	    
	    //Send the rabbitMqbody in queue through message procedure Class
	    messageProducer.sendMessage(rabbitMqBody);
	    
	    //EmailUtil.send(user.getEmail(), environment.getProperty("status.register.mailForRegistration"), getUrl(user.getuserId()));
	    //messageServiceImpl.sendEmail(user);
	    
	    Response response = StatusUtil.statusInfo(environment.getProperty("status.register.successful"), environment.getProperty("status.code.success"));
	    return response;
	}

	@Override
	public String validateEmailId(String token) {
		Long id = UserToken.tokenVerify(token);
		User user = null;
		try {
			user = userRepository.findById(id).orElseThrow(() -> new Exception(environment.getProperty("status.email.user")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setVerification(true);
		userRepository.save(user);
		return environment.getProperty("status.email.userVerify");
	}

	@Override
	public Response login(LoginDto loginuser) {
		Optional<User> userAvailable = userRepository.findByEmail(loginuser.getEmail());
		System.out.println("database password"+userAvailable.get().getPassword());
		System.out.println("login user password"+passwordEncoder.encode(loginuser.getPassword()));
		if(userAvailable.get().isVerification())
		{
			if(userAvailable.isPresent() && passwordEncoder.matches(loginuser.getPassword(),userAvailable.get().getPassword())) 
			{ 
				String generateToken = UserToken.generateToken(userAvailable.get().getUserId());
				Response response = StatusUtil.tokenStatusInfo(environment.getProperty("status.login.successful"), environment.getProperty("status.code.success"), generateToken);
				return response; 
			} 
			else 
			{ 
				throw new UserException(environment.getProperty("status.login.unSuccessful")); 
			}
		}
		else
		{
			throw new UserException(environment.getProperty("status.email.verify"));
		}
	}

	@Override
	public Response forgotPassword(String email) {
		
		Optional<User> userAvailable = userRepository.findByEmail(email);
		if(userAvailable.isPresent()) 
		{
			User user = userAvailable.get();
			//EmailUtil.send(email, environment.getProperty("status.password.reset"), Utility.getBody(user, "user"));
		//	RabbitMqBody rabbitmqBody = new RabbitMqBody();
		//	rabbitmqBody.setToEmailId(user.getEmail());
			rabbitMqBody.setToEmailId(user.getEmail());
			rabbitMqBody.setSubject("password recovery link");
			String url = Utility.getBody(user.getUserId(), "resetPassword");
			rabbitMqBody.setUrl(url);
			System.out.println("Body in forgetpassword:"+rabbitMqBody);
			messageProducer.sendMessage(rabbitMqBody);
			Response response = StatusUtil.statusInfo(environment.getProperty("status.password.successful"), environment.getProperty("status.code.success"));
			return response;
		}
		else 
		{
			throw new UserException(environment.getProperty("status.password.email"));
		}
	}

	@Override
	public Response resetPassword(String token, String password) {
		
		long userId = UserToken.tokenVerify(token);
		System.out.println(userId);
		User user = userRepository.findById(userId).get();
		System.out.println(user.getPassword());
		user.setPassword(passwordEncoder.encode(password));
		System.out.println(user.getPassword());
		user.setAccountUpdateDate(LocalDate.now());
		userRepository.save(user);
		Response response = StatusUtil.statusInfo(environment.getProperty("status.password.resetpassword"), environment.getProperty("status.code.success"));
		return response;
		
	}

}
