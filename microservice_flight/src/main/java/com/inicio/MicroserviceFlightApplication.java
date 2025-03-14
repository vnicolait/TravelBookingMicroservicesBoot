package com.inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com.repository","dto","com.repository","com.service","com.controller"})
@EntityScan(basePackages = {"com.model"})
@EnableJpaRepositories(basePackages = {"com.repository"})
@SpringBootApplication
public class MicroserviceFlightApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceFlightApplication.class, args);
	}

}
