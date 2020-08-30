package com.fsoft.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fsoft.cursomc.services.DBService;
import com.fsoft.cursomc.services.EmailService;
import com.fsoft.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dBService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dBService.instantiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
