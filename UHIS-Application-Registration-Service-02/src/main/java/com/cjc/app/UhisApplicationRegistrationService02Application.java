package com.cjc.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class UhisApplicationRegistrationService02Application {
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(UhisApplicationRegistrationService02Application.class, args);
	}

}
