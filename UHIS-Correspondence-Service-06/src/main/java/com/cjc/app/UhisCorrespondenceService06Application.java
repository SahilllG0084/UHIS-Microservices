package com.cjc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class UhisCorrespondenceService06Application {

	public static void main(String[] args) {
		SpringApplication.run(UhisCorrespondenceService06Application.class, args);
	}

}
