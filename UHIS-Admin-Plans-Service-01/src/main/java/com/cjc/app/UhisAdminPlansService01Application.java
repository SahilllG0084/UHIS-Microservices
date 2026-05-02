package com.cjc.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class UhisAdminPlansService01Application {

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UhisAdminPlansService01Application.class, args);
	}

}
