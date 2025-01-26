package com.inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@ComponentScan(basePackages = {"booking.dto", "external", "dto","controller", "repository", "service"})
@EntityScan(basePackages = {"entities"})
@EnableJpaRepositories(basePackages = {"repository"})
@SpringBootApplication
public class MicroserviceReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceReservationApplication.class, args);
	}

	@Bean
	public WebClient getWebClient() {
		  return WebClient.create();
	}
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
}
