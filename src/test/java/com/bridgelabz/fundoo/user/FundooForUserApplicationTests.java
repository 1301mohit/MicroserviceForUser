package com.bridgelabz.fundoo.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoo.user.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FundooForUserApplication.class)
@WebAppConfiguration
public class FundooForUserApplicationTests {
	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Before()
	public void mockMvcSetup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testUserRegisteration() throws Exception {

		MvcResult result = this.mockMvc
				.perform(post("/api/user/register").contentType("application/json;charset=UTF-8").content(
						"{\"email\":\"1301mohitkr@gmail.com\",\"firstName\":\"Mohit\",\"lastName\":\"Kumar\",\"password\":\"12345\",\"mobileNumber\":\"1234567890\"}"))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		String returnResult = result.getResponse().getContentAsString();
		Response response = objectMapper.readValue(returnResult, Response.class);
		assertEquals(response.getStatusCode(), "101");
	}
	
	@Test
	public void testUserLogin() throws Exception {
		MvcResult result = this.mockMvc.perform(post("/api/user/login").contentType("application/json;charset=UTF-8").content(
		"{\"email\":\"1301mohitkr@gmail.com\",\"password\":\"12345\"}")).andDo(print()).andExpect(status().isOk()).andReturn();
		String returnResult = result.getResponse().getContentAsString();
		Response response = objectMapper.readValue(returnResult, Response.class);
		assertEquals(response.getStatusCode(), "101");
	}
	
	@Test
	public void contextLoads() {
	}

}
