package com.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.inicio.MicroserviceFlightApplication;
import com.model.FlightEntity;

import jakarta.persistence.EntityManager;
@DataJpaTest // Configura solo la capa de persistencia para pruebas
@ActiveProfiles("test")
@ContextConfiguration(classes = MicroserviceFlightApplication.class) // Reemplaza con tu clase principal
public class TestRepository {
// https://stackoverflow.com/questions/47487609/unable-to-find-a-springbootconfiguration-you-need-to-use-contextconfiguration
    @Autowired
    private FlightEntityJpaRepository repository;
    @Autowired
    private EntityManager entityManager;
       	@Test
        @Transactional  // Aquí agregas @Transactional al método de prueba
        public void testFindByIdFlight() {
       	System.out.println(repository.findAll());
       		int idFlight = 1;
          int seatToBlock = 5;

          Optional<FlightEntity> beforeUpdateFlight = repository.findById(idFlight);
          beforeUpdateFlight.ifPresent(flight -> {
              System.out.println("Asientos disponibles: " + flight.getAvailableSeats());
              System.out.println("Asientos bloqueados antes: " + flight.getBlockedSeats());
          });

          LocalDateTime expiration = LocalDateTime.now().plusMinutes(10).withNano(0);

          // Llamar al método del repositorio
          int rowsUpdated = repository.blockSeats(idFlight, seatToBlock, expiration);
          System.out.println("Filas actualizadas: " + rowsUpdated);
          assertEquals(1, rowsUpdated, "Debería haberse actualizado una fila");

          // Sincronizar el contexto de persistencia
          entityManager.flush(); // Sincroniza con la base de datos
          entityManager.clear(); // Limpia el contexto para evitar datos en caché
        //     repository.flush();
          
          // Consultar nuevamente para verificar los cambios
          Optional<FlightEntity> afterUpdateFlight = repository.findById(idFlight);
          afterUpdateFlight.ifPresent(flight -> {
              System.out.println("Asientos bloqueados después: " + flight.getBlockedSeats());
              System.out.println("Fecha de expiración: " + flight.getBlockExpiration());
          });

          // Validaciones
          assertTrue(afterUpdateFlight.isPresent());
          assertEquals(10, afterUpdateFlight.get().getBlockedSeats()); // 5 iniciales + 5 bloqueados
          assertEquals(expiration, afterUpdateFlight.get().getBlockExpiration());
    }
       	
       	@Test
       	@Transactional
       	public void testReleaseExpiredBlocks() {
       	    int idFlight = 1;
       	    int seatsToRelease = 5;
       	    
       	    // Simular un bloqueo caducado (configurando la expiración en el pasado)
       	    Optional<FlightEntity> beforeUpdateFlight = repository.findById(idFlight);
       	    beforeUpdateFlight.ifPresent(flight -> {
       	        flight.setBlockedSeats(10); // Asegurarnos de que hay bloqueos para liberar
       	        flight.setBlockExpiration(LocalDateTime.now().minusMinutes(5)); // Expiración en el pasado
       	        repository.save(flight);
       	        System.out.println("Estado inicial antes de liberar:");
       	        System.out.println("Asientos bloqueados: " + flight.getBlockedSeats());
       	        System.out.println("Fecha de expiración: " + flight.getBlockExpiration());
       	    });

       	    // Llamar al método para liberar los asientos caducados
       	    LocalDateTime now = LocalDateTime.now();
       	    int rowsUpdated = repository.releaseExpiredBlocks(idFlight, seatsToRelease, now);

       	    System.out.println("Filas actualizadas: " + rowsUpdated);
       	    assertEquals(1, rowsUpdated, "Debería haberse actualizado una fila porque el bloqueo ha caducado");

       	    // Sincronizar el contexto de persistencia
       	    entityManager.flush();
       	    entityManager.clear();

       	    // Consultar nuevamente el vuelo
       	    Optional<FlightEntity> afterUpdateFlight = repository.findById(idFlight);
       	    afterUpdateFlight.ifPresent(flight -> {
       	        System.out.println("Estado después de liberar:");
       	        System.out.println("Asientos bloqueados: " + flight.getBlockedSeats());
       	        System.out.println("Fecha de expiración: " + flight.getBlockExpiration());
       	    });

       	    // Validar que los bloqueos caducados fueron liberados
       	    assertTrue(afterUpdateFlight.isPresent());
       	    assertEquals(5, afterUpdateFlight.get().getBlockedSeats(), "Los asientos bloqueados deberían haberse reducido");
       	}

       	@Test
       	@Transactional
       	public void testDoNotReleaseNonExpiredBlocks() {
       	    int idFlight = 2; // Suponiendo que tenemos un vuelo con ID 2
       	    int seatsToRelease = 5;

       	    // Simular un bloqueo no caducado
       	    Optional<FlightEntity> beforeUpdateFlight = repository.findById(idFlight);
       	    beforeUpdateFlight.ifPresent(flight -> {
       	        flight.setBlockedSeats(10);
       	        flight.setBlockExpiration(LocalDateTime.now().plusMinutes(5)); // Expiración en el futuro
       	        repository.save(flight);
       	        System.out.println("Estado inicial antes de intentar liberar:");
       	        System.out.println("Asientos bloqueados: " + flight.getBlockedSeats());
       	        System.out.println("Fecha de expiración: " + flight.getBlockExpiration());
       	    });

       	    // Llamar al método para liberar los asientos caducados
       	    LocalDateTime now = LocalDateTime.now();
       	    int rowsUpdated = repository.releaseExpiredBlocks(idFlight, seatsToRelease, now);

       	    System.out.println("Filas actualizadas: " + rowsUpdated);
       	    assertEquals(0, rowsUpdated, "No debería actualizar ninguna fila porque el bloqueo no ha caducado");

       	    // Consultar nuevamente el vuelo
       	    Optional<FlightEntity> afterUpdateFlight = repository.findById(idFlight);
       	    afterUpdateFlight.ifPresent(flight -> {
       	        System.out.println("Estado después de intentar liberar:");
       	        System.out.println("Asientos bloqueados: " + flight.getBlockedSeats());
       	        System.out.println("Fecha de expiración: " + flight.getBlockExpiration());
       	    });

       	    // Validar que no hubo cambios
       	    assertTrue(afterUpdateFlight.isPresent());
       	    assertEquals(10, afterUpdateFlight.get().getBlockedSeats(), "Los asientos bloqueados deberían permanecer igual");
       	}

/*
	 @Test
	 public void testBlockSeats() {
		// Crear vuelo
		    FlightEntity flight = new FlightEntity();
		    flight.setAvailableSeats(100);
		    flight.setBlockedSeats(0);
		    flight.setCompany("Test Airline");
		    flight.setOrigin("Madrid");
		    flight.setDestination("Barcelona");
		    flight.setDateTimeDeparture(LocalDateTime.of(2025, 2, 1, 10, 0));
		    flight.setDateTimeArrival(LocalDateTime.of(2025, 2, 1, 12, 0));
		    flightRepository.save(flight);

		    // Verifica los valores iniciales
		    FlightEntity savedFlight = flightRepository.findById(flight.getIdFlight()).get();
		    System.out.println("Initial Flight: " + savedFlight);
		    assertEquals(100, savedFlight.getAvailableSeats(),()->"Parecen que el valor esperado de plazas no es el correcto");
		    assertEquals(0, savedFlight.getBlockedSeats(),()->"Parecen que el valor esperado de bloquerosno es el correcto");

		    // Ejecutar blockSeats
		    LocalDateTime expiration = LocalDateTime.now().plusHours(2);
		    int updatedRows = flightRepository.blockSeats(savedFlight.getIdFlight(), 10, expiration);

		    // Verifica si se aplicaron los cambios
		    FlightEntity updatedFlight = flightRepository.findById(flight.getIdFlight()).get();
		    System.out.println("Updated Flight: " + updatedFlight);
		    assertEquals(10, updatedFlight.getBlockedSeats());
		    assertEquals(90, updatedFlight.getAvailableSeats() - updatedFlight.getBlockedSeats());
	    }
	 @Test
	 public void testReleaseExpiredBlocks() {
		 // Crear vuelo con bloques expirados
		    FlightEntity flight = new FlightEntity();
		    flight.setAvailableSeats(100);
		    flight.setBlockedSeats(10);
		    flight.setBlockExpiration(LocalDateTime.now().minusHours(1)); // Bloqueo expirado
		    flightRepository.save(flight);

		    // Verifica los valores iniciales
		    FlightEntity savedFlight = flightRepository.findById(flight.getIdFlight()).get();
		    assertEquals(10, savedFlight.getBlockedSeats());
		    assertTrue(savedFlight.getBlockExpiration().isBefore(LocalDateTime.now()));

		    // Ejecutar releaseExpiredBlocks
		    int updatedRows = flightRepository.releaseExpiredBlocks(savedFlight.getIdFlight(), 10, LocalDateTime.now());

		    // Verifica si se liberaron los bloques
		    FlightEntity updatedFlight = flightRepository.findById(flight.getIdFlight()).get();
		    assertEquals(0, updatedFlight.getBlockedSeats());
	 }
*/
}
