package com.repository;




import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.FlightEntity;

public interface FlightEntityJpaRepository extends JpaRepository<FlightEntity, Integer> {
	@Query("SELECT f FROM FlightEntity f WHERE f.origin = ?1 AND f.destination = ?2 " +
		       "AND ?3<=f.dateTimeDeparture " +
		       "AND ?4>=f.dateTimeArrival " +
		       "AND f.availableSeats > 0")
		List<FlightEntity> listFlightAvailable(String origin, String destination,
		                                       LocalDateTime startDate, LocalDateTime endDate);


	@Query("SELECT f FROM FlightEntity f join f.itineraries i WHERE f.origin = ?1 AND f.destination = ?2 " +
		       "AND ?3<=f.dateTimeDeparture " +
		       "AND ?4>=f.dateTimeArrival " +
		       "AND f.availableSeats > 0 GROUP BY f.idFlight HAVING COUNT(i.idItineraries)=1")
		List<FlightEntity> listFlightAvailableDirect(String origin, String destination,
		                                       LocalDateTime startDate, LocalDateTime endDate);

/*
 * @Query("SELECT f FROM FlightEntity f WHERE f.origin = ?1 AND f.destination = ?2 " +
       "AND ?3 <= f.dateTimeDeparture AND ?4 >= f.dateTimeArrival " +
       "AND f.availableSeats > 0 " +
       "AND SIZE(f.itineraries) = 1")
  */
 
	// Method for blocked while user reservation flight
	@Modifying
	//@Transactional
	@Query("UPDATE FlightEntity f SET f.blockedSeats = f.blockedSeats + :seats, f.blockExpiration = :expiration " +
		       "WHERE f.idFlight = :idFlight AND (f.availableSeats - f.blockedSeats) >= :seats")
		int blockSeats(@Param("idFlight") int idFlight, @Param("seats") int seats, @Param("expiration") LocalDateTime expiration);
//	@Modifying
//    @Query("UPDATE FlightEntity f SET f.blockedSeats = f.blockedSeats + :blockedSeats, f.blockExpiration = :expiration WHERE f.idFlight = :idFlight AND f.availableSeats >= (f.blockedSeats + :blockedSeats)")
//    int blockSeats(@Param("idFlight") int idFlight, @Param("blockedSeats") int blockedSeats, @Param("expiration") ZonedDateTime expiration);
//  
	@Modifying
    @Query("UPDATE FlightEntity f SET f.blockedSeats = f.blockedSeats - ?2 " +
           "WHERE f.idFlight = ?1 AND f.blockExpiration < ?3")
    int releaseExpiredBlocks(int idFlight, int seats, LocalDateTime now);
    
}
