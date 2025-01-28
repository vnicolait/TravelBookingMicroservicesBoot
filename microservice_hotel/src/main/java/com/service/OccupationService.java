package com.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dto.RoomRentRequestDTO;
import dto.RoomRentResponseDTO;


public interface OccupationService {

	void saveRentRoomOccupation(int idHotel, int idRoom, LocalDate startRent, LocalDate endRent);

	RoomRentResponseDTO rentRoomOfHotelCurrent(RoomRentRequestDTO dto);
	
	void confirmReservationRoom(int idOccupation);
	
	boolean releaseExpiredBlocks();
}
