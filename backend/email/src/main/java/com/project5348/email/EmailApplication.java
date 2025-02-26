package com.project5348.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EmailApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmailApplication.class, args);
	}

}
