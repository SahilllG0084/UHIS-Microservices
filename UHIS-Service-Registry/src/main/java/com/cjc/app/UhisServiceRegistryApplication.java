package com.cjc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class UhisServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UhisServiceRegistryApplication.class, args);
	}

}
