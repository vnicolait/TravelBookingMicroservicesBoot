package com.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.model.FlightEntity;

import dto.FlightDTO;

public interface ServiceFlight {

	List<FlightDTO> listFlightAvailableBeetwen(String origin, String destination,
                                                  LocalDate startDate, LocalDate endDate);
//	FlightEntity findByFlight(int idFlight);
	FlightDTO findByFlight(int idFlight);
    List<FlightEntity> listFlightDirect(String origin, String destination,
                                            LocalDateTime startDate, LocalDateTime endDate);
	void updateSeatFlight(int idFlight,int seat);
	//int blockSeats( int idFlight,  int seats, LocalDateTime expiration);

	// Method flo blocked when user reservation
    public boolean blockSeats(int idFlight, int seats);
}
