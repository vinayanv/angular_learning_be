package com.techgeeknext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SpringBootJpaMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaMysqlApplication.class, args);
	}

}
