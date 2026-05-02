package com.cjc.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class UhisDataCollectionService04Application {
    
	@Bean
	public ModelMapper mapper()
	{
		return new ModelMapper();
	}
		
	public static void main(String[] args) {
		SpringApplication.run(UhisDataCollectionService04Application.class, args);
	}

}
