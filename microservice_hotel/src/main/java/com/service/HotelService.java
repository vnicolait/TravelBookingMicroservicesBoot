package com.service;

import java.time.LocalDate;
import java.util.List;

import com.domain.HotelEntity;

import dto.HotelDTO;

public interface HotelService {
    // disponibilidad de Hotel por localidad y fechas SQL
	List<HotelDTO> findAvailableHotelsWithRoomsBetweenDatesSQL(String location,
            LocalDate fechaInicio, LocalDate fechaFin);
    // disponibilidad de Hotel por localidad y fechas JPA
//	List<HotelEntity> findAvailableHotelsWithRoomsBetweenDatesJPA(String location,
//			LocalDate fechaInicio, LocalDate fechaFin);
	
	HotelDTO findByHotelById(int idHotel);
}
