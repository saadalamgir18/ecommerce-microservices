package com.ecommerce.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class UserApplication {




	public static void main(String[] args) {

		SpringApplication.run(UserApplication.class, args);
	}

}
