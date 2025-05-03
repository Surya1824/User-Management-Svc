package com.surya.user.management.svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserManagementSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementSvcApplication.class, args);
	}

}
