package com.cjc.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class UhisCaseWorkerService07Application {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	    return modelMapper;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UhisCaseWorkerService07Application.class, args);
	}

}
