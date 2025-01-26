package com.inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.repository","com.service","com.controller","com.inicio"})
@EntityScan(basePackages = {"com.domain"})
@EnableJpaRepositories(basePackages = {"com.repository"})
@SpringBootApplication
@EnableScheduling
public class MicroserviceHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceHotelApplication.class, args);
	}

}
