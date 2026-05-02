package com.cjc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class UhisApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(UhisApiGatewayApplication.class, args);
	}

}
