package com.inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EntityScan(basePackages = {"com.model"})
@EnableJpaRepositories(basePackages = {"com.repository"})
@ComponentScan(basePackages = {"com.model","com.repository","com.service","com.controller","com.inicio"})
@SpringBootApplication
public class MicroserviceUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUsersApplication.class, args);
		
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Encriptar contraseñas
        String encodedPassword1 = encoder.encode("contraseña123");
        String encodedPassword2 = encoder.encode("contraseña456");

        System.out.println("Contraseña encriptada 1: " + encodedPassword1);
        System.out.println("Contraseña encriptada 2: " + encodedPassword2);
	}

}
