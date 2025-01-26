package com.inicio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.repository.TestRepository;

@SpringBootTest
@ActiveProfiles("test")  // Forzar el perfil "test"
class MicroserviceFlightApplicationTests {

	@Value("${server.port}")
    private int serverPort;
	@Test
	void contextLoads() {
        System.out.println("Active server port: " + serverPort);
      System.out.println("El perfil activo es: " + System.getProperty("spring.profiles.active"));

	}

}
